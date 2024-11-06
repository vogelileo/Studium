

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileSystemTest {

	@Test
	void testGetNameForFile() {
		Element document = new File("Document", 15L);
		assertEquals("Document", document.getName());
	}
	
	@Test
	void testGetSizeForFile() {
		Element document = new File("Document", 15L);
		assertEquals(15L, document.getSize());
	}
	
	@Test
	void testGetNameForFolder() {
		Element pictures = new Folder("Pictures");
		assertEquals("Pictures", pictures.getName());
	}

	@Test
	void testGetSizeForLink() {
		Element theMatrix = new File("The Matrix.mp4", 788_000_000L);
		Element link = new Link("Alias", theMatrix );
		assertEquals(4000L, link.getSize());
	}

	@Test
	void testGetTargetForLink() {
		Element executable = new File("javac.exe", 20_000L);
		Link link = new Link("javacompiler", executable );
		assertSame(executable, link.getTarget());
	}
	
	@Test
	void testGetSizeForEmptyFolder() {
		Element downloads = new Folder("Downloads");
		assertEquals(0L, downloads.getSize());
	}
	
	@Test
	void testGetSizeForNonEmptyFolder() {
		Folder music = new Folder("Music");
		music.addEntry(new File("ElevatorSound.mp3", 2_100_000L));
		music.addEntry(new File("Everything is AWESOME!!!.mp3", 4_350_000L));
		assertEquals(6_450_000L, music.getSize());
	}
	
	@Test
	void testGetSizeForFolderHierarchy() {
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
		assertEquals(2_219_500L, homework.getSize());
	}
}
