import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StackTests {

    @Test
    public void TestPush() {
        Stack<Integer> stack = new ListStack<>(2);
        stack.push(3);
        Assertions.assertEquals(3, stack.top());
        Assertions.assertEquals(1, stack.size());
        Assertions.assertFalse(stack.isEmpty());
        stack.push(5);
        Assertions.assertEquals(5, stack.top());
        Assertions.assertEquals(2, stack.size());
        Assertions.assertFalse(stack.isEmpty());

        Assertions.assertThrows(StackOverflowException.class, () -> stack.push(4));
    }

    @Test
    public void TestSize() {
        Stack<Integer> stack = new ListStack<>(11);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            Assertions.assertEquals(i + 1, stack.size());
        }
    }

    @Test
    public void TestTop() {
        Stack<Integer> stack = new ListStack<>(11);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            Assertions.assertEquals(i, stack.top());
        }
    }

    @Test
    public void TestTopException() {
        Stack<Integer> stack = new ListStack<>(2);
        Assertions.assertThrows(EmptyStackException.class, stack::top);
    }

    @Test
    public void TestPop() {
        Stack<Integer> stack = new ListStack<>(5);
        stack.push(10);
        stack.push(1);
        stack.push(4);
        Assertions.assertEquals(3, stack.size());
        Assertions.assertEquals(4, stack.pop());
        Assertions.assertEquals(2, stack.size());
        Assertions.assertEquals(1, stack.pop());
        Assertions.assertEquals(1, stack.size());
        Assertions.assertEquals(10, stack.pop());
        Assertions.assertEquals(0, stack.size());
        Assertions.assertTrue(stack.isEmpty());

    }
}
