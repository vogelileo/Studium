public abstract class Element {
    private String name;

    public String getName(){
        return this.name;
    }

    public abstract long getSize();

    public abstract void accept(FileSystemVisitor visitor);

    Element (String name){
        this.name = name;
    }
}
