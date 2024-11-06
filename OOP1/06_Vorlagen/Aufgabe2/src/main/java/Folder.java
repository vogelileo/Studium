import java.util.ArrayList;
public class Folder  extends Element{
    private ArrayList<Element> entries = new ArrayList<Element>(0);

    public long getSize(){
        int size = 0;

        for (Element e: this.entries){
            size += e.getSize();
        }
        return size;
    }

    public void addEntry(Element entry){
        this.entries.add(entry);
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
         visitor.visit(this);
         for (Element entry : entries) {
             entry.accept(visitor);
             }
         visitor.leave(this);
         }

    Folder (String name){
        super(name);
    }
}
