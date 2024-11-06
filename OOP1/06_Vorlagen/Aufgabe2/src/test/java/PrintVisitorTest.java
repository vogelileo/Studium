import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class PrintVisitorTest {

	private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
	private final PrintStream stream = new PrintStream(outputContent);

	@Test
	void testVisitFile() {
		Element file = new File("Exam.pdf", 2_777_089);
		FileSystemVisitor visitor = new PrintVisitor(stream);
		file.accept(visitor);
		String expected = String.format(
				"Exam.pdf (2777089 byte)%n"
		);
		assertEquals(expected, outputContent.toString());
	}

	@Test
	void testVisitLink() {
		Element programs = new Folder("Programs");
		Element link = new Link("Apps", programs);
		FileSystemVisitor visitor = new PrintVisitor(stream);
		link.accept(visitor);
		String expected = String.format(
  				"Apps -> Programs%n"
		);
		assertEquals(expected, outputContent.toString());
	}

	@Test
	void testVisitFolder() {
		Element programs = new Folder("Programs");
		FileSystemVisitor visitor = new PrintVisitor(stream);
		programs.accept(visitor);
		String expected = String.format(
				"Programs (Folder)%n"
		);
		assertEquals(expected, outputContent.toString());
	}

	@Test
	void testIndentation() {
		Folder programs = new Folder("Programs");
		programs.addEntry(new File("notepad.exe", 177_000));
		FileSystemVisitor visitor = new PrintVisitor(stream);
		programs.accept(visitor);
		String expected = String.format(
				"Programs (Folder)%n" +
				"  notepad.exe (177000 byte)%n"
		);
		assertEquals(expected, outputContent.toString());
	}

	@Test
	void testComplexExample() {
		Folder homework = new Folder("Homework");
		homework.addEntry(new File("Ex01.doc", 3_000L));
		homework.addEntry(new File("Ex02.ppt", 55_000L));
		Folder week3 = new Folder("Week3");
		week3.addEntry(new File("Ex03.doc", 175_000L));
		Element diagram = new File("NiceDiagram.png", 1_980_000L);
		week3.addEntry(diagram);
		homework.addEntry(week3);
		Folder week4 = new Folder("Week4");
		Folder code = new Folder("Code");
		week4.addEntry(code);
		week4.addEntry(new Link("DiaW3", diagram));
		code.addEntry(new File("Ex04.java", 500L));
		code.addEntry(new File("Ex04Test.java", 2_000L));
		homework.addEntry(week4);

		FileSystemVisitor visitor = new PrintVisitor(stream);
		homework.accept(visitor);
		String expected = String.format(
      			"Homework (Folder)%n" +
				"  Ex01.doc (3000 byte)%n" +
				"  Ex02.ppt (55000 byte)%n" +
				"  Week3 (Folder)%n" +
				"    Ex03.doc (175000 byte)%n" +
				"    NiceDiagram.png (1980000 byte)%n" +
				"  Week4 (Folder)%n" +
				"    Code (Folder)%n" +
				"      Ex04.java (500 byte)%n" +
				"      Ex04Test.java (2000 byte)%n" +
				"    DiaW3 -> NiceDiagram.png%n"
		);
		assertEquals(expected, outputContent.toString());
	}
}
