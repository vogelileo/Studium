import java.util.List;

public class Museum implements MuseumPart {

    private final String name;
    private final List<MuseumPart> areas;

    public Museum(String name, List<MuseumPart> areas) {
        this.name = name;
        this.areas = areas;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(MuseumVisitor visitor) {
        //TODO
    }
}
