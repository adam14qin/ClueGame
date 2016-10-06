package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	private static final long serialVersionUID = -8436274110449011956L;
	private static final String filename = "logfile.txt";

	public BadConfigFormatException() {
		// Default constructor
		super("Bad configuration format encountered.");
		writeMessageToFile("Bad configuration format encountered.");
	}
	
	public BadConfigFormatException(String message) {
		// Parameterized constructor
		super(message);
		writeMessageToFile(message);
	}
	
	private void writeMessageToFile(String msg) {
		// Print message to specified log file
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			out.println(msg);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
}
