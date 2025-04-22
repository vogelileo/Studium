import java.util.List;
import java.util.Random;

public class DataLogger {

    private IRingBuffer ringBuffer;
    private boolean logging;
    private Random random;

    public DataLogger(int bufferSize) {
        this.ringBuffer = new RingBuffer(bufferSize);
        this.logging = false;
        this.random = new Random();
    }

    public void startLogging() {
        logging = true;
        new Thread(() -> {
            while (logging) {
                int data = generateSensorData();
                ringBuffer.append(data);
                try {
                    System.out.println(getLoggedData());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void stopLogging() {
        logging = false;
    }

    public List<Integer> getLoggedData() {
        return ringBuffer.getData();
    }

    private int generateSensorData() {
        return random.nextInt(100);
    }

}
