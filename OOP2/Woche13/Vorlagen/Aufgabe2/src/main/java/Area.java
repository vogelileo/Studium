import java.util.List;

public class Area implements MuseumPart {

    private final String name;
    private final List<MuseumPart> museumParts;

    public Area(String name, List<MuseumPart> museumParts) {
        this.name = name;
        this.museumParts = museumParts;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(MuseumVisitor visitor) {
        //TODO
    }
}
