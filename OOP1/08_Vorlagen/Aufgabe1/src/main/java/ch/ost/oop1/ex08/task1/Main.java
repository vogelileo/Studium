package ch.ost.oop1.ex08.task1;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	private static void someOperation(Logger logger) {
		var randomNumber = Math.random();
		logger.log("Generated Number: " + randomNumber);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Logger nullLogger = new NullLogger();
		someOperation(nullLogger);

		Logger consoleLogger = new ConsoleLogger();
		someOperation(consoleLogger);

		FileLogger fileLogger = new FileLogger(new File("log.txt"));
		someOperation(fileLogger);
		fileLogger.close();
	}
}
