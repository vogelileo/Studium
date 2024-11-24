
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StackTransferTest {

	@Test
	public void testTransferBetweenStacksOfEqualSize() throws StackException {
		var source = new Stack(2);
		source.push("A");
		source.push("B");
		var target = new Stack(2);

		StackTransfer.transfer(source, target);

		assertTrue(source.isEmpty());
		assertTrue(target.isFull());
		assertEquals("A", target.pop());
		assertEquals("B", target.pop());
	}

	@Test
	public void testFromStackIsUnchangedIfTargetCapacityIsTooLow() throws StackException {
		var source = new Stack(2);
		source.push("A");
		source.push("B");
		var target = new Stack(1);

		assertThrows(StackTransferException.class, () -> {
			StackTransfer.transfer(source, target);
		});

		assertTrue(target.isEmpty());
		assertEquals("B", source.pop());
		assertEquals("A", source.pop());
	}

	@Test
	public void testTargetStackIsUnchangedIfCapacityIsTooLow() throws StackException {
		var source = new Stack(1);
		source.push("A");
		var target = new Stack(1);
		target.push("B");

		assertThrows(StackTransferException.class, () -> {
			StackTransfer.transfer(source, target);
		});

		assertEquals("A", source.pop());
		assertEquals("B", target.pop());
	}
}
