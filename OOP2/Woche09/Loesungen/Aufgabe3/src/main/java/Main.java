public class Main {


    public static void main(String[] args) throws InterruptedException {
        DataLogger logger = new DataLogger(5);
        logger.startLogging();
        Thread.sleep(5000);
        logger.stopLogging();
        System.out.println(logger.getLoggedData());
    }
}
