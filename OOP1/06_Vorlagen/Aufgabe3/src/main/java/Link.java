import java.util.Objects;

public class Link  extends Element{
    private final Element target;

    public int hashCode(){
        return Objects.hash(target);
    }

    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        if(!super.equals(obj)){
            return false;
        }
        return this.target == ((Link)obj).getTarget();
    }
    public Element getTarget(){
        return this.target;
    }
    @Override
    public final long getSize() {
        return 4000;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
         visitor.visit(this);
         visitor.leave(this);
         }
    Link(String name,Element target){
        super(name);
        this.target = target;
    }

}
