package webFramework;

import webFramework.annotations.WebController;
import webFramework.annotations.Get;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class WebFramework {
    private final Map<String, WebHandler<Object>> routeHandlers = new HashMap<>();

    public void startServer(int port) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server running on port " + port);

            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleRequest(client)).start();
            }
        }
    }

    public void addController(Class<?> controllerClass) throws Exception {
        if (!controllerClass.isAnnotationPresent(WebController.class)) {
            throw new IllegalArgumentException("Class is not a WebController");
        }

        var controllerInstance = controllerClass.getDeclaredConstructor().newInstance();

        // Scan for methods with @WebRoute
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Get.class)) {
                Get route = method.getAnnotation(Get.class);
                routeHandlers.put(route.path(), new WebHandler<>(method, controllerInstance, route.contentType()));
            }
        }
        System.out.println(routeHandlers.keySet());
    }

    private String getResponse(String path) throws Exception {
        if (routeHandlers.containsKey(path)) {
            WebHandler<Object> handler = routeHandlers.get(path);
            return (String) handler.method().invoke(handler.instance());
        } else {
            return null;
        }
    }

    private void handleRequest(Socket client) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer = new PrintWriter(client.getOutputStream(), true)) {

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            System.out.println("Request: " + requestLine);
            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String path = parts[1];

            if (routeHandlers.containsKey(path)) {
                var response = getResponse(path);
                String httpResponse =
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: "+ routeHandlers.get(path).contentType() +"\r\n" +
                                "Connection: close\r\n" ;
                writer.println(httpResponse);
                writer.println(response);
            } else {
                String httpResponse =
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Type: application/json\r\n" +
                                "Connection: close\r\n" +
                                "\r\n";
                writer.println(httpResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
