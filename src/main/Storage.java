package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	private static File newFolder = new File(DEFAULT_FILE_DIRECTORY);
	private static File file =  new File(DEFAULT_FILE_PATH);
	private String storedFilePath;
	private String folderPath;
	private List<ArrayList<Item>> lists;
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * This is the Storage constructor which makes use of createFolder, writeToProperties, readFileSettings
	 * and initFile methods. This constructor creates a default folder in where the program is run.
	 * In this folder, a .properties file is created to store default program settings and a default
	 * .txt file to store task data.
	 * 
	 * @param 	None.
	 * @return 	Nothing.
	 */
	public Storage () {
		
		createFolder();
		storedFilePath = readFileSettings();
		
		if (storedFilePath == null) {
			writeToProperties(DEFAULT_FILE_PATH);
			initFile();
		} else {
			file = new File(storedFilePath);
			initFile();
		}
		
		folderPath = getFolderPath(storedFilePath);		
	}

	/**
	 * This method returns the current path of the of the storage file.
	 * 
	 * @return Location of storage file.
	 */
	public String getFilePath() {
		return storedFilePath;
	}
	
	/**
	 * This method returns the current path of the of the storage folder.
	 * 
	 * @return Location of storage folder.
	 */
	public String getFolderPath() {
		return folderPath;
	}
	
	/**
	 * This method returns the current path of the of the file specified.
	 * 
	 * @return Location of folder.
	 */
	public String getFolderPath(String storageFile) {
		if (storageFile.equals(DEFAULT_FILE_PATH)) {
			return ".";
		}
		return storageFile.substring(0, storageFile.length() - DEFAULT_FILE_PATH.length());
	}

	/**
	 * This method creates a default folder if it does not exist in where the program is run.
	 * 
	 * @param 		None.
	 * @return 		Whether the folder is created successfully or not.
	 */
	protected static boolean createFolder() {
		
		if (!newFolder.exists()) {
			newFolder.mkdir();
		}
		
		return true;
	}

	/**
	 * This is method reads the file path stored in the program's properties file.
	 * 
	 * @param 			None
	 * @return String	File path of where the data file is stored.
	 * @exception 		IOException On file reading error.
	 * @see 			IOException.
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

	/**
	 * This method changes the file path stored in the properties file and moves
	 * the .txt data file to the specified new file path.
	 * 
	 * @param newFolderPath 			New file path specified by user.
	 * @return 							Whether the file path is changed successfully or not.
	 * @throws IOException.
	 * @throws FileNotFoundException.
	 */
	public void changeFolderPath(String newFolderPath) 
			throws IOException, FileNotFoundException {
		System.out.println(newFolderPath);
		String oldFilePath = readFileSettings();
		
		moveFolder(newFolderPath + "/" + DEFAULT_FILE_DIRECTORY + "/");
		
		String newFilePath = newFolderPath + "/" + DEFAULT_FILE_PATH;
		moveFile(oldFilePath, newFilePath);
		
		writeToProperties(newFilePath);
		folderPath = newFolderPath;
	}

	/**
	 * This method writes the specified file path into the default properties file.
	 * 
	 * @param filePath 	File path to be stored.
	 * @return 			Whether the file path is written successfully or not.
	 * @exception 		IOException On input error.
	 * @see 			IOException.
	 */
	protected boolean writeToProperties (String filePath) {

		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(SETTINGS_FILE_PATH);
			prop.setProperty("filePath", filePath);
			prop.store(output, null);
			storedFilePath = filePath;
			return true;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * This method creates a folder in the new file path specified.
	 * 
	 * @param newFilePath 	New file path to create the folder in.
	 * @return 				Nothing.
	 */
	protected boolean moveFolder(String newFilePath) {
		
		File file = new File(newFilePath);

		if (!file.exists()) {
			file = new File(newFilePath);
			file.mkdir();
			return true;
		}
		
		return false;
	}

	/**
	 * This method copies content from a source .txt file to another destination .txt file.
	 * The source .txt file will be deleted at the end of this method.
	 * 
	 * @param oldFilePath 				File path of the source .txt file.
	 * @param newFilePath 				File path of the destination .txt file.
	 * @return 							Nothing.
	 * @throws IOException.
	 * @throws FileNotFoundException.
	 */
	protected void moveFile(String oldFilePath, String newFilePath) 
			throws IOException, FileNotFoundException {

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
	 * 
	 * @param 		None.
	 * @return 		Nothing.
	 * @exception 	IOException On file input error.
	 * @see 		IOException.
	 */
	protected void initFile() {

		mapper.setDateFormat(dateFormat);

		if ( !(file.exists()) ) {
			lists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Item>());
			}
			try {
				writeLists();
			} catch (IOException e) {
				return;
			}
		} else {
			readLists();
		}
	}

	/**
	 * This method checks whether the data file exist or not.
	 * 
	 * @param 	None.
	 * @return 	Nothing.
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
	 * 
	 * @param id 	ID of the list wanted.
	 * @return  	Integer value of list index.
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
	 * 
	 * @param index Index of the original list.
	 * @return 		Integer value of the complementing list.
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
	 * This method stores a Item object into the specified list and updates the data file.
	 * 
	 * @param listIndex 	list index of the list to store the Item into.
	 * @param t 			Item object to store into the list.
	 * @return 				Nothing.
	 * @throws IOException	On file input error.
	 */
	public void create(int listIndex, Item t) throws IOException {
		lists.get(listIndex).add(t);
		writeLists();
	}

	/**
	 * This method retrieves the list of Item objects specified by the list index.
	 * 
	 * @param listIndex Index of the list wanted.
	 * @return 			The list specified.
	 */
	public ArrayList<Item> getList(int listIndex) {
		return lists.get(listIndex);
	}
	
	/**
	 * This method retrieves the list containing all lists of Item objects.
	 * 
	 * @param 	None.
	 * @return 	The list containing all lists of Item objects.
	 */
	protected List<ArrayList<Item>> getLists() {
		return lists;
	}

	/**
	 * This method updates a specified Item in the list of Items where the Item is stored in
	 * and updates the data file.
	 * 
	 * @param listIndex 	Index of the list where the Item to be updated is stored in.
	 * @param t 			The updated Item to be stored.
	 * @return 				Nothing.
	 * @throws IOException	On file input error.
	 */
	public void update(int listIndex, Item oldTask, Item newTask) 
			throws IOException {
		int pos = getPos(listIndex, oldTask);
		if (pos > -1) {
			lists.get(listIndex).set(pos, newTask);
			writeLists();
		}
	}

	/**
	 * This method deletes the specified Item in the list of Items where the Item is stored in
	 * and updates the data file.
	 * 
	 * @param listIndex 	Index of the list where the Item to be deleted is stored in.
	 * @param t 			Item to be deleted.
	 * @return 				Nothing.
	 * @throws IOException 	On file input error.
	 */
	public void delete(int listIndex, Item t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).remove(pos);
			writeLists();
		}
	}

	/**
	 * This method clears a list of Items of all content and updates the data file.
	 * 
	 * @param listIndex 	Index of the list to be cleared.
	 * @return 				Nothing.
	 * @throws IOException	On file input error.
	 */
	protected void clear(int listIndex) throws IOException {
		lists.set(listIndex, new ArrayList<Item>());
		writeLists();
	}

	/**
	 * This method retrieves the position of a specified Item in the list it is stored in.
	 * 
	 * @param listIndex Index of the list that the Item is stored in.
	 * @param t 		Item object that you want to get the position of.
	 * @return 			Position of the Item in the list it is stored in. (0-based)
	 */
	protected int getPos(int listIndex, Item t) {
		return lists.get(listIndex).indexOf(t);
	}

	/**
	 * This method stores the list of Items into the parent list of Items that contains
	 * all the different lists of Items.
	 * 
	 * @param listIndex 	Index of the list to be set into the parent array.
	 * @param list 			The list to be set into the parent array.
	 * @return 				Nothing.
	 * @throws IOException	On file input error.
	 */
	public void setList(int listIndex, ArrayList<Item> list) 
			throws IOException {
		lists.set(listIndex, list);
		writeLists();
	}

	/**
	 * This method writes all current content from the program into the .txt data file.
	 * 
	 * @param 	None.
	 * @return 	Nothing.
	 * @throws 	IOException		On file input error.
	 */
	protected void writeLists() throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}

	/**
	 * This method reads content from the .txt data file and stores the data into the
	 * program. If the content is empty or the file does not exist, default lists with empty
	 * content will be stored in the program.
	 * 
	 * @param 		None.
	 * @return 		Nothing.
	 * @exception 	Exception	On file reading error.
	 * @see 		Exception.
	 */
	protected void readLists() {
		
		try {
			lists = mapper.readValue(file, new TypeReference<List<ArrayList<Item>>>() { });
		} catch (Exception e) {
			lists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Item>());
			}
		}
		return;
	}
}
