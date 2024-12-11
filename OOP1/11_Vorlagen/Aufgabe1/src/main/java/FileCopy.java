import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FileCopy {

    public static void main(String[] args) throws IOException {
        Scanner inScanner = new Scanner(System.in);

        try {
            System.out.println("Enter path to copy");
            //C:\Users\092132848\Documents\GitHub\Studium\OOP1\11_Vorlagen\Aufgabe1\src\main\java\test.txt
            String originalPath = inScanner.nextLine();

            List<String> lines = Files.readAllLines(Path.of(originalPath), StandardCharsets.UTF_8);
            System.out.println("Enter path to copy to");
            String destPath = inScanner.nextLine();
            Files.write(Path.of(destPath), lines, StandardCharsets.UTF_8);

        } catch (NoSuchFileException e) {
            System.out.println("Original path not found");
        }

    }
}
