import webFramework.annotations.WebController;
import webFramework.annotations.Get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebController
public class Controller {
    private User user = new User("frank", "meier", "Franer", "0427177711");

    @Get(path = "/", contentType = "text/html")
    public String showHTML() {
        ClassLoader classLoader = Controller.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("index.html")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                return reader.lines().collect(Collectors.joining("\n"));

            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return "Error reading file!";
        }
    }

    @Get(path = "/user")
    public String user() {
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        return serializer.convertToJson(user);
    }
}