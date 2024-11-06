package main.java;

public class Circle implements Shape{
    private Vector center;
    private final int radius;
    private final int color;

    public int getRadius(){
        return this.radius;
    }

    public int getX(){
        return this.center.x;
    }

    public int getY(){
        return this.center.y;
    }

    public void move(int x, int y){
        this.center.x += x;
        this.center.y += y;
    }

    public int getAreaColor() {
        return this.color;
    }

    public void draw(Visualization visualization){
        visualization.drawCircle(center.x, center.y ,radius,color);
    }

    public Circle(int centerX, int centerY, int radius, int color){
        this.center = new Vector(centerX, centerY);
        this.radius = radius;
        this.color = color;
    }


}
