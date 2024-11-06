package ch.ost.oop1.ex08.task1;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class FileLogger implements Closeable, Logger {

	private OutputStreamWriter logWriter;

	public FileLogger(File file) throws FileNotFoundException {
		logWriter = new OutputStreamWriter(new FileOutputStream(file, true));
	}

	public void log(String message) {
		var timestamp = new Date();
		try {
			logWriter.write(timestamp + ": " + message + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			logWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
