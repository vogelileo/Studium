import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StackTest {

	@Test
	void testNewStackIsEmpty() {
		var stack = new Stack(5);
		assertTrue(stack.isEmpty());
	}

	@Test
	void testSizeOfNewStackIsZero() {
		var stack = new Stack(5);
		assertEquals(0, stack.size());
	}

	@Test
	void testStackWithZeroCapacityIsFull() {
		var stack = new Stack(0);
		assertTrue(stack.isFull());
	}

	@Test
	void testPopOnEmptyStackReturnsNull() {
		var stack = new Stack(3);
		assertNull(stack.pop());
	}

	@Test
	void testStackHasCorrectSizeAfterInitializationWithElements() {
		var stack = new Stack(5);
		stack.push("lorem");
		stack.push("ipsum");
		stack.push("dolor");
		stack.push("sit");
		assertEquals(4, stack.size());
	}

	@Test
	void testStackContainsElementsOfInitialization() {
		var stack = new Stack(5);
		stack.push("Ein");
		stack.push("Ring");
		stack.push("sie");
		stack.push("zu");
		stack.push("knechten");
		assertEquals("knechten", stack.pop());
		assertEquals("zu", stack.pop());
		assertEquals("sie", stack.pop());
		assertEquals("Ring", stack.pop());
		assertEquals("Ein", stack.pop());
	}

	@Test
	void testStackIsEmptyAfterAllPopped() {
		var stack = new Stack(6);
		stack.push("To");
		stack.push("be");
		stack.push("or");
		stack.push("not");
		stack.push("to");
		stack.push("be");

		for (int i = 0; i < 6; i++) {
			stack.pop();
		}
		assertTrue(stack.isEmpty());
	}
}
