package main.java;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SwingVisualization implements Visualization {
	@FunctionalInterface
	private interface Figure {
		void draw(Graphics g);
	}

	private final JFrame frame;
	private final Collection<Figure> figures = new ArrayList<Figure>();
	private final Canvas canvas;

	public void drawRectangle(int left, int top, int width, int height, int color) {
		SwingUtilities.invokeLater(() -> {
			figures.add(g -> {
				g.setColor(new Color(color));
				g.fillRect(left, top, width, height);
			});
			canvas.repaint();
		});
	}

	public void drawCircle(int x, int y, int radius, int color) {
		SwingUtilities.invokeLater(() -> {
			figures.add(g -> {
				g.setColor(new Color(color));
				g.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, 0, 360);
			});
			canvas.repaint();
		});
	}

	public void drawText(int x, int y, String text, int color) {
		var defaultFont = Font.decode(null);
		var defaultRenderContext = new FontRenderContext(null, false, false);
		var bounds = defaultFont.getStringBounds(text, defaultRenderContext);
		int adjustedX = (int) (x - bounds.getWidth() / 2);
		int adjustedY = (int) (y + bounds.getHeight() / 2);
		SwingUtilities.invokeLater(() -> {
			figures.add(g -> {
				g.setColor(new Color(color));
				g.drawChars(text.toCharArray(), 0, text.length(), adjustedX, adjustedY);
			});
			canvas.repaint();
		});
	}

	public void clear() {
		SwingUtilities.invokeLater(() -> {
			figures.clear();
			canvas.repaint();
		});
	}

	public SwingVisualization() {
		frame = new JFrame();
		frame.setSize(1200, 800);
		frame.setTitle("Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics graphics) {
				for (var f : figures) {
					f.draw(graphics);
				}
			}
		};
		frame.getContentPane().add(canvas);
		SwingUtilities.invokeLater(() -> frame.setVisible(true));
	}
}
