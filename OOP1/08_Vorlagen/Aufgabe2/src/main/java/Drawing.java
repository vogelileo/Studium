package main.java;

import java.util.List;

public class Drawing {
    private List<Shape> figures;
    private Visualization visualization;

    public void draw(){
        visualization.clear();
        for(Shape shape: figures){
            shape.draw(visualization);
        }
    }

    public void move(int deltaX, int deltaY){
        for(Shape shape: figures){
            shape.move(deltaX, deltaY);
        }
        this.draw();
    }

    public void printTexts(){
        for(Shape shape:figures){
            if(shape instanceof Text){
                System.out.println(((Text) shape).getText());
            }
        }
    }
    public Drawing(List<Shape> figures, Visualization visualization){
        this.figures = figures;
        this.visualization = visualization;
    }
}
