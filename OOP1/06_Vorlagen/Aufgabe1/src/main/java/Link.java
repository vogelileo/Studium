public class Link  extends Element{
    private final Element target;

    public Element getTarget(){
        return this.target;
    }
    public final long getSize() {
        return 4000;
    }

    Link(String name,Element target){
        super(name);
        this.target = target;
    }

}
