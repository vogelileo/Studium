import java.io.Serial;

public class EmptyPriorityQueueException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public EmptyPriorityQueueException(String err) {
    super(err);
  }
}
 
 
 
 
