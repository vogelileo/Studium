import java.io.Serial;

public class EmptyQueueException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmptyQueueException(String err) {
        super(err);
    }
}