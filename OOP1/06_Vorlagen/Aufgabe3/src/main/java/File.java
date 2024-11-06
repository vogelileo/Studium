import java.util.Objects;

public class File extends Element{
    private final long size;

    public int hashCode(){
        return Objects.hash(size);
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
       return this.size == ((File)obj).getSize();
    }
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
