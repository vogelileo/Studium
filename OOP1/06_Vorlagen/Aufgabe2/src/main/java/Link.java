public class Link  extends Element{
    private final Element target;

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
