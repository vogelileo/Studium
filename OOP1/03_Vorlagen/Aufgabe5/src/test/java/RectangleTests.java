

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RectangleTests {
	@Test
	void testPointConstructorSetsTopLeft() {
		Rectangle r = new Rectangle(new Point(1, 2), new Point(6, 5));
		Point topLeft = r.getTopLeft();
		assertEquals(1, topLeft.getX());
		assertEquals(2, topLeft.getY());
	}
	
	@Test
	void testPointConstructorSetsBottomRight() {
		Rectangle r = new Rectangle(new Point(1, 2), new Point(6, 5));
		Point bottomRight = r.getBottomRight();
		assertEquals(6, bottomRight.getX());
		assertEquals(5, bottomRight.getY());
	}
	
	@Test
	void testSquareConstructorSetsTopLeft() {
		Rectangle r = new Rectangle(new Point(1, 2), 2);
		Point topLeft = r.getTopLeft();
		assertEquals(1, topLeft.getX());
		assertEquals(2, topLeft.getY());
	}
	
	@Test
	void testSquareConstructorCalculatesBottomRight() {
		Rectangle r = new Rectangle(new Point(1, 2), 2);
		Point bottomRight = r.getBottomRight();
		assertEquals(3, bottomRight.getX());
		assertEquals(4, bottomRight.getY());
	}
	
	@Test
	void testIsSame() {
		Rectangle r1 = new Rectangle(new Point(2, 2), new Point(4, 4));
		Rectangle r2 = new Rectangle(new Point(2, 2), 2);
		assertTrue(r1.isSame(r2));
		assertTrue(r2.isSame(r1));
	}
	
	@Test
	void testIsSquareForSquareIsTrue() {
		Rectangle r = new Rectangle(new Point(-2, -2), new Point(4, 4));
		assertTrue(r.isSquare());
	}
	
	@Test
	void testIsSquareForSquareIsFalse() {
		Rectangle r = new Rectangle(new Point(-2, -1), new Point(4, 4));
		assertFalse(r.isSquare());
	}

	@Test
	void testEnclosesItself() {
		Rectangle r = new Rectangle(new Point(1, 1), new Point(4, 4));
		assertTrue(r.encloses(r));
	}
	
	@Test
	void testOuterEnclosesInnerRectangle() {
		Rectangle outer = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle inner = new Rectangle(new Point(3, 3), new Point(4, 4));
		assertTrue(outer.encloses(inner));
	}
	
	@Test
	void testInnerDoesNotEncloseOuterRectangle() {
		Rectangle outer = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle inner = new Rectangle(new Point(3, 3), new Point(4, 4));
		assertFalse(inner.encloses(outer));
	}
	
	@Test
	void testOverlappingRectanglesDoNotEncloseEachother() {
		Rectangle r1 = new Rectangle(new Point(1, 1), new Point(4, 4));
		Rectangle r2 = new Rectangle(new Point(2, 2), new Point(5, 5));
		assertFalse(r1.encloses(r2));
		assertFalse(r2.encloses(r1));
	}
	
	@Test
	void testOuterEnclosesInnerWithSameTopLeft() {
		Rectangle outer = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle inner = new Rectangle(new Point(2, 2), new Point(4, 4));
		assertTrue(outer.encloses(inner));
	}

	@Test
	void testOverlapIfFullyEnclosed() {
		Rectangle rect = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle inside = new Rectangle(new Point(3, 3), new Point(4, 4));
		assertTrue(rect.overlaps(inside));
		assertTrue(inside.overlaps(rect));
	}
	
	@Test
	void testNoOverlapWhenBoundariesTouch() {
		Rectangle rect = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle border = new Rectangle(new Point(4, 5), new Point(8, 9));
		assertFalse(rect.overlaps(border));
		assertFalse(border.overlaps(rect));
	}

	@Test
	void testOverlapIfRectanglesOverlap() {
		Rectangle rect = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle overlap = new Rectangle(new Point(3, 4), new Point(8, 9));
		assertTrue(rect.overlaps(overlap));
		assertTrue(overlap.overlaps(rect));
	}
	
	@Test
	void testNoOverlapWhenRectangleIsAboveOther() {
		Rectangle rect = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle above = new Rectangle(new Point(0, 0), new Point(7, 1));
		assertFalse(rect.overlaps(above));
		assertFalse(above.overlaps(rect));
	}

	
	@Test
	void testNoOverlapWhenRectangleIsBesideOther() {
		Rectangle rect = new Rectangle(new Point(2, 2), new Point(5, 5));
		Rectangle left = new Rectangle(new Point(0, 0), new Point(1, 7));
		assertFalse(rect.overlaps(left));
		assertFalse(left.overlaps(rect));
	}

	@Test
	void testStretch() {
		Rectangle rect = new Rectangle(new Point(4, 5), new Point(7, 9));
		Rectangle expected = new Rectangle(new Point(4, 5), new Point(10, 13));
		assertTrue(rect.stretch(2).isSame(expected));
	}
	
	@Test
	void testStretchDoesNotChangeOriginal() {
		Rectangle rect = new Rectangle(new Point(4, 5), new Point(7, 9));
		Rectangle expected = new Rectangle(new Point(4, 5), new Point(7, 9));
		rect.stretch(2);
		assertTrue(rect.isSame(expected));
	}
	
	@Test
	void testShrink() {
		Rectangle rect = new Rectangle(new Point(4, 5), new Point(13, 17));
		Rectangle expected = new Rectangle(new Point(4, 5), new Point(7, 9));
		assertTrue(rect.shrink(3).isSame(expected));
	}
	
	@Test
	void testShrinkDoesNotChangeOriginal() {
		Rectangle rect = new Rectangle(new Point(4, 5), new Point(13, 17));
		Rectangle expected = new Rectangle(new Point(4, 5), new Point(13, 17));
		rect.shrink(3);
		assertTrue(rect.isSame(expected));
	}
	
	@Test
	void testStretchShrink() {
		Rectangle r = new Rectangle(new Point(1, 1), new Point(5, 9));
		assertTrue(r.stretch(2).shrink(2).isSame(r));		
	}
}
