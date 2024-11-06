package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.TextBox;
import org.junit.jupiter.api.Test;

public class TextBoxTest {

	private static final int left = 1;
	private static final int top = 2;
	private static final int width = 3;
	private static final int height = 4;
	private static final int color = 0x0000ff;
	private static final String text = "TestText";
	private static final int textColor = 0xffffff;
	private final TextBox textBox = new TextBox(left, top, width, height, color, text, textColor);

	@Test
	void testGetX() {
		assertEquals(left, textBox.getX());
	}

	@Test
	void testGetY() {
		assertEquals(top, textBox.getY());
	}

	@Test
	void testGetWidth() {
		assertEquals(width, textBox.getWidth());
	}

	@Test
	void testGetHeight() {
		assertEquals(height, textBox.getHeight());
	}

	@Test
	void testGetColor() {
		assertEquals(color, textBox.getAreaColor());
	}

	@Test
	void testGetText() {
		assertEquals(text, textBox.getText());
	}

	@Test
	void testGetTextColor() {
		assertEquals(textColor, textBox.getTextColor());
	}
}
