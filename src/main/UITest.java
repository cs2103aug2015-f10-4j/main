package main;

import java.io.*;
import java.util.Date;

import junit.framework.TestCase;

public class UITest extends TestCase {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final String endL = System.getProperty("line.separator");

	public void setUp() throws Exception {
	    System.setOut(new PrintStream(outContent));
	}

	public void tearDown() throws Exception {
	    System.setOut(null);
	}
	
	public void testShowToUser() {
		UI.showToUser("Hello world!");
		assertEquals("Hello world!" + endL, outContent.toString());
	}
	
	public void testStart() {
		UI.displayWelcomeMessage();
		assertEquals("*:.。.☆ Welcome to Magical! ☆.。.:*" + endL, outContent.toString());
	}
	
	public static void testDisplayTaskDetails() {
		Task myTask = new Task();
		myTask.setTitle("Do 2103 homework");
		myTask.setDescription("Finish writing test cases for Magical");
		myTask.setDueDate(new Date(115, 8, 30));
		UI.displayTaskDetails(myTask);
		String expected = "Do 2103 homework";
	}
	
	
}
