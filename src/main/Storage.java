package main;

import java.io.File;
import java.io.FileInputStream;
// import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
	
	private static final String MESSAGE_WELCOME = "Welcome to Magical. %1$s is ready for use";
	private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";
	// private static final String MESSAGE_WRITE_FILE_ERROR = "Error writing to file '%1$s'";
	// private static final String MESSAGE_DELETE_ALL = "all content deleted from %1$s";
	// private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	// private static final String MESSAGE_DELETED_LINE = "deleted from %1$s: \"%2$s\"";
	// private static final String MESSAGE_NO_CONTENT_TO_DELETE = "There is nothing to delete in %1$s.";
	// private static final String MESSAGE_INVALID_DELETE = "Delete failed: The line does not exist.";
	// private static final String MESSAGE_INVALID_DELETE_ARGUMENT = "Please enter a non-zero positive integer to delete line";

	private static File file;
	// private static String fileName;
	private ArrayList<Task> taskList;
	
	public Storage (String fileName) { // constructor

		file = new File(fileName);

		if ( !(file.exists()) ) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println(MESSAGE_FILE_NOT_CREATED);
			}
		}
		taskList = new ArrayList<Task>();
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}
	
	// for reading contents in the file
	public ArrayList<Task> readTasks() throws Exception {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		taskList = (ArrayList<Task>) ois.readObject();
		ois.close();
		return taskList;
	}
	
	// for clearing
	public void clearTaskList() throws IOException {
		taskList = new ArrayList<Task>();
		writeTaskList(taskList);
	}
	
	// for add, delete and update
	public void writeTaskList(ArrayList<Task> tasks) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		taskList = tasks;
		oos.writeObject(taskList);
		oos.close();
	}

	
	/*
	protected static String writeFile(String fileName, ArrayList<Task> contents, String command, Task newContent) {

		String result = "";
		
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			
			switch (command) {
			case "ADD":
				result = writeAdd(contents, newContent, oos, fileName);
				break;

			case "DELETE":
				if ( !(isValidDelete(newContent, contents, fileName, oos)) ) {
					break;
				} else {
					result = writeDelete(oos, contents, fileName, newContent);
				}
				break;

			case "CLEAR":
				result = writeClear(fileName);
				break;
			}
			
			oos.close();

		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		}
		return result;
	}
	
	private static void writeExistingContent(ObjectOutputStream oos, ArrayList<Task> contents,
			String fileName) {
		try {
			oos.writeObject(contents);
		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		}
	}

	private static String writeAdd (ArrayList<Task> contents, Task newContent, ObjectOutputStream oos, String fileName) {
		contents.add(newContent);
		writeExistingContent(oos, contents, fileName);
		System.out.println(String.format(MESSAGE_ADDED, fileName, newContent));
		return String.format(MESSAGE_ADDED, fileName, newContent);
	}
	
	private static boolean isValidDelete(Task newContent, ArrayList<Task> contents, String fileName, ObjectOutputStream oos) {	
		try {
			// Integer.parseInt(newContent); // check if newContent is a valid integer
			return true;
		} catch (NumberFormatException e) {
			// still write whatever you already have into the text file
			writeExistingContent(oos, contents, fileName);
			System.out.println(MESSAGE_INVALID_DELETE_ARGUMENT);
			return false;
		}
	}
		
	private static String writeDelete(ObjectOutputStream oos, ArrayList<Task> contents, String fileName, Task newContent) {
		int numOfLines = contents.size();
		int lineToDelete = 0;
				// Integer.parseInt(newContent);
	
		if (numOfLines == 0 && lineToDelete > 0) {
			
			System.out.println(String.format(MESSAGE_NO_CONTENT_TO_DELETE, fileName));
			return String.format(MESSAGE_NO_CONTENT_TO_DELETE, fileName);
			
		} else if (lineToDelete > numOfLines || lineToDelete <= 0) {
			
			writeExistingContent(oos, contents, fileName);
			System.out.println(String.format(MESSAGE_INVALID_DELETE));
			return String.format(MESSAGE_INVALID_DELETE);
			
		} else {
			
			Task removedElement = contents.remove(lineToDelete - 1);
			writeExistingContent(oos, contents, fileName);
			System.out.println(String.format(MESSAGE_DELETED_LINE, fileName, removedElement));
			return String.format(MESSAGE_DELETED_LINE, fileName, removedElement);
			
		}
	}
	
	private static String writeClear(String fileName) {
		try {
			
			File file = new File(fileName);
			file.createNewFile();
			System.out.println(String.format(MESSAGE_DELETE_ALL, fileName));
			return String.format(MESSAGE_DELETE_ALL, fileName);
			
		} catch (IOException ex) {
			
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
			return String.format(MESSAGE_WRITE_FILE_ERROR, fileName);
			
		}
	}
	
	protected static ArrayList<Task> readData() {

		ArrayList<Task> tasks = null;

		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tasks = (ArrayList<Task>) ois.readObject(); // just returns an ArrayList
			ois.close();

		} catch (FileNotFoundException e) {
			// need to write more accurate error message
			e.printStackTrace();
		} catch (IOException ex) {
			// need to write more accurate error message
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		} catch (ClassNotFoundException ex1) {
			// need to write more accurate error message
			System.out.println(String.format("Class Not Found Exception"));
		}
		
		return tasks;
	}
	
	protected static void writeData(Task newTask) {
		ArrayList<Task> contents = new ArrayList<Task> (); // hard coded for now
		String command = "ADD"; // hardcoded for now
		writeFile(fileName, contents, command, newTask);
	}
	*/

	
}
