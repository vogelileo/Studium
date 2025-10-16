<script lang="ts">
    import {onMount, onDestroy} from 'svelte';
    import 'maplibre-gl/dist/maplibre-gl.css';
    import {wsConnect, wsSubscribe} from "$lib/wsClient.js";
    import maplibregl from 'maplibre-gl';
    import {Protocol, PMTiles} from 'pmtiles';

    type MarkerInput =
        | [number, number]
        | { lng: number; lat: number; label?: string; title?: string; html?: string };
    type LayerToggle = { id: string; label: string; visible: boolean }

    const {data} = $props();
    let nodes = $state(data.nodes);

    let center: [number, number] = $state([8.2267, 46.8011]); // center of switzerland
    let zoom: number = $state(6.5);
    let showZoomControls: boolean = $state(false);
    let zoomControlPosition: 'top-left' | 'top-right' | 'bottom-left' | 'bottom-right' = $state('top-right');
    let showCompass: boolean = $state(true);
    let showLayerControlButton: boolean = $state(true);
    let markers: MarkerInput[] = $state([]);
    let reliefPath: string = $state('/tiles/swisstopo.relief.vt.pmtiles');
    let vectorPath: string = $state('/tiles/swisstopo.base.vt.pmtiles');
    let mapEl: HTMLDivElement;
    let map: any;
    let ML: any;
    let toggles: LayerToggle[] = $state([]);
    let visMap: Record<string, boolean> = $state({});
    let showLayerControl = $state(false);
    let markerRefs: any[] = $state([]);
    let is3D = $state(false);
    let bearing = $state(0);
    let initialZoomTimer: number | null = null;
    let refreshMarkersTimer: number | null = null;
    // New: periodic 10s check to update lingered detection state
    let lingerCheckTimer: number | null = null;
    const newMarkerRefs: any[] = $state([]);
    const existingMarkers: Record<string, any> = $state({});
    // Track per-node last active time per sensor to keep popup sensors visible during linger
    let lastSensorActiveAtByNode: Record<string, Record<string, number>> = $state({});

    const RED_MS = 10_000;
    const ORANGE_MS = 60_000;
    const TOTAL_LINGER_MS = RED_MS + ORANGE_MS;
    let lastDetectedAtByNode: Record<string, number> = $state({}); // key: nodeId -> epoch ms of last detection
    let lastTamperedAtByNode: Record<string, number> = $state({});

    function normMarkers() {
        return (markers || []).map(m =>
            Array.isArray(m)
                ? {lng: m[0], lat: m[1], title: 'Node', html: ''}
                : {
                    lng: m.lng,
                    lat: m.lat,
                    title: m.title ?? (m.label ?? 'Node'),
                    html: m.html ?? (m.label ?? '')
                }
        );
    }

    // helper to make ids nicer in the UI
    function prettyLabel(id: string) {
        return id.replace(/[_-]+/g, ' ')
            .replace(/\b\w/g, m => m.toUpperCase());
    }

    // color mapping for vector layers
    const layerColors: Record<string, string> = {
        'boundary': '#ff00ff',
        'building': '#666666',
        'landcover': '#66bb6a',
        'landuse': '#aed581',
        'park': '#4caf50',
        'spot_elevation': '#2196f3',
        'transportation': '#ff8844',
        'contour_line': '#4dff00ff',
        'water': '#42a5f5',
        'waterway': '#1976d2'
    };

    // decide what's toggleable: vector layers from your vector source
    const VECTOR_SOURCE = 'swissmap'; // <-- keep in sync with your sources
    function isToggleableVectorLayer(layer: any): boolean {
        if (!layer) return false;
        if (layer.type === 'background') return false;
        if (!layer.source || layer.source !== VECTOR_SOURCE) return false;
        return true;
    }

    function applyVisibility(id: string, visible: boolean) {
        if (!map || !map.getLayer(id)) return;
        map.setLayoutProperty(id, 'visibility', visible ? 'visible' : 'none');
        visMap[id] = visible;
    }

    // build toggle list from the current map style - only layers actually defined in style
    function buildTogglesFromStyle() {
        if (!map) return;
        const style = map.getStyle();

        // Get only the layers that are explicitly defined in our map style
        // These should match the layers defined in the style.layers array
        const definedLayerIds = [
            'boundary', 'building', 'landcover', 'landuse', 'park', 'spot_elevation', 'transportation', 'contour_line',
            'water', 'waterway'
        ];

        const layers = (style?.layers || []).filter((l: any) =>
            definedLayerIds.includes(l.id) && isToggleableVectorLayer(l)
        );

        toggles = layers.map((l: any) => {
            const current = (map.getLayoutProperty(l.id, 'visibility') ?? 'visible') as string;
            const visible = current !== 'none';
            visMap[l.id] = visible;
            return {id: l.id, label: prettyLabel(l.id), visible};
        });
    }

    function toggleLayerControl() {
        showLayerControl = !showLayerControl;
    }

    function startRefreshMarkersTimer() {
        if (refreshMarkersTimer) {
            clearInterval(refreshMarkersTimer);
        }
        refreshMarkersTimer = setInterval(() => {
            updateMarkers();
        }, 60 * 1000);
    }

    // New: check linger/detection state every 10s so red -> green flips on time
    function startLingerCheckTimer() {
        if (lingerCheckTimer) {
            clearInterval(lingerCheckTimer);
        }
        // check every second so color transitions occur promptly
        lingerCheckTimer = setInterval(() => {
            try {
                updateMarkers();
            } catch {
            }
        }, 1000);
    }

    onMount(() => {
        ML = maplibregl;

        const protocol = new Protocol();
        ML.addProtocol('pmtiles', protocol.tile);
        const reliefURL = `${window.location.origin}${reliefPath}`;
        const vectorURL = `${window.location.origin}${vectorPath}`;
        protocol.add(new PMTiles(reliefURL));
        protocol.add(new PMTiles(vectorURL));

        map = new ML.Map({
            container: mapEl,
            style: {
                version: 8,
                glyphs: 'https://demotiles.maplibre.org/font/{fontstack}/{range}.pbf',
                sources: {
                    relief: {type: 'vector', url: `pmtiles://${reliefURL}`},
                    swissmap: {type: 'vector', url: `pmtiles://${vectorURL}`}
                },
                layers: [
                    {id: "background", type: "background", paint: {"background-color": "#ffffff"}},
                    {
                        id: "boundary",
                        type: "line",
                        source: "swissmap",
                        "source-layer": "boundary",
                        paint: {"line-color": "#ff00ff", "line-width": 1.5, "line-opacity": 0.6, "line-blur": 0.5}
                    },
                    {
                        id: "contour_line",
                        type: "line",
                        source: "swissmap",
                        "source-layer": "contour_line",
                        paint: {"line-color": "#4dff00ff", "line-opacity": 0.5}
                    },
                    {
                        id: "building",
                        type: "fill",
                        source: "swissmap",
                        "source-layer": "building",
                        paint: {
                            "fill-color": "#666666",
                            "fill-opacity": 0.5,
                            "fill-antialias": true,
                            "fill-outline-color": "#333333"
                        }
                    },
                    {
                        id: "landcover",
                        type: "fill",
                        source: "swissmap",
                        "source-layer": "landcover",
                        paint: {
                            "fill-color": "#66bb6a",
                            "fill-opacity": 0.3,
                            "fill-antialias": true,
                            "fill-outline-color": "rgba(102, 187, 106, 0.5)"
                        }
                    },
                    {
                        id: "landuse",
                        type: "fill",
                        source: "swissmap",
                        "source-layer": "landuse",
                        paint: {
                            "fill-color": "#aed581",
                            "fill-opacity": 0.3,
                            "fill-antialias": true,
                            "fill-outline-color": "rgba(174, 213, 129, 0.5)"
                        }
                    },
                    {
                        id: "park",
                        type: "fill",
                        source: "swissmap",
                        "source-layer": "park",
                        paint: {
                            "fill-color": "#4caf50",
                            "fill-opacity": 0.25,
                            "fill-antialias": true,
                            "fill-outline-color": "rgba(76, 175, 80, 0.6)"
                        }
                    },
                    {
                        id: "transportation",
                        type: "line",
                        source: "swissmap",
                        "source-layer": "transportation",
                        paint: {
                            "line-color": "#ff8844",
                            "line-opacity": 0.8,
                            "line-width": [
                                "match",
                                ["get", "class"],       // street type from tiles
                                "motorway", 3,
                                "trunk", 2.5,
                                "primary", 2,
                                "secondary", 1.5,
                                "tertiary", 1.2,
                                "residential", 1,
                                "service", 0.5,
                                /* default */ 0.8
                            ]
                        }
                    },
                    {
                        id: "water",
                        type: "fill",
                        source: "swissmap",
                        "source-layer": "water",
                        paint: {
                            "fill-color": "#42a5f5",
                            "fill-opacity": 0.4,
                            "fill-antialias": true,
                            "fill-outline-color": "rgba(66, 165, 245, 0.6)"
                        }
                    },
                    {
                        id: "waterway",
                        type: "line",
                        source: "swissmap",
                        "source-layer": "waterway",
                        paint: {"line-color": "#1976d2", "line-width": 1.5, "line-opacity": 0.7, "line-blur": 0.5}
                    }
                ]
            },
            center,
            zoom,
            minZoom: zoom,
            maxZoom: 25,
            renderWorldCopies: false
        });

        map.on('load', () => {
            if (showZoomControls) {
                map.addControl(new ML.NavigationControl({
                    showZoom: true,
                    showCompass,
                    visualizePitch: false
                }), zoomControlPosition);
            }

            // Add relief layers dynamically
            try {
                const reliefLayers: string[] = [
                    'landcover', 'landuse', 'water', 'waterway', 'transportation',
                    'building', 'contour', 'hillshade', 'boundary'
                ];

                reliefLayers.forEach(layerName => {
                    try {
                        const isLine = ['transportation', 'waterway', 'contour', 'boundary'].includes(layerName);
                        const lineColor = (layerName === 'water' || layerName === 'waterway') ? '#4a90e2' : '#8b7355';
                        const fillColor = layerName === 'water' ? '#4a90e2' : (layerName === 'landcover' ? '#90c695' : '#e6ddd4');

                        const paint = isLine
                            ? {'line-color': lineColor, 'line-width': 0.5, 'line-opacity': 0.7}
                            : {'fill-color': fillColor, 'fill-opacity': layerName === 'water' ? 0.8 : 0.6};

                        map.addLayer({
                            id: `relief_${layerName}`,
                            type: isLine ? 'line' : 'fill',
                            source: 'relief',
                            'source-layer': layerName,
                            paint
                        }, 'boundary'); // Insert before the first vector layer
                    } catch (e) {
                        console.log(`Relief layer ${layerName} not available:`, e);
                    }
                });
            } catch (e) {
                console.log('Error adding relief layers:', e);
            }

            // Load existing saved lines (if any) after base layers are ready
            loadExistingLines();

            map.once('idle', () => {
                buildTogglesFromStyle();
            });
        });

        map.on('styledata', () => {
            if (map.isStyleLoaded()) buildTogglesFromStyle();
        });

        // Add drawing handlers
        map.on('click', (e: any) => {
            if (drawingMode) {
                currentLine = [...currentLine, [e.lngLat.lng, e.lngLat.lat]];
                updateDrawSource();
            } else if (deleteLineMode) {
                try {
                    // Query lines and text annotations
                    const features = map.queryRenderedFeatures(e.point, {layers: ['drawn-lines', 'text-annotations']});
                    if (features && features.length) {
                        // Prefer deleting a non-temp line first, else a text annotation
                        let target = features.find(f => f.layer?.id === 'drawn-lines' && !f.properties?.temp && f.properties?._id != null);
                        if (!target) {
                            target = features.find(f => f.layer?.id === 'text-annotations' && f.properties?._id != null);
                        }
                        if (target) {
                            const idToRemove = target.properties._id;
                            if (target.layer?.id === 'drawn-lines') {
                                const beforeLen = drawnFeatures.length;
                                drawnFeatures = drawnFeatures.filter(f => f.properties?._id !== idToRemove);
                                if (drawnFeatures.length !== beforeLen) {
                                    updateDrawSource();
                                    scheduleSave();
                                }
                            } else if (target.layer?.id === 'text-annotations') {
                                const beforeTxt = textAnnotations.length;
                                textAnnotations = textAnnotations.filter(t => t._id !== idToRemove);
                                if (textAnnotations.length !== beforeTxt) {
                                    updateTextSource();
                                    scheduleSave();
                                }
                            }
                        }
                    }
                } catch (err) {
                    console.warn('Fehler beim Löschen:', err);
                }
            } else if (annotationMode) {
                try {
                    const text = window.prompt('Text eingeben:');
                    if (text && text.trim()) {
                        const newAnno = {
                            _id: ++textIdCounter,
                            text: text.trim(),
                            color: drawColor,
                            coordinates: [e.lngLat.lng, e.lngLat.lat]
                        };
                        textAnnotations = [...textAnnotations, newAnno];
                        updateTextSource();
                        scheduleSave();
                        // Auto-exit annotation mode after successful placement
                        annotationMode = false;
                        try {
                            map.getCanvas().style.cursor = '';
                        } catch {
                        }
                    }
                } catch {
                }
            }
        });
        map.on('dblclick', (e: any) => {
            if (drawingMode) {
                if (e && e.preventDefault) e.preventDefault();
                finalizeCurrentLine();
            }
        });

        window.addEventListener('keydown', (ev: KeyboardEvent) => {
            if (ev.key === 'Escape') {
                if (deleteLineMode) {
                    toggleDeleteLineMode();
                } else if (annotationMode) {
                    toggleAnnotationMode();
                } else {
                    cancelDrawing();
                }
            }
            if (ev.key === 'Enter' && drawingMode) {
                finalizeCurrentLine();
            }
        });

        map.on('error', (e: any) => {
        });

        // cleanup = () => { map?.remove(); protocol.remove(); };

        try {
            (nodes || []).forEach((n: any) => {
                const key = n?._id?.toString?.() ?? `${n?._id}`;
                if (n?.detected) lastDetectedAtByNode[key] = Date.now();
            });
        } catch {
        }
        try {
            (nodes || []).forEach((n: any) => {
                const key = n?._id?.toString?.() ?? `${n?._id}`;
                if (n?.tampered) lastTamperedAtByNode[key] = Date.now();
            });
        } catch {
        }
        // Seed sensor-level sticky timestamps from initial data
        try {
            (nodes || []).forEach((n: any) => {
                const key = n?._id?.toString?.() ?? `${n?._id}`;
                const lm = (n?.lastMeasurements ?? {}) as Record<string, any>;
                Object.entries(lm).forEach(([name, data]) => {
                    if (data && ((data as any).detected || (data as any).tampered)) {
                        const bySensor = lastSensorActiveAtByNode[key] ?? {};
                        bySensor[name] = Date.now();
                        lastSensorActiveAtByNode[key] = bySensor;
                    }
                });
            });
        } catch {}

        updateMarkers();
        startRefreshMarkersTimer();
        // also run a 10s linger checker so markers revert from red to green on time
        startLingerCheckTimer();
        // after mount, zoom to markers after 2 seconds
        initialZoomTimer = window.setTimeout(() => {
            try {
                zoomToMarkers();
            } catch (e) { /* ignore */
            }
        }, 0) as unknown as number;
        wsConnect();
        const unsub = wsSubscribe((msg) => {
            if (msg && msg.type === 'nodeView' && msg.payload) {
                const idx = nodes.findIndex(node => node._id === msg.payload._id);
                if (idx !== -1) {
                    // Update existing node
                    nodes[idx].lastUpdated = msg.payload.lastUpdated;
                    nodes[idx].battery = msg.payload.battery;
                    nodes[idx].displayName = msg.payload.displayName;
                    nodes[idx].posLong = msg.payload.posLong;
                    nodes[idx].posLat = msg.payload.posLat;
                    nodes[idx].detected = msg.payload.detected;
                    nodes[idx].tampered = msg.payload.tampered;
                    // Merge lastMeasurements for sensor-level info
                    if (msg.payload.lastMeasurements) {
                        nodes[idx].lastMeasurements = {
                            ...(nodes[idx].lastMeasurements ?? {}),
                            ...msg.payload.lastMeasurements
                        };
                        // Update sticky timestamps for any sensors that are currently active
                        try {
                            const key = nodes[idx]._id?.toString?.() ?? `${nodes[idx]._id}`;
                            const bySensor = lastSensorActiveAtByNode[key] ?? {};
                            Object.entries(msg.payload.lastMeasurements).forEach(([name, data]: [string, any]) => {
                                if (data && (data.detected || data.tampered)) {
                                    bySensor[name] = Date.now();
                                }
                            });
                            lastSensorActiveAtByNode[key] = bySensor;
                        } catch {}
                    }

                    if (msg.payload.detected) {
                        const key = nodes[idx]._id?.toString?.() ?? `${nodes[idx]._id}`;
                        lastDetectedAtByNode[key] = Date.now();
                    }
                    if (msg.payload.tampered) {
                        const key = nodes[idx]._id?.toString?.() ?? `${nodes[idx]._id}`;
                        lastTamperedAtByNode[key] = Date.now();
                    }
                    // also update direction if provided
                    if ('direction' in msg.payload) nodes[idx].direction = msg.payload.direction;
                    updateMarkers();
                    startRefreshMarkersTimer();
                } else {
                    // Add new node to the list
                    nodes = [...nodes, msg.payload];

                    if (msg.payload?.detected) {
                        const key = msg.payload._id?.toString?.() ?? `${msg.payload._id}`;
                        lastDetectedAtByNode[key] = Date.now();
                    }
                    if (msg.payload?.tampered) {
                        const key = msg.payload._id?.toString?.() ?? `${msg.payload._id}`;
                        lastTamperedAtByNode[key] = Date.now();
                    }
                    // Seed sensor sticky timestamps for a new node
                    try {
                        if (msg.payload?.lastMeasurements) {
                            const key = msg.payload._id?.toString?.() ?? `${msg.payload._id}`;
                            const bySensor = lastSensorActiveAtByNode[key] ?? {};
                            Object.entries(msg.payload.lastMeasurements).forEach(([name, data]: [string, any]) => {
                                if (data && (data.detected || data.tampered)) {
                                    bySensor[name] = Date.now();
                                }
                            });
                            lastSensorActiveAtByNode[key] = bySensor;
                        }
                    } catch {}
                    updateMarkers();
                }
            }
        });
        return () => unsub();
    });

    onDestroy(() => {
        if (initialZoomTimer) {
            clearTimeout(initialZoomTimer);
            initialZoomTimer = null;
        }
        if (refreshMarkersTimer) {
            clearTimeout(refreshMarkersTimer);
            refreshMarkersTimer = null;
        }
        if (lingerCheckTimer) {
            clearInterval(lingerCheckTimer);
            lingerCheckTimer = null;
        }
        cancelDrawing();
    });

    function isMeshNode(node) {
        return node.senderOpMode !== "LORA";
    }

    function isVisibleCoord(posLong, posLat) {
        if (!posLat || posLat < 45.3 || posLat > 48.4) {
            return false;
        }
        if (!posLat || posLong < 4.7 || posLong > 12.2) {
            return false;
        }
        return true;
    }

    function getMarkerBackground(node) {
        if (node.lastUpdated && new Date(node.lastUpdated).getTime() < Date.now() - 1000 * 60 * 2) {
            return "#878787";
        }
        const key = node?._id?.toString?.() ?? `${node?._id}`;
        const lastDetected = lastDetectedAtByNode[key] ?? 0;
        const lastTampered = lastTamperedAtByNode[key] ?? 0;
        const last = Math.max(lastDetected, lastTampered);
        const now = Date.now();
        const delta = last ? (now - last) : Infinity;
        if (delta < RED_MS) return '#dc3545'; // red for first 10s
        if (delta < RED_MS + ORANGE_MS) return '#ff9800'; // orange for next 60s
        return '#28a745'; // green afterwards
    }

    // Helper: normalize angle to [0,360)
    function normAngle(angle: any): number {
        let a = Number(angle);
        if (!isFinite(a)) return NaN;
        a = a % 360;
        if (a < 0) a += 360;
        return a;
    }

    // Helper: add/update/remove a small directional indicator around a marker element based on bearing
    function setDirectionIndicator(markerEl: HTMLElement, direction: any) {
        // If direction is explicitly null/undefined, remove any existing indicator and exit
        const existing = markerEl.querySelector('.dir-indicator') as HTMLElement | SVGElement | null;
        if (direction === null || direction === undefined) {
            if (existing) existing.remove();
            return;
        }
        // Keep indicator aligned to true north regardless of map rotation by compensating current bearing
        const currentBearing = (map && typeof map.getBearing === 'function') ? Number(map.getBearing()) : 0;
        const a = normAngle(Number(direction) - currentBearing);
        // If not a valid number after normalization, remove indicator and exit
        if (isNaN(a)) {
            if (existing) existing.remove();
            return;
        }
        const svgSize = 20; // px
        const apexOffset = svgSize / 2; // distance from center to tip in our 20x20 viewBox
        // Radius from marker center to triangle tip: marker radius + small gap
        const markerRadius = Math.max(markerEl.offsetWidth || 28, markerEl.offsetHeight || 28) / 2;
        const gap = 16; // px outside the marker
        const r = markerRadius + gap;
        const angleDeg = a; // already normalized degrees

        let svg = existing as SVGSVGElement | null;
        if (!svg || svg.tagName.toLowerCase() !== 'svg') {
            // Remove non-SVG existing indicator if present
            if (existing && existing.tagName.toLowerCase() !== 'svg') existing.remove();

            svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg') as SVGSVGElement;
            svg.classList.add('dir-indicator');
            svg.setAttribute('width', String(svgSize));
            svg.setAttribute('height', String(svgSize));
            svg.setAttribute('viewBox', '0 0 20 20');
            (svg.style as any).position = 'absolute';
            (svg.style as any).pointerEvents = 'none';
            (svg.style as any).zIndex = '2';
            (svg.style as any).filter = 'drop-shadow(0 0 3px rgba(0,0,0,0.35))';

            // Triangle pointing up: tip at (10,0), base at y=14
            const tri = document.createElementNS('http://www.w3.org/2000/svg', 'polygon');
            tri.setAttribute('points', '10,0 16,14 4,14');
            tri.setAttribute('fill', '#000');
            tri.setAttribute('stroke', '#fff');
            tri.setAttribute('stroke-width', '2');
            svg.appendChild(tri);
            markerEl.appendChild(svg);
        }

        // Position at marker center, rotate to angle, then translate so the tip sits at radius r
        (svg.style as any).left = '50%';
        (svg.style as any).top = '50%';
        // Move center to marker center, rotate, then move outward by (r - apexOffset) so the tip is at radius r
        (svg.style as any).transform = `translate(-50%, -50%) rotate(${angleDeg}deg) translate(0, -${r - apexOffset}px)`;
        (svg.style as any).transformOrigin = '50% 50%';
    }

    // Helper: add/remove a centered detection icon inside the marker when explicitly requested
    function updateDetectedIcon(markerEl: HTMLElement, showDetected: boolean) {
        if (!markerEl) return;
        const existing = markerEl.querySelector('.detected-icon') as HTMLElement | null;
        if (!showDetected) {
            if (existing) existing.remove();
            return;
        }
        let icon = existing as HTMLElement | null;
        if (!icon || icon.tagName.toLowerCase() !== 'i') {
            if (existing && existing.tagName.toLowerCase() !== 'i') existing.remove();
            icon = document.createElement('i');
            icon.classList.add('detected-icon', 'bi', 'bi-exclamation-triangle-fill');
            icon.setAttribute('title', 'Detected');
            (icon.style as any).color = '#fff';
            (icon.style as any).position = 'absolute';
            (icon.style as any).left = '50%';
            (icon.style as any).top = '50%';
            (icon.style as any).transform = 'translate(-50%, -50%)';
            (icon.style as any).pointerEvents = 'none';
            (icon.style as any).zIndex = '1';
            (icon.style as any).fontSize = '14px';
            (icon.style as any).lineHeight = '1';
            (icon.style as any).textShadow = '0 0 2px rgba(0,0,0,0.35)';
            markerEl.appendChild(icon);
        }
    }

    // Helper: add/remove a small low-battery icon in the top-right corner of the marker
    function updateLowBatteryIcon(markerEl: HTMLElement, batteryPercent: any) {
        if (!markerEl) return;
        const existing = markerEl.querySelector('.bi-battery-low') as HTMLElement | null;
        const n = Number(batteryPercent);
        const show = Number.isFinite(n) && n >= 0 && n <= 20 && n !== 255;
        if (!show) {
            if (existing) existing.remove();
            return;
        }
        let icon = existing as HTMLElement | null;
        if (!icon || icon.tagName.toLowerCase() !== 'i') {
            if (existing && existing.tagName.toLowerCase() !== 'i') existing.remove();
            icon = document.createElement('i');
            icon.classList.add('bi', 'bi-battery-low');
            icon.setAttribute('title', `Battery low (${n}%)`);
            (icon.style as any).color = 'red';
            (icon.style as any).position = 'absolute';
            (icon.style as any).bottom = '10px';
            (icon.style as any).left = '15px';
            (icon.style as any).fontSize = '22px';
            (icon.style as any).lineHeight = '1';
            (icon.style as any).pointerEvents = 'none';
            (icon.style as any).zIndex = '2';
            (icon.style as any).textShadow = '0 0 2px rgba(0,0,0,0.45)';
            (icon.style as any).transform = 'rotate(-90deg)';
            markerEl.appendChild(icon);
        } else {
            icon.setAttribute('title', `Battery low (${n}%)`);
        }
    }

    // Helper: derive Bluetooth devices count from lastMeasurements
    function getBluetoothDeviceCount(lm: Record<string, any>): number {
        if (!lm || typeof lm !== 'object') return 0;
        const keys = Object.keys(lm).filter(k => /bluetooth|\bble\b/i.test(k));
        if (!keys.length) return 0;

        let best = 0;
        for (const k of keys) {
            const r: any = (lm as any)[k];
            let c = 0;
            if (typeof r === 'number' && isFinite(r) && r >= 0) {
                c = r;
            } else if (Array.isArray(r)) {
                c = r.length;
            } else if (r && typeof r === 'object') {
                const numericProps = ['devicesCount', 'deviceCount', 'count', 'num', 'n', 'value', 'val'];
                for (const p of numericProps) {
                    const v = r[p];
                    if (typeof v === 'number' && isFinite(v) && v >= 0) c = Math.max(c, v);
                }
                const arrayProps = ['devices', 'list', 'seen', 'results', 'addresses', 'macs', 'uuids'];
                for (const p of arrayProps) {
                    const v = r[p];
                    if (Array.isArray(v)) c = Math.max(c, v.length);
                    else if (typeof v === 'string' && v.trim()) {
                        const parts = v.split(/[;,\s]+/).filter(Boolean);
                        c = Math.max(c, parts.length);
                    }
                }
            }
            if (c > best) best = c;
        }
        return best;
    }

    // Helper: unified status overlay with priority:
    // 1) detectedNow (true now) overrides everything
    // 2) else tamperedSticky (linger window) shows
    // 3) else detectedSticky (linger window) shows
    // 4) else no icon
    function updateStatusIcons(markerEl: HTMLElement, detectedNow: boolean, tamperedSticky: boolean, detectedSticky: boolean) {
        if (!markerEl) return;
        const tamperedEl = markerEl.querySelector('.tampered-icon') as HTMLElement | null;
        const detectedEl = markerEl.querySelector('.detected-icon') as HTMLElement | null;

        if (detectedNow) {
            // ensure detected icon, remove tampered
            updateDetectedIcon(markerEl, true);
            if (tamperedEl) tamperedEl.remove();
            return;
        }

        if (tamperedSticky) {
            // Ensure tampered icon exists
            let icon = tamperedEl as HTMLElement | null;
            if (!icon || icon.tagName.toLowerCase() !== 'i') {
                if (tamperedEl && tamperedEl.tagName.toLowerCase() !== 'i') tamperedEl.remove();
                icon = document.createElement('i');
                icon.classList.add('tampered-icon', 'bi', 'bi-shield-exclamation');
                icon.setAttribute('title', 'Tampered');
                (icon.style as any).color = '#fff';
                (icon.style as any).position = 'absolute';
                (icon.style as any).left = '50%';
                (icon.style as any).top = '50%';
                (icon.style as any).transform = 'translate(-50%, -50%)';
                (icon.style as any).pointerEvents = 'none';
                (icon.style as any).zIndex = '1';
                (icon.style as any).fontSize = '14px';
                (icon.style as any).lineHeight = '1';
                (icon.style as any).textShadow = '0 0 2px rgba(0,0,0,0.35)';
                markerEl.appendChild(icon);
            }
            // Remove detected icon if present (priority to tampered)
            if (detectedEl) detectedEl.remove();
        } else {
            // Remove tampered icon if any
            if (tamperedEl) tamperedEl.remove();
            // Show detected icon if within sticky window, else remove
            updateDetectedIcon(markerEl, !!detectedSticky);
        }
    }

    // helper to animate marker border for detected state (blink for 5 seconds)
    function startBlink(markerEl: HTMLElement) {
        if (!markerEl) return;
        // clear any existing timer so we can extend the 5s window
        try {
            (markerEl as any)._blinkTimer && clearTimeout((markerEl as any)._blinkTimer);
        } catch {
        }

        // If an animation is already running, don't recreate it (prevents flicker) - just extend the timeout
        if (!(markerEl as any)._blinkAnim) {
            try {
                const anim = markerEl.animate([
                    {boxShadow: '0 0 0 0 rgba(0,0,0,0)', borderColor: markerEl.style.borderColor || '#fff'},
                    {boxShadow: '0 0 12px 4px rgba(220,53,69,0.9)', borderColor: '#631721'}
                ], {duration: 500, iterations: Infinity, direction: 'alternate'});
                (markerEl as any)._blinkAnim = anim;
            } catch {
            }
        }

        // ensure we stop after 5 seconds from the latest call
        try {
            (markerEl as any)._blinkTimer = setTimeout(() => {
                try {
                    (markerEl as any)._blinkAnim && (markerEl as any)._blinkAnim.cancel();
                } catch {
                }
                try {
                    delete (markerEl as any)._blinkAnim;
                } catch {
                }
                try {
                    delete (markerEl as any)._blinkTimer;
                } catch {
                }
                markerEl.style.boxShadow = '';
                markerEl.style.borderColor = '#fff';
            }, 10000);
        } catch {
        }
    }

    function stopBlink(markerEl: HTMLElement) {
        if (!markerEl) return;
        try {
            const anim = (markerEl as any)._blinkAnim;
            if (anim) anim.cancel();
        } catch {
        }
        try {
            const t = (markerEl as any)._blinkTimer;
            if (t) clearTimeout(t);
        } catch {
        }
        try {
            delete (markerEl as any)._blinkAnim;
            delete (markerEl as any)._blinkTimer;
        } catch {
        }
        markerEl.style.boxShadow = '';
        markerEl.style.borderColor = '#fff';
    }

    // Refresh all markers' direction indicators (used on map rotate/reset)
    function refreshAllDirectionIndicators() {
        try {
            markerRefs.forEach(mk => {
                const el = mk?.getElement && mk.getElement();
                const id = el?.dataset?.id;
                if (!el || !id) return;
                const node = nodes.find(n => n && n._id != null && n._id.toString() === id);
                setDirectionIndicator(el as HTMLElement, node ? (node as any).direction : undefined);
            });
        } catch {
        }
    }

    function updateMarkers() {
        if (!map) return;

        // Clear newMarkerRefs before repopulating
        newMarkerRefs.length = 0;

        markerRefs.forEach(mk => {
            const id = mk.getElement().dataset.id;
            if (id) existingMarkers[id] = mk;
        });

    nodes.filter(node => !!node.posLong && !!node.posLat)
            .filter(node => isVisibleCoord(node.posLong, node.posLat))
            .forEach(node => {
                const nodeId = node._id.toString();
                let mk = existingMarkers[nodeId];

                // Determine linger state to decide sensor visibility window
                const lastTampered = lastTamperedAtByNode[nodeId] ?? 0;
                const lastDetected = lastDetectedAtByNode[nodeId] ?? 0;
                const tamperedSticky = !!(node as any).tampered || (Date.now() - lastTampered) < TOTAL_LINGER_MS;
                const detectedNow = !!(node as any).detected;
                const detectedSticky = detectedNow || ((Date.now() - lastDetected) < TOTAL_LINGER_MS);
                const showSensors = detectedNow || tamperedSticky || detectedSticky; // red/orange period

                const battNum = Number((node as any).battery);
                const batteryInfo = Number.isFinite(battNum)
                    ? `<div style="margin-top:4px;font-size:12px;opacity:.85">
        ${battNum === 255 ? 'Charging' : `${battNum}%`}
        <i class="bi bi-battery-full" style="margin-right:4px;"></i>
      </div>`
                    : '';
        // Status line (Detected/Tampered) shown during red/orange linger window
        let statusInfo = '';
        if (detectedNow) {
            statusInfo = `<div>Status: Detected</div>`;
        } else if (tamperedSticky) {
            statusInfo = `<div>Status: Tampered</div>`;
        } else if (detectedSticky) {
            statusInfo = `<div>Status: Detected</div>`;
        }
        // Active sensors now or sticky within linger window
        const lm = ((node as any).lastMeasurements ?? {}) as Record<string, any>;
        const sensorsNow = Object.entries(lm)
            .filter((e: any) => e && e[1] && (e[1].detected || e[1].tampered))
            .map((e: any) => e[0]);
        const bySensor = lastSensorActiveAtByNode[nodeId] ?? {};
        const nowTs = Date.now();
        const sensorsSticky = Object.entries(bySensor)
            .filter(([, ts]) => typeof ts === 'number' && (nowTs - (ts as number)) < TOTAL_LINGER_MS)
            .map(([name]) => name as string);
        const effectiveSensors = sensorsNow.length ? sensorsNow : sensorsSticky;
        const bleCount = getBluetoothDeviceCount(lm);
        const bleInfo = bleCount > 0
            ? `<div>Bluetooth devices: ${bleCount}</div>`
            : '';
        let sensorsInfo = '';
        if (showSensors && effectiveSensors.length) {
            if (effectiveSensors.length > 1) {
                const items = effectiveSensors.map(s => `<li>${s}</li>`).join('');
                sensorsInfo = `<div style="margin-top:4px;font-size:12px;opacity:.85">Sensors:<ul style="margin:4px 0 0 16px;">${items}</ul></div>`;
            } else {
                sensorsInfo = `<div style="margin-top:4px;font-size:12px;opacity:.85">Sensor: ${effectiveSensors[0]}</div>`;
            }
        }
                const html = `
        <div style="min-width:210px">
            <strong>${node.displayName}</strong>
            ${statusInfo}
            ${batteryInfo}
            ${bleInfo}
        ${sensorsInfo}
            <div style="margin-top:6px;font-size:12px;opacity:.7">
                Lat: ${Number(node.posLat).toFixed(7)} Long: ${Number(node.posLong).toFixed(7)}
            </div>
            <a id="switchToEvent${nodeId}" href="/events?node=${node._id}" style="margin-top:8px" target="_blank" rel="noopener">Alle Events von ${node.displayName}</a>
        </div>`;

                const bg = getMarkerBackground(node);

                if (mk) {
                    // Update marker color if needed
                    const markerEl = mk.getElement();
                    markerEl.style.background = bg;
                    // Determine overlays (reuse computed)
                    updateStatusIcons(markerEl, detectedNow, tamperedSticky, detectedSticky);
                    // Low battery icon overlay (top-right)
                    updateLowBatteryIcon(markerEl, battNum);
                    // Update popup content if needed
                    const wasOpen = mk.getPopup().isOpen();
                    mk.setPopup(new ML.Popup({offset: 24}).setHTML(html));
                    // Restore popup open state
                    if (wasOpen) mk.getPopup().addTo(map);
                    // Update marker position if changed
                    mk.setLngLat([node.posLong, node.posLat]);
                    // Update or remove direction indicator
                    setDirectionIndicator(markerEl, (node as any).direction);
                    // Blink border only if not gray; stop immediately when gray
                    if (bg === '#878787') {
                        // ensure no blink for gray markers
                        stopBlink(markerEl);
                    } else if (node.detected || node.tampered) {
                        // start or extend blink
                        startBlink(markerEl);
                    } else {
                        // If a blink is already active (within its 5s window), keep it running
                        const hasBlink = !!((markerEl as any)._blinkTimer || (markerEl as any)._blinkAnim);
                        if (!hasBlink) stopBlink(markerEl);
                    }
                    newMarkerRefs.push(mk);
                } else {
                    // Create new marker
                    const markerEl = document.createElement('div');
                    if (isMeshNode(node)) {
                        markerEl.style.borderRadius = '50%';
                    }

                    markerEl.style.border = '2px solid #fff';
                    markerEl.style.width = '28px';
                    markerEl.style.height = '28px';
                    markerEl.style.background = bg;
                    markerEl.dataset.id = nodeId;

                    // add direction indicator if provided
                    setDirectionIndicator(markerEl, (node as any).direction);
                    // add status icon
                    updateStatusIcons(markerEl, detectedNow, tamperedSticky, detectedSticky);
                    // add low-battery icon if needed
                    updateLowBatteryIcon(markerEl, battNum);

                    mk = new ML.Marker({element: markerEl}).setLngLat([node.posLong, node.posLat]);
                    mk.setPopup(new ML.Popup({offset: 24}).setHTML(html));
                    mk.addTo(map);

                    // start blink only if detected and not gray
                    if ((node.detected || node.tampered) && bg !== '#878787') startBlink(markerEl);

                    mk.getPopup().on('open', () => {
                        const btn = document.getElementById(`switchToEvent${nodeId}`);
                        if (btn) {
                            btn.onclick = () => window.open(`/events?node=${node._id}`, "_blank");
                        }
                    });
                    newMarkerRefs.push(mk);
                }
            });

        // Remove markers that are no longer needed
        markerRefs.forEach(mk => {
            const id = mk.getElement().dataset.id;
            if (!nodes.some(node => node._id.toString() === id)) {
                // stop any blinking before removal
                try {
                    stopBlink(mk.getElement());
                } catch {
                }
                mk.remove();
            }
        });

        markerRefs = [...newMarkerRefs];
    }
    function toggle2D3D() {
        if (!map) return;
        if (is3D) {
            map.setPitch(0); // 2D
            is3D = false;
    } else {
            map.setPitch(60); // 3D
            is3D = true;
        }
    }

    function resetNorth() {
        if (!map) return;
        map.resetNorth();
    }

    // Zoom map to include all markers (fit bounds). If only one marker, fly to it.
    function zoomToMarkers() {
        if (!map) return;
        const pts = nodes
            .filter(n => n.posLong !== undefined && n.posLat !== undefined && !isNaN(Number(n.posLong)) && !isNaN(Number(n.posLat)) && isVisibleCoord(n.posLong, n.posLat))
            .map(n => [Number(n.posLong), Number(n.posLat)] as [number, number]);

        if (pts.length === 0) return;
        if (pts.length === 1) {
            map.flyTo({center: pts[0], zoom: Math.max(12, map.getZoom() || 12), duration: 600});
            return;
        }

        let minLng = Infinity, minLat = Infinity, maxLng = -Infinity, maxLat = -Infinity;
        pts.forEach(([lng, lat]) => {
            if (lng < minLng) minLng = lng;
            if (lat < minLat) minLat = lat;
            if (lng > maxLng) maxLng = lng;
            if (lat > maxLat) maxLat = lat;
        });

        // Expand bounds a little to avoid markers at exact edge
        const padFactor = 0.02; // 2% padding
        const lngSpan = (maxLng - minLng) || 0.001;
        const latSpan = (maxLat - minLat) || 0.001;
        minLng -= lngSpan * padFactor;
        maxLng += lngSpan * padFactor;
        minLat -= latSpan * padFactor;
        maxLat += latSpan * padFactor;

        map.fitBounds([[minLng, minLat], [maxLng, maxLat]], {
            padding: 80,
            maxZoom: 16,
            duration: 4000,
            easing: t => 1 - Math.pow(1 - t, 10)
        });
    }

    // Update bearing on map rotate
    $effect(() => {
        if (map) {
            map.setCenter(center);
            if (typeof zoom === 'number') map.setZoom(zoom);
            map.on('rotate', () => {
                bearing = map.getBearing();
                refreshAllDirectionIndicators();
            });
            map.on('resetnorth', () => {
                bearing = 0;
                refreshAllDirectionIndicators();
            });
            map.on('pitch', () => {
                is3D = map.getPitch() > 0;
            });
        }
    });

    // Handle checkbox change for layer visibility from template
    function handleLayerToggle(e: Event, id: string) {
        const input = e.currentTarget as HTMLInputElement;
        const checked = input.checked;
        visMap[id] = checked;
        applyVisibility(id, checked);
    }

    // Reset view to default center and zoom
    function resetView() {
        if (!map) return;

        // Stop any ongoing animation
        map.stop();

        const defaultCenter: [number, number] = [8.2267, 46.8011];
        const defaultZoom = 6.5;

        // Force north-up 2D view immediately (no 3D / no bearing animation)
        map.setPitch(0);
        map.setBearing(0);

        map.flyTo({
            center: defaultCenter,
            zoom: defaultZoom,
            bearing: 0,
            pitch: 0,
            duration: 800,
            essential: true
        });

        const applyState = () => {
            center = defaultCenter;
            zoom = defaultZoom;
            bearing = 0;
            is3D = false;
        };

        if (map.isMoving()) {
            map.once('moveend', applyState);
        } else {
            applyState();
        }
    }

    // --- Line Drawing Feature State ---
    let drawingMode = $state(false);
    let drawnFeatures: any[] = $state([]); // stored finished LineString features
    let currentLine: [number, number][] = $state([]); // coordinates being drawn
    // Deletion mode for individual lines
    let deleteLineMode = $state(false);
    let lineIdCounter = 0;
    let deleteHoverHandler: any = null;
    // Neue State-Variable für Linienfarbe
    let drawColor: string = $state('#ff0000');
    // Flag to prevent double-loading (e.g. during hot reload)
    let initialLinesLoaded = false;

    // --- Text Annotation Feature State ---
    let annotationMode = $state(false);
    let textAnnotations: any[] = $state([]); // { _id, text, coordinates:[lng,lat], color }
    let textIdCounter = 0;

    function toggleAnnotationMode() {
        if (!map) return;
        // disable other modes
        if (drawingMode) finalizeCurrentLine();
        if (deleteLineMode) toggleDeleteLineMode();
        annotationMode = !annotationMode;
        try {
            map.getCanvas().style.cursor = annotationMode ? 'text' : '';
        } catch {
        }
    }

    function updateTextSource() {
        if (!map) return;
        const features = textAnnotations.map(a => ({
            type: 'Feature',
            properties: {_id: a._id, text: a.text, color: a.color || '#000000'},
            geometry: {type: 'Point', coordinates: a.coordinates}
        }));
        const data = {type: 'FeatureCollection', features} as any;
        const src: any = map.getSource && map.getSource('text-annotations');
        if (src) {
            src.setData(data);
        } else {
            try {
                map.addSource('text-annotations', {type: 'geojson', data});
                map.addLayer({
                    id: 'text-annotations',
                    type: 'symbol',
                    source: 'text-annotations',
                    layout: {
                        'text-field': ['get', 'text'],
                        'text-size': 16,
                        'text-font': ['Open Sans Regular', 'Arial Unicode MS Regular'],
                        'text-offset': [0, -1.2],
                        'text-anchor': 'bottom'
                    },
                    paint: {
                        'text-color': ['coalesce', ['get', 'color'], '#000000'],
                        'text-halo-color': '#ffffff',
                        'text-halo-width': 1.2
                    }
                });
            } catch (e) { /* layer might already exist */
            }
        }
    }

    function loadExistingLines() {
        if (initialLinesLoaded) return;
        initialLinesLoaded = true;
        try {
            let ml: any = data?.mapLines;
            if (!ml) {
            } else {
                // Accept JSON string
                if (typeof ml === 'string') {
                    try {
                        ml = JSON.parse(ml);
                    } catch {
                        ml = null;
                    }
                }
                // Accept direct feature collection or array
                let feats: any[] = [];
                if (Array.isArray(ml)) {
                    feats = ml;
                } else if (ml && typeof ml === 'object') {
                    if (Array.isArray(ml.features)) feats = ml.features;
                }
                if (feats && feats.length) {
                    const newFeatures: any[] = [];
                    let maxId = lineIdCounter;
                    feats.forEach((f: any) => {
                        if (!f || !f.geometry || f.geometry.type !== 'LineString' || !Array.isArray(f.geometry.coordinates)) return;
                        const coords = f.geometry.coordinates.filter((c: any) => Array.isArray(c) && c.length === 2 && !isNaN(c[0]) && !isNaN(c[1]));
                        if (coords.length < 2) return;
                        const props = {...(f.properties || {})};
                        if (props['_id'] == null) props['_id'] = ++maxId; else if (typeof props['_id'] === 'number') maxId = Math.max(maxId, props['_id']);
                        if (!props['color'] || typeof props['color'] !== 'string') props['color'] = '#ff0000';
                        newFeatures.push({
                            type: 'Feature',
                            properties: props,
                            geometry: {type: 'LineString', coordinates: coords}
                        });
                    });
                    if (newFeatures.length) {
                        drawnFeatures = [...drawnFeatures, ...newFeatures];
                        lineIdCounter = Math.max(lineIdCounter, ...newFeatures.map(f => f.properties._id));
                        updateDrawSource();
                    }
                }
            }
            // Load existing text annotations (mapText)
            let mt: any = data?.mapText;
            if (mt) {
                if (typeof mt === 'string') {
                    try {
                        mt = JSON.parse(mt);
                    } catch {
                        mt = null;
                    }
                }
                let tfeats: any[] = [];
                if (Array.isArray(mt)) {
                    tfeats = mt;
                } else if (mt && typeof mt === 'object' && Array.isArray(mt.features)) {
                    tfeats = mt.features;
                }
                if (tfeats && tfeats.length) {
                    let maxTid = textIdCounter;
                    const newTexts: any[] = [];
                    tfeats.forEach((f: any) => {
                        if (!f || !f.geometry || f.geometry.type !== 'Point') return;
                        const coords = f.geometry.coordinates;
                        if (!Array.isArray(coords) || coords.length !== 2 || isNaN(coords[0]) || isNaN(coords[1])) return;
                        const props = {...(f.properties || {})};
                        if (!props['text']) return;
                        if (props['_id'] == null) props['_id'] = ++maxTid; else if (typeof props['_id'] === 'number') maxTid = Math.max(maxTid, props['_id']);
                        if (!props['color'] || typeof props['color'] !== 'string') props['color'] = '#000000';
                        newTexts.push({
                            _id: props['_id'],
                            text: props['text'],
                            color: props['color'],
                            coordinates: coords
                        });
                    });
                    if (newTexts.length) {
                        textAnnotations = [...textAnnotations, ...newTexts];
                        textIdCounter = Math.max(textIdCounter, ...newTexts.map(t => t._id));
                        updateTextSource();
                    }
                }
            }
        } catch (e) {
            console.warn('Konnte bestehende Linien oder Texte nicht laden:', e);
        }
    }

    function updateDeleteHoverHandler() {
        if (!map) return;
        // Remove existing handler first
        if (deleteHoverHandler) {
            map.off('mousemove', deleteHoverHandler);
            deleteHoverHandler = null;
        }
        if (!deleteLineMode) {
            // Reset cursor if we leave delete mode and not drawing
            if (!drawingMode) {
                try {
                    map.getCanvas().style.cursor = '';
                } catch {
                }
            }
            return;
        }
        // Create new handler
        deleteHoverHandler = (e: any) => {
            if (!deleteLineMode || drawingMode) return; // ignore while drawing
            try {
                const feats = map.queryRenderedFeatures(e.point, {layers: ['drawn-lines', 'text-annotations']});
                const hasDeletable = feats && feats.some(f => (f.layer?.id === 'drawn-lines' && !f.properties?.temp) || f.layer?.id === 'text-annotations');
                map.getCanvas().style.cursor = hasDeletable ? 'pointer' : '';
            } catch {
                try {
                    map.getCanvas().style.cursor = '';
                } catch {
                }
            }
        };
        map.on('mousemove', deleteHoverHandler);
    }

    function updateDrawSource() {
        if (!map) return;
        const features = drawnFeatures.map(f => f); // clone existing finished features
        if (drawingMode && currentLine.length >= 2) {
            features.push({
                type: 'Feature',
                properties: {temp: true, color: drawColor}, // temp feature with current color
                geometry: {type: 'LineString', coordinates: currentLine}
            });
        }
        const data = {type: 'FeatureCollection', features} as any;
        const src: any = map.getSource && map.getSource('drawn-lines');
        if (src) {
            src.setData(data);
            try {
                // Ensure layer uses data-driven color expression
                if (map.getLayer('drawn-lines')) {
                    map.setPaintProperty('drawn-lines', 'line-color', ['coalesce', ['get', 'color'], '#ff0000']);
                }
            } catch {
            }
        } else {
            try {
                map.addSource('drawn-lines', {type: 'geojson', data});
                map.addLayer({
                    id: 'drawn-lines',
                    type: 'line',
                    source: 'drawn-lines',
                    paint: {
                        'line-color': ['coalesce', ['get', 'color'], '#ff0000'],
                        'line-width': 5,
                        'line-opacity': 0.9
                    }
                });
            } catch (e) {
                // source may already exist if hot reloaded
            }
        }
        try {
            if (drawingMode) {
                map.getCanvas().style.cursor = 'crosshair';
            } else if (!deleteLineMode) {
                map.getCanvas().style.cursor = '';
            }
        } catch {
        }
    }

    function finalizeCurrentLine() {
        if (!map) return;
        if (currentLine.length >= 2) {
            drawnFeatures = [
                ...drawnFeatures,
                {
                    type: 'Feature',
                    properties: {_id: ++lineIdCounter, color: drawColor},
                    geometry: {type: 'LineString', coordinates: currentLine}
                }
            ];
        }
        currentLine = [];
        drawingMode = false;
        try {
            if (!deleteLineMode) {
                map.getCanvas().style.cursor = '';
            }
        } catch {
        }
        updateDeleteHoverHandler();
        updateDrawSource();
        // autosave after adding a line
        scheduleSave();
    }

    function toggleDrawing() {
        if (!map) return;
        if (deleteLineMode) {
            // If deletion mode active, disable it before drawing
            toggleDeleteLineMode();
        }
        if (drawingMode) {
            // finish drawing
            finalizeCurrentLine();
        } else {
            drawingMode = true;
            currentLine = [];
            try {
                map.getCanvas().style.cursor = 'crosshair';
            } catch {
            }
            try {
                map.doubleClickZoom && map.doubleClickZoom.disable();
            } catch {
            }
            updateDrawSource();
        }
    }

    function cancelDrawing() {
        if (!drawingMode) return;
        currentLine = [];
        drawingMode = false;
        try {
            if (!deleteLineMode) {
                map.getCanvas().style.cursor = '';
            }
        } catch {
        }
        updateDeleteHoverHandler();
        updateDrawSource();
    }

    function toggleDeleteLineMode() {
        if (!map) return;
        if (drawingMode) {
            // Cancel current drawing (do not finalize) when switching to delete mode
            currentLine = [];
            drawingMode = false;
        }
        deleteLineMode = !deleteLineMode;
        try {
            if (!deleteLineMode) {
                map.getCanvas().style.cursor = drawingMode ? 'crosshair' : '';
            }
        } catch {
        }
        updateDeleteHoverHandler();
        updateDrawSource();
    }

    async function saveLines() {
        // Always persist (even empty) without alert
        const featureCollection = {
            features: drawnFeatures.map(f => ({
                properties: f.properties || {},
                geometry: {type: 'LineString', coordinates: f.geometry.coordinates}
            }))
        };
        const textFeatureCollection = {
            features: textAnnotations.map(t => ({
                properties: {_id: t._id, text: t.text, color: t.color},
                geometry: {type: 'Point', coordinates: t.coordinates}
            }))
        };
        try {
            await fetch(`/api/config/`, {
                method: 'PATCH',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({mapLines: featureCollection, mapText: textFeatureCollection})
            });
        } catch (e) {
            console.error('Fehler beim Speichern der Linien / Texte:', e);
        }
    }

    // Debounce wrapper to avoid flooding server
    let saveDebounceHandle: any = null;

    function scheduleSave() {
        if (saveDebounceHandle) clearTimeout(saveDebounceHandle);
        saveDebounceHandle = setTimeout(() => {
            saveDebounceHandle = null;
            saveLines();
        }, 400); // 400ms debounce
    }
