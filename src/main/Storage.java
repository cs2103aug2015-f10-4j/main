package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
	
	private static final String MESSAGE_WELCOME = "Welcome to Magical. %1$s is ready for use";
	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";

	private static File file;
	private ArrayList<Task> taskList;
	
	public Storage (String fileName) throws IOException {

		file = new File(fileName);

		if ( !(file.exists()) ) {
			file.createNewFile();
			taskList = new ArrayList<Task>();
		} else {
			readTaskList();
		}
		
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}
	
	public ArrayList<Task> getTasks() throws Exception {
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
	
	// for reading contents in the file
	public ArrayList<Task> readTaskList() {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			taskList = (ArrayList<Task>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			taskList = new ArrayList<Task>();
		} 
		
		return taskList;
	}
}
