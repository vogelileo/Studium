import webFramework.WebFramework;

public class main {
    public static void main(String[] args) throws Exception {
        WebFramework framework = new WebFramework();
        framework.addController(Controller.class);
        framework.startServer(8080);
    }
}
