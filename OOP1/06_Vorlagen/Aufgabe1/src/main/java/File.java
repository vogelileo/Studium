public class File extends Element{
    private final long size;

    @Override
    public long getSize(){
        return this.size;
    }

    File(String name, long size){
        super(name);
        this.size = size;
    }
}
