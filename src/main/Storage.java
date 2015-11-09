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
	private static final String DEFAULT_FILE_PATH = DEFAULT_FILE_DIRECTORY
			+ "/" + DEFAULT_FILE_NAME;
	private static final String SETTINGS_FILE_NAME = "settings.properties";
	private static final String SETTINGS_FILE_PATH = DEFAULT_FILE_DIRECTORY
			+ "/" + SETTINGS_FILE_NAME;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd MMM yyyy");
	private static File newFolder = new File(DEFAULT_FILE_DIRECTORY);
	private static File file = new File(DEFAULT_FILE_PATH);

	private String storedFilePath;
	private String folderPath;
	private List<ArrayList<Item>> lists;
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * This is the Storage constructor which makes use of createFolder,
	 * writeToProperties, readFileSettings and initFile methods. This
	 * constructor creates a default folder in where the program is run. In this
	 * folder, a .properties file is created to store default program settings
	 * and a default .txt file to store task data.
	 */
	public Storage() {

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
	 * This method creates a default folder if it does not exist in where the
	 * program is run.
	 * 
	 * @return Whether the folder is created successfully or not.
	 */
	protected static boolean createFolder() {
		if (!newFolder.exists()) {
			newFolder.mkdir();
		}
		return true;
	}

	/**
	 * This method retrieves the index of the list complementing to the original
	 * list.
	 * 
	 * @param index
	 *            Index of the original list.
	 * @return Integer value of the complementing list.
	 */
	public static int getComplementListIndex(int index) {
		switch (index) {
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
	 * This method retrieves the list index of the list wanted.
	 * 
	 * @param id
	 *            ID of the list wanted.
	 * @return Integer value of list index.
	 */
	public static int getListIndex(String id) {
		switch (id.charAt(0)) {
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
	 * This method changes the file path stored in the properties file and moves
	 * the .txt data file to the specified new file path.
	 * 
	 * @param newFolderPath
	 *            New file path specified by user.
	 * @return Whether the file path is changed successfully or not.
	 * @throws IOException.
	 * @throws FileNotFoundException.
	 */
	public void changeFolderPath(String newFolderPath) throws IOException,
	FileNotFoundException {

		assert (newFolderPath != null);
		String oldFilePath = readFileSettings();

		moveFolder(newFolderPath + "/" + DEFAULT_FILE_DIRECTORY + "/");

		String newFilePath = newFolderPath + "/" + DEFAULT_FILE_PATH;
		moveFile(oldFilePath, newFilePath);

		writeToProperties(newFilePath);
		folderPath = newFolderPath;
	}

	/**
	 * This method clears a list of Items of all content and updates the data
	 * file.
	 * 
	 * @param listIndex
	 *            Index of the list to be cleared.
	 * @throws IOException
	 *             On file input error.
	 */
	protected void clear(int listIndex) throws IOException {
		lists.set(listIndex, new ArrayList<Item>());
		writeLists();
	}

	/**
	 * This method stores a Item object into the specified list and updates the
	 * data file.
	 * 
	 * @param listIndex
	 *            list index of the list to store the Item into.
	 * @param t
	 *            Item object to store into the list.
	 * @throws IOException
	 *             On file input error.
	 */
	public void create(int listIndex, Item item) throws IOException {
		lists.get(listIndex).add(item);
		writeLists();
	}

	/**
	 * This method deletes the specified Item in the list of Items where the
	 * Item is stored in and updates the data file.
	 * 
	 * @param listIndex
	 *            Index of the list where the Item to be deleted is stored in.
	 * @param t
	 *            Item to be deleted.
	 * @throws IOException
	 *             On file input error.
	 */
	public void delete(int listIndex, Item item) throws IOException {
		int pos = getPos(listIndex, item);
		if (pos > -1) {
			lists.get(listIndex).remove(pos);
			writeLists();
		}
	}

	/**
	 * This method checks whether the data file exist or not.
	 * 
	 * @return whether file exists.
	 */
	protected boolean fileExist() {

		if (file.exists()) {
			return true;
		} else {
			return false;
		}

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
		return storageFile.substring(0, storageFile.length()
				- DEFAULT_FILE_PATH.length());
	}

	/**
	 * This method retrieves the list of Item objects specified by the list
	 * index.
	 * 
	 * @param listIndex
	 *            Index of the list wanted.
	 * @return The list specified.
	 */
	public ArrayList<Item> getList(int listIndex) {
		return lists.get(listIndex);
	}

	/**
	 * This method retrieves the list containing all lists of Item objects.
	 * 
	 * @return The list containing all lists of Item objects.
	 */
	protected List<ArrayList<Item>> getLists() {
		return lists;
	}

	/**
	 * This method retrieves the position of a specified Item in the list it is
	 * stored in.
	 * 
	 * @param listIndex
	 *            Index of the list that the Item is stored in.
	 * @param t
	 *            Item object that you want to get the position of.
	 * @return Position of the Item in the list it is stored in. (0-based)
	 */
	protected int getPos(int listIndex, Item item) {
		return lists.get(listIndex).indexOf(item);
	}

	/**
	 * This method initialises the .txt data file. If the file doesn't exist,
	 * the data file is created and data is written into it. Else if the data
	 * file exist, data is being read and stored in the program.
	 * 
	 * @exception IOException
	 *                On file input error.
	 * @see IOException.
	 */
	protected void initFile() {

		mapper.setDateFormat(dateFormat);

		if (!(file.exists())) {
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
	 * This method copies content from a source .txt file to another destination
	 * .txt file. The source .txt file will be deleted at the end of this
	 * method.
	 * 
	 * @param oldFilePath
	 *            File path of the source .txt file.
	 * @param newFilePath
	 *            File path of the destination .txt file.
	 * @throws IOException.
	 * @throws FileNotFoundException.
	 */
	protected void moveFile(String oldFilePath, String newFilePath)
			throws IOException, FileNotFoundException {

		assert (oldFilePath != null);
		assert (newFilePath != null);

		InputStream inStream = null;
		OutputStream outStream = null;

		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);

		inStream = new FileInputStream(oldFile);
		outStream = new FileOutputStream(newFile);

		byte[] buffer = new byte[1024];

		int length;
		while ((length = inStream.read(buffer)) > 0) {
			outStream.write(buffer, 0, length); // copy file contents over
		}

		inStream.close();
		outStream.close();

		oldFile.delete(); // delete the original file
		file = newFile;
	}

	/**
	 * This method creates a folder in the new file path specified.
	 * 
	 * @param newFilePath
	 *            New file path to create the folder in.
	 * @return whether the folder was moved
	 */
	protected boolean moveFolder(String newFilePath) {

		assert (newFilePath != null);

		File file = new File(newFilePath);

		if (!file.exists()) {
			file = new File(newFilePath);
			file.mkdir();
			return true;
		}

		return false;
	}

	/**
	 * This is method reads the file path stored in the program's properties
	 * file.
	 * 
	 * @return String File path of where the data file is stored.
	 * @exception IOException
	 *                On file reading error.
	 * @see IOException.
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
	 * This method reads content from the .txt data file and stores the data
	 * into the program. If the content is empty or the file does not exist,
	 * default lists with empty content will be stored in the program.
	 * 
	 * @exception Exception
	 *                On file reading error.
	 * @see Exception.
	 */
	protected void readLists() {

		try {
			lists = mapper.readValue(file,
					new TypeReference<List<ArrayList<Item>>>() {});
		} catch (Exception e) {
			lists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Item>());
			}
		}
		return;
	}

	/**
	 * This method stores the list of Items into the parent list of Items that
	 * contains all the different lists of Items.
	 * 
	 * @param listIndex
	 *            Index of the list to be set into the parent array.
	 * @param list
	 *            The list to be set into the parent array.
	 * @throws IOException
	 *             On file input error.
	 */
	public void setList(int listIndex, ArrayList<Item> list) throws IOException {
		lists.set(listIndex, list);
		writeLists();
	}

	/**
	 * This method updates a specified Item in the list of Items where the Item
	 * is stored in and updates the data file.
	 * 
	 * @param listIndex
	 *            Index of the list where the Item to be updated is stored in.
	 * @param t
	 *            The updated Item to be stored.
	 * @throws IOException
	 *             On file input error.
	 */
	public void update(int listIndex, Item oldItem, Item newItem)
			throws IOException {
		int pos = getPos(listIndex, oldItem);
		if (pos > -1) {
			lists.get(listIndex).set(pos, newItem);
			writeLists();
		}
	}

	/**
	 * This method writes all current content from the program into the .txt
	 * data file.
	 * 
	 * @throws IOException
	 *             On file input error.
	 */
	protected void writeLists() throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}

	/**
	 * This method writes the specified file path into the default properties
	 * file.
	 * 
	 * @param filePath
	 *            File path to be stored.
	 * @return Whether the file path is written successfully or not.
	 * @exception IOException
	 *                On input error.
	 * @see IOException.
	 */
	protected boolean writeToProperties(String filePath) {

		assert (filePath != null);

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
}
