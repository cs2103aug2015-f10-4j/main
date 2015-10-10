package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
	
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
	}
	
	protected boolean fileExist() {
		if (file.exists()) {
			return true;
		}
		else
			return false;
	}
	
	
	public void createTask(Task t) throws IOException {
		taskList.add(t);
		writeTaskList();
	}
	
	public ArrayList<Task> getTasks() {
		return taskList;
	}
	
	protected void updateTask(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskList.set(pos, t);
			writeTaskList();
		}
	}
	
	protected void deleteTask(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskList.remove(pos);
			writeTaskList();
		}
	}
	
	// for clearing
	protected void clearTaskList() throws IOException {
		taskList = new ArrayList<Task>();
		writeTaskList();
	}
	
	protected int getTaskPos(Task t) {
		return taskList.indexOf(t);
	}
	
	protected void writeTaskList() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(taskList);
		oos.close();
	}
	
	// for reading contents in the file
	protected ArrayList<Task> readTaskList() {
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
	
	protected void setTaskList(ArrayList<Task> tList) throws IOException {
		taskList = tList;
		writeTaskList();
	}
}
