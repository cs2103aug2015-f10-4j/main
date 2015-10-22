package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Storage {
	
	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";

	private static File file;
	private static String logFileName = "logFile.txt";
	private ArrayList<Task> taskList;
	ObjectMapper mapper = new ObjectMapper();
	
	// private static File logFile = new File(logFileName);
	// FileOutputStream fos;
	// ObjectOutputStream oos;
	// FileWriter logFile;
	
	// for logging for Week 9 tutorial
	private static FileHandler handler;
	private static Logger logger;

	/*
	// for creating logFile
	public void createLog () {
		try {
			logger = Logger.getLogger(Storage.class.getName());
			logger.setUseParentHandlers(false); // to prevent logger from writing into console
			handler = new FileHandler(logFileName, true);
			// handler.setFormatter(new SimpleFormatter());
			handler.setFormatter(new XMLFormatter());
			logger.addHandler(handler);
			logger.setLevel(Level.SEVERE);
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("Error: File Not Found");
		} catch (IOException e) {
			System.out.println("Error: Unable to write to file");
		}
	}
	
	// Gson
	/*
	// Gson gson = new Gson();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String jsonRepresentation;
	*/
	
	public Storage (String fileName) {
		// createLog();
		
		assert fileName != null;
		
		file = new File(fileName);

		if ( !(file.exists()) ) {
			try {
				file.createNewFile();
				// logger.info("logFile: Creating new file...");
			} catch (IOException e) {
				// logger.info("Storage IOException: File not created successfully");
				System.out.println("Storage IOException: File not created successfully");
				// logger.severe("Severe message");
				return;
			}
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
		// FileOutputStream fos = new FileOutputStream(file);
		// ObjectOutputStream oos = new ObjectOutputStream(fos);
		/*
		jsonRepresentation = gson.toJson(taskList);
		oos.writeObject(jsonRepresentation);
		*/
		// System.out.print(jsonRepresentation);
		// oos.writeObject(taskList);
		
		mapper.writeValue(file, taskList);
		// oos.close();
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
			// JsonReader reader = new JsonReader(new FileReader(file));
			// reader.setLenient(true);
			// String garbage = reader.nextString();
			// Task[] read = gson.fromJson(reader, Task[].class);
			// String jsonTest = gson.toJson(read);
			// System.out.println(jsonTest);
			
			// Map<String,Object> userData = mapper.readValue(new File("user.json"), Map.class);
			taskList = mapper.readValue(file, new TypeReference<ArrayList<Task>>() { });
			
			//debug
			/*
			int size = taskList.size();
			System.out.println("new");
			for (int i = 0; i < size; i++) {
				System.out.println(taskList.get(i).getType());
				System.out.println(taskList.get(i).getTitle());
				System.out.println(taskList.get(i).getDescription());
				System.out.println(taskList.get(i).getDueDate());
				System.out.println();
			}
			*/


			// taskList.add(read[0]);
			// taskList = gson.fromJson(jsonRepresentation, new TypeToken<ArrayList<Task>>(){}.getType());

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
