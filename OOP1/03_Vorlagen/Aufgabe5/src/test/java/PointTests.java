import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PointTests {
	
	@Test
	void testConstructorSetsX() {
		Point point = new Point(2, 3);
		assertEquals(2, point.getX());
	}	
	
	@Test
	void testConstructorSetsY() {
		Point point = new Point(2, 3);
		assertEquals(3, point.getY());
	}
	
	@Test
	void testIsSameOnItself() {
		Point point = new Point(2, 3);
		assertTrue(point.isSame(point));
	}
	
	@Test
	void testIsSameOnSamePoint() {
		Point point1 = new Point(2, 3);
		Point point2 = new Point(2, 3);
		assertTrue(point1.isSame(point2));
	}
	
	@Test
	void testIsSameOnDifferentPoints() {
		Point point1 = new Point(2, 3);
		Point point2 = new Point(2, 4);
		assertFalse(point1.isSame(point2));
	}
	
	
}
