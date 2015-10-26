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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {
	
	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";

	private static File file;
	private static String logFileName = "logFile.txt";
	private ArrayList<Task> taskList = new ArrayList<Task>();
	ObjectMapper mapper = new ObjectMapper();
	
	// for logging for Week 9 tutorial - may be implementing permanently in future versions
	private static FileHandler handler;
	private static Logger logger;
	
	private static SimpleDateFormat dateFormat = 
			new SimpleDateFormat("dd MMM yyyy");

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
	*/
	
	public Storage (String fileName) {
		
		// createLog();
		
		assert fileName != null;
		
		file = new File(fileName);
		mapper.setDateFormat(dateFormat);

		if ( !(file.exists()) ) {
			try {
				//file.createNewFile();
				// logger.info("logFile: Creating new file...");
				writeTaskList();
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
	
	
	public void createTask(Task t) throws IOException {
		taskList.add(t);
		writeTaskList();
	}
	
	public ArrayList<Task> getTasks() {
		return taskList;
	}
	
	public void updateTask(Task t) throws IOException {
		int pos = getTaskPos(t);
		if (pos > -1) {
			taskList.set(pos, t);
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
	
	// for clearing
	protected void clearTaskList() throws IOException {
		taskList = new ArrayList<Task>();
		writeTaskList();
	}
	
	protected int getTaskPos(Task t) {
		return taskList.indexOf(t);
	}
	
	protected void writeTaskList() throws IOException {
		
		mapper.writeValue(file, taskList);
	}
	
	// for reading contents in the file
	protected ArrayList<Task> readTaskList() {
		try {
			
			taskList = mapper.readValue(file, new TypeReference<ArrayList<Task>>() { });

		} catch (Exception e) {
			taskList = new ArrayList<Task>();
			e.printStackTrace();
		} 
		return taskList;
	}
	
	public void setTaskList(ArrayList<Task> tList) throws IOException {
		taskList = tList;
		writeTaskList();
	}
}
