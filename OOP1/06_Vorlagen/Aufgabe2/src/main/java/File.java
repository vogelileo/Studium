public class File extends Element{
    private final long size;

    @Override
    public long getSize(){
        return this.size;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
         visitor.leave(this);
         }

    File(String name, long size){
        super(name);
        this.size = size;
    }
}
