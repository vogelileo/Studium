public class DinoMuseumVisitor implements MuseumVisitor {
    @Override
    public void visit(Museum museum) {
        System.out.println("Entering " + museum.getName());
    }

    @Override
    public void visit(Area area) {
        System.out.println("Visiting " + area.getName() + " Area");
    }

    @Override
    public void visit(Exhibit exhibit) {
        System.out.println("Looking at " + exhibit.getExhibitName());
    }

    @Override
    public void leave(Museum museum) {
        System.out.println("Leaving " + museum.getName());
    }

    @Override
    public void leave(Area area) {
        System.out.println("Leaving " + area.getName() + " Area");
    }

    @Override
    public void leave(Exhibit exhibit) {
        System.out.println("Leaving " + exhibit.getExhibitName());
    }
}
