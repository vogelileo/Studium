import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CatalogueReader implements AutoCloseable {
	private final BufferedReader reader;

	public CatalogueReader(String fileName) {
		reader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(fileName), Charset.forName("UTF-8")));
	}

	public String[] readNextLine() {
		try {
			String line = reader.readLine();
			if (line != null) {
				return line.split(" ");
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			// ignore
		}
	}
}
