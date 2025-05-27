public class Exhibit implements MuseumPart {

    private final String exhibitName;

    public Exhibit(String exhibitName) {
        this.exhibitName = exhibitName;
    }

    public String getExhibitName() {
        return exhibitName;
    }

    @Override
    public void accept(MuseumVisitor visitor) {
        //TODO
    }
}
