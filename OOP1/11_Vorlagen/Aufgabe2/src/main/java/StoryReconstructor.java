import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StoryReconstructor {
	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(Path.of("C:\\Users\\092132848\\Documents\\GitHub\\Studium\\OOP1\\11_Vorlagen\\Aufgabe2\\src\\main\\resources\\story-input.txt"));
		HashMap<Integer,String> linesIndex= new HashMap<>();
		for(String line: lines){
			String word = line.split("=")[1];
			Integer index = Integer.valueOf(line.split("=")[0]);
			linesIndex.put(index, word);
		}

		String finishedStory = " ";

		for(Integer key: linesIndex.keySet()){
			finishedStory = finishedStory + linesIndex.get(key) + " ";
		}
		try(FileWriter output = new FileWriter("outputStory.txt", StandardCharsets.UTF_8,false)){

			output.write(finishedStory);
		}catch (Exception e){
			System.out.println(e);
		}


	}
}
