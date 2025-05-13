import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;

class WordIndexTest {

    @Test
    void getLines() throws URISyntaxException {
        var index = new WordIndex();
        var path = WordIndex.class.getClassLoader().getResource("./text.txt").toURI().getPath();
        index.indexFile(path);

        var lines = index.getLines("meows");
        assertArrayEquals(new Integer[]{13}, lines);
    }
}