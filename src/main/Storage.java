package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {
	
	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";

	private static File file;
	private ArrayList<Task> taskList;
	private ArrayList<Task> taskDoneList;
	private ArrayList<Task>[] lists;
	ObjectMapper mapper = new ObjectMapper();
	
	public Storage (String fileName) {
		assert fileName != null;
		
		file = new File(fileName);

		if ( !(file.exists()) ) {
			try {
				taskList = new ArrayList<Task>();
				taskDoneList = new ArrayList<Task>();
				ArrayList<ArrayList<Task>> tempLists = new ArrayList<ArrayList<Task>>(2);
				lists =  (ArrayList<Task>[]) tempLists.toArray((ArrayList<Task>[]) Array.newInstance(taskList.getClass(), 2));				
				writeTaskList();
			} catch (IOException e) {
				System.out.println("Storage IOException: File not created successfully");
				return;
			}
			taskList = new ArrayList<Task>();
			taskDoneList = new ArrayList<Task>();
		} else {
			readLists();
		}
	}
	
	protected boolean fileExist() {
		if (file.exists()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void createTask(Task t) throws IOException {
		taskList.add(t);
		writeTaskList();
	}
	
	public void createTaskDone(Task t) throws IOException {
		taskDoneList.add(t);
		writeTaskList();
	}
	
	public ArrayList<Task> getTasks() {
		return taskList;
	}
	
	public ArrayList<Task> getTasksDone() {
		return taskDoneList;
	}
	
	public void updateTask(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskList.set(pos, t);
			writeTaskList();
		}
	}
	
	public void updateTaskDone(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskDoneList.set(pos, t);
			writeTaskList();
		}
	}
	
	public void deleteTask(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskList.remove(pos);
			writeTaskList();
		}
	}
	
	public void deleteTaskDone(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskDoneList.remove(pos);
			writeTaskList();
		}
	}
	
	// for clearing
	protected void clearTaskList() throws IOException {
		taskList = new ArrayList<Task>();
		writeTaskList();
	}
	
	protected void clearTaskDoneList() throws IOException {
		taskDoneList = new ArrayList<Task>();
		writeTaskList();
	}
	
	protected int getTaskPos(Task t) {
		return taskList.indexOf(t);
	}
	
	protected int getTaskDonePos(Task t) {
		return taskDoneList.indexOf(t);
	}
	
	protected void writeTaskList() throws IOException {
		lists[0] = taskList;
		lists[1] = taskDoneList;
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}
	
	// for reading contents in the file
	protected void readLists() {
		try {
			lists = mapper.readValue(file, new TypeReference<ArrayList<Task>[]>() { });
			taskList = lists[0];
			taskDoneList = lists[1];
		} catch (Exception e) {
			taskList = new ArrayList<Task>();
			taskDoneList = new ArrayList<Task>();
			e.printStackTrace();
		} 
		return;
	}
	
	public void setTaskList(ArrayList<Task> tList) throws IOException {
		taskList = tList;
		writeTaskList();
	}
	
	public void setTaskDoneList(ArrayList<Task> tList) throws IOException {
		taskDoneList = tList;
		writeTaskList();
	}
}
