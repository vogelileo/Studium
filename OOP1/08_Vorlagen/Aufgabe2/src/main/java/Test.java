package main.java;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		// Sample shows how to directly draw on the visualization board.
		// TODO: Replace this code by using instances of the Shape interface.
		Rectangle rect = new Rectangle(10, 10, 100, 80, 0x1f497d);
		 Circle circle = new Circle(130, 60, 40, 0xc0504d);
		 TextBox textBox =
				 new TextBox(30, 110, 100, 50, 0x9bbb59, "Hello!", 0x000000);
		 List<Shape> figures = List.of((Shape)rect, (Shape)circle, (Shape) textBox);
		 Visualization v = new SwingVisualization();
		 Drawing drawing = new Drawing(figures, v);
		 drawing.move(100, 100);
		 drawing.draw();
		 drawing.printTexts();
	}
}