</script>

<div>
    <div style="height: 84vh; overflow: hidden;">
        <div style="position:relative; width:100%; height:100%" bind:this={mapEl}>
            <!-- Layer Control Button -->
            {#if showLayerControlButton}
                <button
                        class="layer-control-btn"
                        onclick={toggleLayerControl}
                        title="Toggle layer control"
                        style="font-size: 2em;">
                    🗂️
                </button>
            {/if}

            <div style="position:absolute;top:10px;left:10px;z-index:1001;display:flex;flex-direction:column;gap:8px;">
                <button class="zoom-btn" onclick={() => map?.zoomIn()} title="Zoom in">+</button>
                <button class="zoom-btn" onclick={() => map?.zoomOut()} title="Zoom out">−</button>
                <button class="zoom-btn" onclick={zoomToMarkers} title="Zoom to markers" aria-label="Zoom to markers">
                    <svg width="22" height="22" viewBox="0 0 24 24" role="img" aria-hidden="true">
                        <circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="2" fill="none"/>
                        <line x1="12" y1="3" x2="12" y2="7" stroke="currentColor" stroke-width="2"
                              stroke-linecap="round"/>
                        <line x1="12" y1="17" x2="12" y2="21" stroke="currentColor" stroke-width="2"
                              stroke-linecap="round"/>
                        <line x1="3" y1="12" x2="7" y2="12" stroke="currentColor" stroke-width="2"
                              stroke-linecap="round"/>
                        <line x1="17" y1="12" x2="21" y2="12" stroke="currentColor" stroke-width="2"
                              stroke-linecap="round"/>
                        <circle cx="12" cy="12" r="2.5" fill="currentColor"/>
                    </svg>
                </button>
                <button class="zoom-btn" onclick={resetView} title="Reset view" aria-label="Reset view">🌍
                </button>
                <button aria-label="Reorient North"
                        class="zoom-btn compass-btn"
                        onclick={resetNorth}
                        title="Reorient North">
                    <svg
                            width="24"
                            height="24"
                            viewBox="0 0 24 24"
                            style="transform: rotate({-bearing}deg); transition: transform 0.2s;">
                        <circle cx="12" cy="12" r="10" fill="#fff" stroke="#888" stroke-width="2"/>
                        <polygon points="12,4 14,12 12,10 10,12" fill="#e53935"/>
                        <polygon points="12,20 10,12 12,14 14,12" fill="#1976d2"/>
                    </svg>
                </button>
                <button class="zoom-btn" onclick={toggle2D3D} title="Toggle 2D/3D">{is3D ? '2D' : '3D'}</button>
                <!-- Farbwahl für Linien unter dem Stift -->
                <div style="display:flex;flex-direction:column;align-items:center;gap:4px;">
                    <input
                            type="color"
                            bind:value={drawColor}
                            oninput={updateDrawSource}
                            title="Linienfarbe (nur für aktuelle Linie / Text)"
                            style="width:40px;height:40px;padding:0;border:1px solid #888;border-radius:4px;cursor:pointer;"/>
                </div>
                <button class="zoom-btn" onclick={toggleAnnotationMode}
                        title={annotationMode ? 'Textmodus beenden (Esc)' : 'Text hinzufügen'}
                        style={annotationMode ? 'background:#ffd54f;color:#000;' : ''}>
                    {#if annotationMode}T*{:else}T{/if}
                </button>
                <button class="zoom-btn" onclick={toggleDrawing}
                        title={drawingMode ? 'Linienzeichnen beenden (Doppelklick oder Enter)' : 'Linie zeichnen (Klicken, Doppelklick beendet)'}>
                    {#if drawingMode}
                    <span style="display:inline-flex;align-items:center;gap:4px;">
                        <!-- Stift ablegen Icon -->
                        <svg width="20" height="20" viewBox="0 0 24 24" aria-label="Stift ablegen" role="img">
                            <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25Z" fill="#000"/>
                            <path d="M20.71 7.04c.39-.39.39-1.02 0-1.41L18.37 3.29a.9959.9959 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83Z"
                                  fill="#000"/>
                            <path d="M4 22h16v-2H4v2Z" fill="#000"/>
                        </svg>
                    </span>
                    {:else}
                    <span style="display:inline-flex;align-items:center;gap:4px;">
                        <!-- Stift (Zeichnen) Icon - now green -->
                        <svg width="20" height="20" viewBox="0 0 24 24" aria-label="Linie zeichnen" role="img">
                            <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25Z" fill="#000"/>
                            <path d="M20.71 7.04c.39-.39.39-1.02 0-1.41L18.37 3.29a.9959.9959 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83Z"
                                  fill="#000"/>
                        </svg>
                    </span>
                    {/if}
                </button>
                {#if drawingMode}
                    <div aria-live="polite" role="status"
                         style="position:absolute;top:10px;left:86px;z-index:1002;background:rgba(255,255,255,0.95);padding:8px 10px;border-radius:6px;box-shadow:0 2px 6px rgba(0,0,0,0.15);font-size:13px;line-height:1.2;color:#111;">
                        <div style="display:flex;align-items:center;gap:8px;">
                            <div style="width:14px;height:14px;border-radius:3px;background: {drawColor};border:1px solid #888;"></div>
                            <strong>Zeichnen aktiv</strong>
                        </div>
                        <div style="width:10rem;margin-top:6px;opacity:0.9;display:flex;flex-direction:column;gap:4px">
                            <div>Klicken = Punkt hinzufügen</div>
                            <div>Doppelklick / Enter = Fertig</div>
                            <div>Esc = Abbrechen</div>
                        </div>
                    </div>
                {/if}
                <!-- Neuer Button: Linien löschen Modus -->
                <button class="zoom-btn" onclick={toggleDeleteLineMode}
                        title={deleteLineMode ? 'Linien-Löschmodus beenden (Esc)' : 'Linie löschen (Klicken Sie auf eine Linie)'}>
                    {#if deleteLineMode}
                        <span style="display:inline-flex;align-items:center;gap:4px;">
                            <!-- Active delete icon (trash open) -->
                            <svg width="20" height="20" viewBox="0 0 24 24" aria-label="Löschmodus aktiv" role="img">
                                <path fill="#c62828" d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V9H6v10z"/>
                                <path fill="#ef5350" d="M19 6h-3.5l-1-1h-5l-1 1H5v2h14z"/>
                            </svg>
                        </span>
                    {:else}
                        <span style="display:inline-flex;align-items:center;gap:4px;">
                            <!-- Inactive delete icon -->
                            <svg width="20" height="20" viewBox="0 0 24 24" aria-label="Linie löschen" role="img">
                                <path fill="#555" d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V9H6v10z"/>
                                <path fill="#888" d="M19 6h-3.5l-1-1h-5l-1 1H5v2h14z"/>
                            </svg>
                        </span>
                    {/if}
                </button>
            </div>
            <!-- Layer Control Panel -->
            {#if showLayerControl}
                <div class="layer-control-panel">
                    <div class="layer-control-header">
                        <h3>Map Layers</h3>
                        <button class="close-btn" onclick={toggleLayerControl}>×</button>
                    </div>
                    <div class="layer-list">
                        {#each toggles as t (t.id)}
                            <label class="layer-item">
                                <input
                                        type="checkbox"
                                        checked={visMap[t.id]}
                                        onchange={(e) => handleLayerToggle(e, t.id)}
                                />
                                <span class="color-indicator"
                                      style="background-color: {layerColors[t.id] || '#888888'}"></span>
                                <span class="layer-name">{t.label}</span>
                            </label>
                        {/each}
                    </div>
                </div>
            {/if}
        </div>
    </div>
    <div class="container-fluid">
        <div class="road-legend">
            <div class="row align-items-center g-2 legend-row">
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:6px;"></span> Autobahn
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:5px;"></span> Autostrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:4px;"></span> Hauptstrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:3px;"></span> Nebenstrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:2px;"></span> Regionalstrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:2px;"></span> Wohnstrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <span class="legend-line" style="height:1px;"></span> Zufahrtsstrasse
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <i class="detected-icon bi bi-exclamation-triangle-fill" title="Detected" style="margin-right:6px;"></i> Detected
                    </div>
                </div>
                <div class="col-12 col-sm-6 col-md-auto">
                    <div class="legend-item">
                        <i class="tampered-icon bi bi-shield-exclamation" title="Tampered" style="margin-right:6px;"></i> Tampered
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
