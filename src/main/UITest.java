package main;

import java.io.*;
import java.util.Date;

import junit.framework.TestCase;

public class UITest extends TestCase {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	public void setUp() throws Exception {
	    System.setOut(new PrintStream(outContent));
	}

	public void tearDown() throws Exception {
	    System.setOut(null);
	}
	
	public void testShowToUser() {
		UI userInterface = new UI();
		userInterface.showToUser("Hello world!");
		assertEquals("Hello world!\r\n", outContent.toString());
	}
	
	public void testStart() {
		UI userInterface = new UI();
		userInterface.start();
		assertEquals("*:.。.☆ Welcome to Magical! ☆.。.:*\r\n\r\n", outContent.toString());
	}
	
	public static void testDisplayTaskDetails() {
		UI userInterface = new UI();
		Task myTask = new Task();
		myTask.setTitle("Do 2103 homework");
		myTask.setDescription("Finish writing test cases for Magical");
		myTask.setDueDate(new Date(115, 8, 30));
		userInterface.displayTaskDetails(myTask);
		String expected = "Do 2103 homework";
	}
	
	
}
