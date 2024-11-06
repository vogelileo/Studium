import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintVisitor extends FileSystemVisitor{
    private final PrintStream printStream;
    private int indentLevel = 0;

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
