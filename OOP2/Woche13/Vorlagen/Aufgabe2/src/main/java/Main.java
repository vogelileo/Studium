import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Museum museum = new Museum("Museum", Arrays.asList(
                new Area("Paleozoic", Arrays.asList(
                        new Exhibit("Trilobite"),
                        new Exhibit("Pelycosaur")
                )),
                new Area("Mesozoic", Arrays.asList(
                        new Area("Triassic", Arrays.asList(
                                new Exhibit("Plesiosaurs")
                        )),
                        new Area("Jurassic", Arrays.asList(
                                new Exhibit("Diplodocus")
                        )),
                        new Area("Cretaceous", Arrays.asList(
                                new Exhibit("T-Rex")
                        ))
                ))
        ));

        museum.accept(new DinoMuseumVisitor() {
        });
    }
}
