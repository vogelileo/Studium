package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.Circle;
import main.java.Shape;
import org.junit.jupiter.api.Test;

public class CircleTest {

	private static final int circleX = 2;
	private static final int circleY = 3;
	private static final int radius = 4;
	private static final int color = 0xff7700;
	private final Circle circle = new Circle(circleX, circleY, radius, color);

	@Test
	void testX() {
		assertEquals(circleX, circle.getX());
	}
	
	@Test
	void testY() {
		assertEquals(circleY, circle.getY());
	}
	
	@Test
	void testRadius() {
		assertEquals(radius, circle.getRadius());
	}
	
	@Test
	void testColor() {
		assertEquals(color, circle.getAreaColor());
	}
	
	@Test
	void testMoveMovesX() {
		Shape circle = new Circle(circleX, circleY, radius, color);
		circle.move(10, 20);
		assertEquals(12, circle.getX());
	}
	
	@Test
	void testMoveMovesY() {
		Shape circle = new Circle(circleX, circleY, radius, color);
		circle.move(10, 20);
		assertEquals(23, circle.getY());
	}
}
