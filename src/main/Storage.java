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
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	private static File newFolder = new File(DEFAULT_FILE_DIRECTORY);
	private static File file =  new File(DEFAULT_FILE_PATH);

	/**
	 * This is the Storage constructor which makes use of createFolder, writeToProperties, readFileSettings
	 * and initFile methods. This constructor creates a default folder in where the program is run.
	 * In this folder, a .properties file is created to store default program settings and a default
	 * .txt file to store task data.
	 * @param None.
	 * @return Nothing.
	 */
	public Storage () {

		createFolder();

		String storedFilePath = readFileSettings();
		if (storedFilePath == null) { // if properties file is empty, create properties file
			writeToProperties(DEFAULT_FILE_PATH);
			initFile();
		} else {
			file = new File(storedFilePath);
			initFile();
		}

	}

	/**
	 * This method creates a default folder if it does not exist in where the program is run.
	 * @param None.
	 * @return whether the folder is created successfully or not
	 * @exception Exception when the folder is not successfully created
	 * @see Exception
	 */
	protected static boolean createFolder() {
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

	/**
	 * This is method reads the file path stored in the program's properties file.
	 * @param None
	 * @return String	file path of where the data file is stored
	 * @exception IOException On input error.
	 * @see IOException
	 */
	protected String readFileSettings() {

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
	/**
	 * This method changes the file path stored in the properties file.
	 * @param newFilePath New file path specified by user.
	 * @return Nothing.
	 * @throws IOException 
	 */
	public void changeFilePath(String newFilePath) throws IOException {

		String oldFilePath = readFileSettings();

		moveFolder(newFilePath + "/" + DEFAULT_FILE_DIRECTORY + "/");

		// save new properties to project root folder
		newFilePath = newFilePath + "/" + DEFAULT_FILE_PATH;
		writeToProperties(newFilePath);

		moveFile(oldFilePath, newFilePath);
	}

	/**
	 * This method writes the specified file path into the default properties file.
	 * @param filePath File path to be stored.
	 * @return Nothing.
	 * @exception IOException On input error.
	 * @see IOException
	 */
	protected void writeToProperties (String filePath) {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(SETTINGS_FILE_PATH);
			prop.setProperty("filePath", filePath);
			prop.store(output, null); // save properties to project root folder

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * This method creates a folder in the new file path specified.
	 * @param newFilePath New file path to create the folder in.
	 * @return Nothing.
	 */
	protected void moveFolder(String newFilePath) {
		File file = new File(newFilePath);

		if (!file.exists()) {
			file = new File(newFilePath);
			file.mkdir();
		}
	}

	/**
	 * This method copies content from a source .txt file to another destination .txt file.
	 * The source .txt file will be deleted at the end of this method.
	 * @param oldFilePath File path of the source .txt file.
	 * @param newFilePath File path of the destination .txt file.
	 * @return Nothing.
	 * @exception IOException On input error.
	 * @see IOException
	 */
	protected void moveFile(String oldFilePath, String newFilePath) throws IOException {

		InputStream inStream = null;
		OutputStream outStream = null;

		File oldFile =new File(oldFilePath);
		File newFile =new File(newFilePath);

		inStream = new FileInputStream(oldFile);
		outStream = new FileOutputStream(newFile);

		byte[] buffer = new byte[1024];

		int length;

		while ((length = inStream.read(buffer)) > 0){
			outStream.write(buffer, 0, length); // copy file contents over
		}

		inStream.close();
		outStream.close();

		oldFile.delete(); //delete the original file
		file = newFile;
	}

	/**
	 * This method initialises the .txt data file. If the file doesn't exist, the data file is created and
	 * data is written into it. Else if the data file exist, data is being read and stored in the program.
	 * @param None.
	 * @return Nothing.
	 * @exception IOException On input error.
	 * @see IOException
	 */
	protected void initFile() {

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

	/**
	 * This method checks whether the data file exist or not.
	 * @param None.
	 * @return Nothing.
	 */
	protected boolean fileExist() {
		if (file.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This method retrieves the list index of the list wanted.
	 * @param id ID of the list wanted.
	 * @return integer value of list index.
	 */
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

	/**
	 * This method retrieves the index of the list complementing to the original list.
	 * @param index index of the original list.
	 * @return integer value of the complementing list.
	 */
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

	/**
	 * This method stores a Task object into the specified list and updates the data file.
	 * @param listIndex list index of the list to store the task into.
	 * @param t Task object to store into the list.
	 * @return Nothing.
	 */
	public void create(int listIndex, Task t) throws IOException {
		lists.get(listIndex).add(t);
		writeLists();
	}

	/**
	 * This method retrieves the list of Task objects specified by the list index.
	 * @param listIndex Index of the list wanted.
	 * @return the list specified.
	 */
	public ArrayList<Task> getList(int listIndex) {
		return lists.get(listIndex);
	}

	/**
	 * This method updates a specified Task in the list of tasks where the task is stored in
	 * and updates the data file.
	 * @param listIndex Index of the list where the Task to be updated is stored in.
	 * @param t The updated Task to be stored.
	 * @return Nothing.
	 */
	public void update(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).set(pos, t);
			writeLists();
		}
	}

	/**
	 * This method deletes the specified Task in the list of tasks where the task is stored in
	 * and updates the data file.
	 * @param listIndex Index of the list where the Task to be deleted is stored in.
	 * @param t Task to be deleted.
	 * @return Nothing.
	 */
	public void delete(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).remove(pos);
			writeLists();
		}
	}

	/**
	 * This method clears a list of tasks of all content and updates the data file.
	 * @param listIndex Index of the list to be cleared.
	 * @return Nothing.
	 */
	protected void clear(int listIndex) throws IOException {
		lists.set(listIndex, new ArrayList<Task>());
		writeLists();
	}

	/**
	 * This method retrieves the position of a specified task in the list it is stored in.
	 * @param listIndex Index of the list that the Task is stored in.
	 * @param t Task object that you want to get the position of.
	 * @return position of the Task in the list it is stored in. (0-based)
	 */
	protected int getPos(int listIndex, Task t) {
		return lists.get(listIndex).indexOf(t);
	}

	/**
	 * This method stores the list of tasks into the parent list of tasks that contains
	 * all the different lists of tasks.
	 * @param listIndex Index of the list to be set into the parent array.
	 * @param list The list to be set into the parent array.
	 * @return Nothing.
	 */
	public void setList(int listIndex, ArrayList<Task> list) throws IOException {
		lists.set(listIndex, list);
		writeLists();
	}

	/**
	 * This method writes all current content from the program into the .txt data file.
	 * @param None.
	 * @return Nothing.
	 */
	protected void writeLists() throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}

	/**
	 * This method reads content from the .txt data file and stores the data into the
	 * program. If the content is empty or the file does not exist, default lists with empty
	 * content will be stored in the program.
	 * @param None.
	 * @return Nothing.
	 * @exception Exception
	 * @see Exception
	 */
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
