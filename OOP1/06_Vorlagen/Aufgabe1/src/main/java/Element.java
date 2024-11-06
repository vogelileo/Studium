public abstract class Element {
    private String name;

    public String getName(){
        return this.name;
    }

    abstract long getSize();

    Element (String name){
        this.name = name;
    }
}
