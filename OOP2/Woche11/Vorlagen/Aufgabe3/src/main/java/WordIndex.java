import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class WordIndex {

    // TODO: Create a Map with Sets

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

            while (line != null) {
                // TODO: index File
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer[] getLines(String word){
       // TODO: return all lines where the word is found
        return null;
    }
}
