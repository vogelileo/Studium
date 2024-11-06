package main.java;

public interface Visualization {
	void drawRectangle(int left, int top, int width, int height, int color);
	void drawCircle(int x, int y, int radius, int color);
	void drawText(int x, int y, String text, int color);
	void clear();
}
