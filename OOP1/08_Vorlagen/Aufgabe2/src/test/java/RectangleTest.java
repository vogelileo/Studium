package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.Rectangle;
import main.java.Shape;
import org.junit.jupiter.api.Test;

public class RectangleTest {
	private static final int left = 1;
	private static final int top = 2;
	private static final int width = 3;
	private static final int height = 4;
	private static final int color = 0x0000ff;
	private final Rectangle rectangle = new Rectangle(left, top, width, height, color);

	@Test
	void testGetX() {
		assertEquals(left, rectangle.getX());
	}

	@Test
	void testGetY() {
		assertEquals(top, rectangle.getY());
	}

	@Test
	void testGetWidth() {
		assertEquals(width, rectangle.getWidth());
	}

	@Test
	void testGetHeight() {
		assertEquals(height, rectangle.getHeight());
	}

	@Test
	void testGetColor() {
		assertEquals(color, rectangle.getAreaColor());
	}

	@Test
	void testMoveMovesX() {
		Shape rectangle =  new Rectangle(left, top, width, height, color);
		rectangle.move(10, 20);
		assertEquals(11, rectangle.getX());
	}

	@Test
	void testMoveMovesY() {
		Shape rectangle =  new Rectangle(left, top, width, height, color);
		rectangle.move(10, 20);
		assertEquals(22, rectangle.getY());
	}

	@Test
	void testResizeResizesWidth() {
		var rectangle = new Rectangle(left, top, width, height, color);
		rectangle.resize(10, 20);
		assertEquals(10, rectangle.getWidth());
	}

	@Test
	void testResizeResizesHeight() {
		var rectangle = new Rectangle(left, top, width, height, color);
		rectangle.resize(10, 20);
		assertEquals(20, rectangle.getHeight());
	}
}
