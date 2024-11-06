package ch.ost.oop1.ex08.task1;

import java.util.Date;

public class ConsoleLogger implements Logger {

	public void log(String message) {
		var timestamp = new Date();
		System.out.println(timestamp + ": " + message);
	}

}
