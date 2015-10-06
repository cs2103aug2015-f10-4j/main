package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Storage {
	
	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";

	private static File file;
	private ArrayList<Task> taskList;
	// Gson gson = new Gson();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	
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
	
	
	protected void createTask(Task t) throws IOException {
		taskList.add(t);
		writeTaskList();
	}
	
	protected ArrayList<Task> getTasks() {
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
		String jsonRepresentation = gson.toJson(taskList);
		oos.writeObject(jsonRepresentation);
		// oos.writeObject(taskList);
		oos.close();
	}
	
	// for reading contents in the file
	protected ArrayList<Task> readTaskList() {
		try {
			
			// PROBLEM: WENT INTO EXCEPTION because of the weird String
			
			// FileInputStream fis = new FileInputStream(file);
			// ObjectInputStream ois = new ObjectInputStream(fis);
			// taskList = (ArrayList<Task>) ois.readObject();
			// ois.close();
			
			// FileInputStream fis = new FileInputStream(file);
			// ObjectInputStream ois = new ObjectInputStream(fis);
			// taskList = (ArrayList<Task>) ois.readObject();
			// FileReader fileReader = new FileReader(file);
			// BufferedReader buffered = new BufferedReader(fileReader);
			
			// Task[] contents = new Gson().fromJson(reader, Task[].class);
			JsonReader reader = new JsonReader(new FileReader(file));
			reader.setLenient(true);
			String garbage = reader.nextString();
			Task[] read = gson.fromJson(reader, Task[].class);

			// taskList.add(read[0]);
			// taskList = gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());

			// taskList = gson.fromJson(ois, Task.class);
			// ois.close();

		} catch (Exception e) {
			taskList = new ArrayList<Task>();
			e.printStackTrace();
		} 
		return taskList;
	}
	
	protected void setTaskList(ArrayList<Task> tList) throws IOException {
		taskList = tList;
		writeTaskList();
	}
}
