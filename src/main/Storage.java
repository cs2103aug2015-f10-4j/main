package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {

	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";
	public static final int NUM_LISTS = 4;
	public static final int TASKS_INDEX = 0;
	public static final int TASKS_DONE_INDEX = 1;
	public static final int EVENTS_INDEX = 2;
	public static final int EVENTS_DONE_INDEX = 3;
	private static final String DEFAULT_FILE_DIRECTORY = "magical";
	private static final String DEFAULT_FILE_NAME = "storage.txt";
	private static final String SETTINGS_FILE_NAME = "settings.properties";
	private static final String SETTINGS_FILE_PATH = DEFAULT_FILE_DIRECTORY + "/" + SETTINGS_FILE_NAME;
	private static final String DEFAULT_FILE_PATH = DEFAULT_FILE_DIRECTORY + "/" + DEFAULT_FILE_NAME;
	
	private List<ArrayList<Task>> lists;
	ObjectMapper mapper = new ObjectMapper();
	private static SimpleDateFormat dateFormat = 
			new SimpleDateFormat("dd MMM yyyy");
	private static File newFolder = new File(DEFAULT_FILE_DIRECTORY);
	private static File defaultPropertiesFile = new File(SETTINGS_FILE_PATH);
	private static File file =  new File(DEFAULT_FILE_PATH);
	
	public Storage () {
		
		Properties prop = new Properties();
		OutputStream output = null;
		
		createFolder();
		
		String storedFilePath = readFileSettings();
		System.out.println(storedFilePath);
		if (storedFilePath == null) { // if properties file is empty, create properties file
			try {
				output = new FileOutputStream(SETTINGS_FILE_PATH);
				prop.setProperty("filePath", DEFAULT_FILE_PATH);
				prop.store(output, null); // save properties to project root folder
				initFile();

			} catch (IOException io) {
				io.printStackTrace();
			}
		} else {
			file = new File(storedFilePath);
			initFile();
		}

	}
	
	private static boolean createFolder() {
		try {
			if (!newFolder.exists()) {
				newFolder.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns String of filePath read
	private String readFileSettings() {
		
		Properties prop = new Properties();
		InputStream input = null;
		String filePath = DEFAULT_FILE_PATH;

		try {
			input = new FileInputStream(SETTINGS_FILE_PATH);
			prop.load(input);
			filePath = prop.getProperty("filePath");

		} catch (IOException ex) {
			return null;
		}
		
		return filePath;
	}
	
	// method for Logic to call should the user want to change filePath
	// will be stored in settings.properties file
	protected void changeFilePath(String newFilePath) {
		
		Properties prop = new Properties();
		OutputStream output = null;
		
		String oldFilePath = readFileSettings();
		try {
			output = new FileOutputStream(SETTINGS_FILE_PATH);
			moveFolder(newFilePath + "/" + DEFAULT_FILE_DIRECTORY + "/");
			newFilePath = newFilePath + "/" + DEFAULT_FILE_PATH;
			prop.setProperty("filePath", newFilePath);
			prop.store(output, null); // save properties to project root folder
			moveFile(oldFilePath, newFilePath);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private void moveFolder(String newFilePath) {
		File file = new File(newFilePath);

		if (!file.exists()) {
			file = new File(newFilePath);
			file.mkdir();
		}
	}
	
	private void moveFile(String oldFilePath, String newFilePath) {
		
		InputStream inStream = null;
		OutputStream outStream = null;
			
	    	try{
	    		
	    	    File oldFile =new File(oldFilePath);
	    	    File newFile =new File(newFilePath);
	    		
	    	    inStream = new FileInputStream(oldFile);
	    	    outStream = new FileOutputStream(newFile);
	        	
	    	    byte[] buffer = new byte[1024];
	    		
	    	    int length;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0){
	    	    	outStream.write(buffer, 0, length);
	    	    }
	    	 
	    	    inStream.close();
	    	    outStream.close();
	    	    
	    	    oldFile.delete(); //delete the original file
	    	    file = newFile;
	    	    
	    	}catch(IOException e){
	    	    e.printStackTrace();
	    	}
	}
	
	private void initFile() {

		// file = new File(DEFAULT_FILE_NAME);
		mapper.setDateFormat(dateFormat);

		if ( !(file.exists()) ) {
			lists = new ArrayList<ArrayList<Task>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Task>());
			}
			try {
				writeLists();
			} catch (IOException e) {
				System.out.println("Storage IOException: File not created successfully");
				return;
			}
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

	public static int getListIndex(String id) {
		switch(id.charAt(0)) {
		case 't':
			return TASKS_INDEX;
		case 'd':
			return TASKS_DONE_INDEX;
		case 'e':
			return EVENTS_INDEX;
		case 'p':
			return EVENTS_DONE_INDEX;
		default:
			return -1;
		}
	}
	
	public static int getComplementListIndex(int index) {
		switch(index) {
		case TASKS_INDEX:
			return TASKS_DONE_INDEX;
		case TASKS_DONE_INDEX:
			return TASKS_INDEX;
		case EVENTS_INDEX:
			return EVENTS_DONE_INDEX;
		case EVENTS_DONE_INDEX:
			return EVENTS_INDEX;
		default:
			return -1;
		}
	}
	
	public void create(int listIndex, Task t) throws IOException {
		lists.get(listIndex).add(t);
		writeLists();
	}
	
	public ArrayList<Task> getList(int listIndex) {
		return lists.get(listIndex);
	}
	
	public void update(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).set(pos, t);
			writeLists();
		}
	}
	
	public void delete(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).remove(pos);
			writeLists();
		}
	}

	protected void clear(int listIndex) throws IOException {
		lists.set(listIndex, new ArrayList<Task>());
		writeLists();
	}
	
	protected int getPos(int listIndex, Task t) {
		return lists.get(listIndex).indexOf(t);
	}
	
	public void setList(int listIndex, ArrayList<Task> list) throws IOException {
		lists.set(listIndex, list);
		writeLists();
	}

	protected void writeLists() throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}

	protected void readLists() {
		try {
			lists = mapper.readValue(file, new TypeReference<List<ArrayList<Task>>>() { });
		} catch (Exception e) {
			lists = new ArrayList<ArrayList<Task>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Task>());
			}
		}
		return;
	}
}
