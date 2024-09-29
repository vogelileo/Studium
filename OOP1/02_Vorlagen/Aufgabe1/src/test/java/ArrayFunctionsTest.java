import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayFunctionsTest {

	@Test
	void testRevertExample() {
		int[] initial = { 1, 2, 3 };
		int[] expected = { 3, 2, 1 };
		assertArrayEquals(expected, ArrayFunctions.revert(initial));
	}

	@Test
	void testRevertDoesNotChangeArgument() {
		int[] initial = { 1, 2, 3 };
		ArrayFunctions.revert(initial);
		assertArrayEquals(new int[] { 1, 2, 3 }, initial);
	}

	@Test
	void testRevertEmpty() {
		int[] empty = {};
		assertArrayEquals(empty, ArrayFunctions.revert(empty));
	}

	@Test
	void testRevertNull() {
		assertNull(ArrayFunctions.revert(null));
	}

	@Test
	void testAscendinglySortedArray_IsTrue_ForSortedArray() {
		int[] input = { -1, 0, 7 };
		assertTrue(ArrayFunctions.ascendinglySorted(input));
	}

	@Test
	void testAscendinglySortedArray_IsFalse_ForUnsortedArray() {
		int[] input = { -1, -2, 7 };
		assertFalse(ArrayFunctions.ascendinglySorted(input));
	}

	@Test
	void testAscendinglySortedArray_IsTrue_ForEmptyArray() {
		int[] input = new int[] {};
		assertTrue(ArrayFunctions.ascendinglySorted(input));
	}

	@Test
	void testAscendinglySortedArray_IsFalse_ForNull() {
		assertFalse(ArrayFunctions.ascendinglySorted(null));
	}

	@Test
	void testDescendinglySortedArray_IsTrue_ForSortedArray() {
		int[] input = { 7, 0, -1 };
		assertTrue(ArrayFunctions.descendinglySorted(input));
	}

	@Test
	void testDescendinglySortedArray_IsFalse_ForUnsortedArray() {
		int[] input = { -1, -2, 7 };
		assertFalse(ArrayFunctions.descendinglySorted(input));
	}

	@Test
	void testDescendinglySortedArray_IsTrue_ForEmptyArray() {
		int[] input = {};
		assertTrue(ArrayFunctions.descendinglySorted(input));
	}

	@Test
	void testDescendinglySortedArray_IsFalse_ForNull() {
		assertFalse(ArrayFunctions.descendinglySorted(null));
	}
}
