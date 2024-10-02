public class Point{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSame(Point other){
        return this.x == other.getX() && this.y == other.getY();
    }

    public Point(int x, int y){
        this.x = x;
        this.y =y;
    }
}