public class FileSystemVisitor {
	public void visit(File file) {}
	public void visit(Link link) {}
	public void visit(Folder folder) {}
	public void leave(File file) {}
	public void leave(Link link) {}
	public void leave(Folder folder) {}
}
