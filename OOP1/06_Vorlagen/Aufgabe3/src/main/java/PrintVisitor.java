import java.io.PrintStream;
import java.util.Objects;

public class PrintVisitor extends FileSystemVisitor{
    private final PrintStream printStream;
    private int indentLevel = 0;

    public int hashCode(){
        return Objects.hash(printStream, indentLevel);
    }
    public String generateIndents(){
        return "  ".repeat(indentLevel);
    }

    public void visit(File file){
        printStream.println(generateIndents()+file.getName() + " ("+file.getSize()+" byte)");
    }
    public void visit(Link link){
        printStream.println(generateIndents()+link.getName() + " -> "+link.getTarget().getName());
    }
    public void visit(Folder folder){
        printStream.println(generateIndents()+folder.getName() + " (Folder)");
        indentLevel++;
    }

    public void leave(Folder folder){
        indentLevel--;
    }

    PrintVisitor(PrintStream printStream){
        this.printStream = printStream;
    }
}
