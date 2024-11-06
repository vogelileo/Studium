public abstract class Element {
    private String name;

    public String getName(){
        return this.name;
    }

    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        return this.name.equals(((Element) obj).name);
    }
    public abstract long getSize();

    public abstract void accept(FileSystemVisitor visitor);

    Element (String name){
        this.name = name;
    }
}
