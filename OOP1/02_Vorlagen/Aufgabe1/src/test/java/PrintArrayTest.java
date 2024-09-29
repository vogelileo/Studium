import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintArrayTest {

	private final ByteArrayOutputStream outTestStream = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outTestStream));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}

	@Test
	void testPrintEmptyArray() {
		int[] empty = {};
		ArrayFunctions.print(empty);
		assertEquals(String.format("[]%n"), outTestStream.toString());
	}

	@Test
	void testPrintArray() {
		int[] values = { 1, 2, 3 };
		ArrayFunctions.print(values);
		assertEquals(String.format("[1, 2, 3]%n"), outTestStream.toString());
	}

	@Test
	void testPrintNullArray() {
		ArrayFunctions.print(null);
		assertEquals(String.format("null%n"), outTestStream.toString());
	}
}
