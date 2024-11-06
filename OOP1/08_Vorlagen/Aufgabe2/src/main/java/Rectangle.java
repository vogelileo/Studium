package main.java;

public class Rectangle implements Shape {
    private Vector lefttop;
    private Vector size;

    private final int color;

    public int getX(){
        return this.lefttop.x;
    }

    public int getY(){
        return this.lefttop.y;
    }

    public int getAreaColor(){
        return this.color;
    }

    public int getWidth(){
        return this.size.x;
    }

    public int getHeight(){
        return this.size.y;
    }

    public void resize(int width, int height){
        this.size.x = width;
        this.size.y = height;
    }

    public void draw(Visualization visualization){
        visualization.drawRectangle(this.lefttop.x, this.lefttop.y, this.size.x,this.size.y,color);
    }

    public void move(int x, int y){
        this.lefttop.x += x;
        this.lefttop.y += y;
    }

    public Rectangle(int left, int top,int width, int height, int color){
        this.lefttop = new Vector(left, top);
        this.size = new Vector(width, height);
        this.color = color;
    }
}
