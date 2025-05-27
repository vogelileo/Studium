import java.util.List;

public class Main {

    public static void main(String[] args) {
        Area paleozoic = new Area("Paleozoic", List.of(new Exhibit("Trilobite"), new Exhibit("Pelycosaur")));
        Area triassic = new Area("Triassic", List.of(new Exhibit("Plesiosaurs")));
        Area jurassic = new Area("Jurassic", List.of(new Exhibit("Diplodocus")));
        Area cretaceous = new Area("Cretaceous", List.of(new Exhibit("T-Rex")));
        Area mesozoic = new Area("Mesozoic", List.of(triassic, jurassic, cretaceous));
        Museum museum = new Museum("Dinosaur Museum", List.of(paleozoic, mesozoic));
        museum.accept(new DinoMuseumVisitor());
    }
}
