package main.java;

public class Vector {
    public int x;
    public int y;


    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }

    Vector(int x, int y){
        this.x = x;
        this.y = y;
    }
}
