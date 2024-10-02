import org.w3c.dom.css.Rect;

public class Rectangle{
    private Point topLeft;
    private Point bottomRight;

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public int getWidth(){
      return  this.bottomRight.getX() - this.topLeft.getX();
    }

    public int getHeight(){
        return this.bottomRight.getY() - this.topLeft.getY();
    }

    public boolean isSquare(){
        return this.topLeft.getX() - this.bottomRight.getX() == this.topLeft.getY() - this.bottomRight.getY();
    }

    public boolean isSame(Rectangle other){
        return (this.topLeft.getX() == other.topLeft.getX()
                && this.topLeft.getY() == other.topLeft.getY()
                && this.bottomRight.getX() == other.bottomRight.getX()
                && this.bottomRight.getY() == other.bottomRight.getY());
    }

    public boolean encloses(Rectangle other){
        return (this.topLeft.getX() <= other.topLeft.getX()
                && this.topLeft.getY()<= other.topLeft.getY()
                && this.bottomRight.getX() >= other.bottomRight.getX()
                && this.bottomRight.getY() >= other.bottomRight.getY());
    }

    //TODO

    private boolean isPointInRectangle(Point p){
       return this.topLeft.getX() < p.getX()
               && p.getX() <this.bottomRight.getX()
               && this.topLeft.getY() < p.getY()
               && p.getY() < this.bottomRight.getY();
    }
    public boolean overlaps(Rectangle other){
        return isPointInRectangle(other.topLeft)
                || isPointInRectangle(other.bottomRight)
                || isPointInRectangle(new Point(other.topLeft.getX(),other.bottomRight.getY()))
                || isPointInRectangle(new Point(other.bottomRight.getX(), other.topLeft.getY()))
                || other.encloses(this);



    }
    

    public Rectangle stretch(int factor){
        return new Rectangle(new Point(this.topLeft.getX(),this.topLeft.getY()), new Point(this.topLeft.getX()+this.getWidth()*factor,this.topLeft.getY()+this.getHeight()*factor));
    }

    public Rectangle shrink(int factor){
        return new Rectangle(new Point(this.topLeft.getX(),this.topLeft.getY()), new Point(this.topLeft.getX()+this.getWidth()/factor,this.topLeft.getY()+this.getHeight()/factor));
    }


    public Rectangle(Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Rectangle(Point topLeft, int size){
        this(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

}
//Add your Rectangle class here
