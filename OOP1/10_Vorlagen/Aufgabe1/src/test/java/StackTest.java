
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StackTest {

	@Test
	public void testStackNullPushThrowsException() {
		var stack = new Stack(1);

		// Die () -> { ... } Syntax ist eine Lambda-Funktion und wird nächste
		// Woche im Detail behandelt.
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			stack.push(null);
		});
	}

	@Test
	public void testStackNullPushDoesNotCorruptStack() throws StackException {
		var stack = new Stack(1);

		try {
			stack.push(null);
		} catch (IllegalArgumentException e) {
			// Ignore
		}
		stack.push("A");

		assertEquals("A", stack.pop());
	}

	@Test
	public void testStackDoesNotAllowConstructionWithNegativeCapacity() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Stack(-1);
		});
	}

	@Test
	public void testStackDoesNotAllowConstructionWithTooHighCapacity() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Stack(65536 + 1);
		});
	}

	@Test
	public void testStackDoesNotOverflow() throws StackOverflowException {
		var stack = new Stack(1);
		stack.push("A");

		Assertions.assertThrows(StackOverflowException.class, () -> {
			stack.push("Too much");
		});
	}

	@Test
	public void testStackDoesStillWorkAfterAnOverflow() throws StackOverflowException, StackUnderflowException {
		var stack = new Stack(1);
		stack.push("A");

		try {
			stack.push("Too much");
		} catch (StackOverflowException e) {
			// Ignore
		}

		assertEquals("A", stack.pop());
	}

	@Test
	public void testStackDoesNotUnderflow() throws StackOverflowException {
		var stack = new Stack(1);

		Assertions.assertThrows(StackUnderflowException.class, () -> {
			stack.pop();
		});
	}

	@Test
	public void testStackDoesStillWorkAfterAnUnderflow() throws StackOverflowException, StackUnderflowException {
		var stack = new Stack(1);

		try {
			stack.pop();
		} catch (StackUnderflowException e) {
			// Ignore
		}

		stack.push("A");
		assertEquals("A", stack.pop());
	}
}
