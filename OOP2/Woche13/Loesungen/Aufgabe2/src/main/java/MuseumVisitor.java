public interface MuseumVisitor {
    void visit(Museum museum);
    void visit(Area area);
    void visit(Exhibit exhibit);
    void leave(Museum museum);
    void leave(Area area);
    void leave(Exhibit exhibit);
}
