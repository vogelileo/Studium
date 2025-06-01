import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

public class WordIndex {
    private final HashMap<String, HashSet<Integer>> map = new HashMap<>();

    public static void main(String[] args) throws URISyntaxException {

        var index = new WordIndex();
        var path = WordIndex.class.getClassLoader().getResource("./text.txt").toURI().getPath();
        index.indexFile(path);

        var lines = index.getLines("Elvis");
        for (Integer a : lines) {
            System.out.println(a);
        }
    }

    public void indexFile(String path){
        try (var reader = new BufferedReader(new FileReader(path))) {
            var line = reader.readLine();
            var lineNr = 1;

            while (line != null) {
                for (String a : line.toLowerCase().replaceAll("[^a-zA-Z\\d\\s:]", "").split(" ")){
                    if (map.containsKey(a)){
                        map.get(a).add(lineNr);
                    } else {
                        var set = new HashSet<Integer>();
                        set.add(lineNr);
                        map.put(a, set);
                    }
                }
                lineNr++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer[] getLines(String word){
        var set = map.get(word.toLowerCase());
        if (set == null) return new Integer[0];
        var array = new Integer[set.size()];
        return set.toArray(array);
    }
}
