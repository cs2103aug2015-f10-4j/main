package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageTest {

	public static final int NUM_LISTS = 4;

	private static final String DEFAULT_FILE_DIRECTORY = "magical";
	private static final String DEFAULT_FILE_NAME = "storage.txt";
	private static final String DEFAULT_FILE_PATH = DEFAULT_FILE_DIRECTORY + "/" + DEFAULT_FILE_NAME;
	private static final String SETTINGS_FILE_NAME = "settings.properties";
	private static final String SETTINGS_FILE_PATH = DEFAULT_FILE_DIRECTORY + "/" + SETTINGS_FILE_NAME;
	

	Item task1 = new Item();
	Item event1 = new Item();
	Item task2 = new Item();
	Item event2 = new Item();

	@Before
	public void setUp() {
		task1.setType("task");
		task1.setTitle("help mum buy groceries");
		task1.setEndDate(createDateObjects(1992, 3, 17, 22, 0, 0));
		task1.setEndTime(2200);
		task1.setPriority("medium");

		task2.setType("task");
		task2.setTitle("study for midterms");
		task2.setEndDate(createDateObjects(1993, 10, 12, 20, 0, 0));
		task2.setEndTime(2000);
		task2.setPriority("high");

		event1.setType("event");
		event1.setTitle("my birthday");
		event1.setStartDate(createDateObjects(1988, 2, 12, 7, 0, 0));
		event1.setEndDate(createDateObjects(1988, 2, 16, 18, 0, 0));
		event1.setStartTime(700);
		event1.setEndTime(1800);
		event1.setPriority("medium");

		event2.setType("event");
		event2.setTitle("eat dinner at utown");
		event2.setStartDate(createDateObjects(1988, 2, 12, 5, 0, 0));
		event2.setEndDate(createDateObjects(1988, 8, 18, 14, 0, 0));
		event2.setStartTime(500);
		event2.setEndTime(1400);
		event2.setPriority("low");
	}

	/******************* HELPER METHODS *******************/

	// creates localArray by adding default tasks to test against
	private void createLocalArray(List<ArrayList<Item>> localLists) {

		for (int i = 0; i < NUM_LISTS; i++) {
			localLists.add(new ArrayList<Item>());
		}	
		localLists.get(0).add(task1);
		localLists.get(0).add(task2);
		localLists.get(2).add(event1); // adding the updated element into localArray
		localLists.get(2).add(event2); // adding items into local array
	}

	/*
	 * this method helps to add everything into the testing array using create()
	 * method in Storage.java
	 */
	private void creatingItems(Storage testStorage) {
		try {
			testStorage.create(0, task1);
			testStorage.create(0, task2);
			testStorage.create(2, event1);
			testStorage.create(2, event2); //adding original items into Storage array
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private CustomDate createDateObjects(int year, int month, int day, int hour, int min, int sec) {
		Calendar date = Calendar.getInstance();
		date.clear();

		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DATE, day);
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, min);
		date.set(Calendar.SECOND, sec);

		CustomDate dateToReturn = new CustomDate(date.getTime());
		return dateToReturn;
	}

	/******************* END OF HELPER METHODS *******************/

	/******************* UNIT TEST CASES *******************/
	// tests whether the file specified will be created when the constructor is called.
	@Test
	public void testStorageConstructor() throws IOException {
		Storage testStorage = new Storage();
		assertTrue(testStorage.fileExist());
	}

	// tests whether the magical folder will be created.
	@Test
	public void testCreateFolder() {
		Storage testStorage = new Storage();
		assertTrue(testStorage.createFolder());
	}

	// tests whether file path is written into .properties file.
	@Test
	public void testWriteToProperties() {
		Storage testStorage = new Storage();
		assertTrue(testStorage.writeToProperties(DEFAULT_FILE_PATH));
	}

	// tests whether stored file path is read from .properties file.
	@Test
	public void testReadFileSettings() {
		Storage testStorage = new Storage();
		String defaultFileSettings = testStorage.readFileSettings();
		assertEquals(DEFAULT_FILE_PATH, defaultFileSettings);
	}

	// tests whether Items will be created.
	@Test
	public void testCreateTask() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		List<ArrayList<Item>> testingLists = testStorage.getLists();
		assertEquals(localLists, testingLists);	
	}

	// tests whether Items list can be retrieved.
	@Test
	public void testGetTaskList() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		ArrayList<Item> testingList = testStorage.getList(0);
		ArrayList<Item> localList = localLists.get(0);
		assertEquals(localList, testingList);
	}

	// tests whether Items can be updated.
	@Test
	public void testUpdateTask() throws IOException {

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		Item oldEvent = event1;

		event1.setType("event");
		event1.setTitle("friend's birthday"); // updating the content of event1

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		testStorage.update(2, oldEvent, event1); // updating with the newly updated event1
		List<ArrayList<Item>> testingLists = testStorage.getLists(); // get updated lists
		assertEquals(localLists, testingLists);
	}

	// tests whether Items can be deleted.
	@Test
	public void testDeleteTask() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);
		localLists.get(2).remove(0);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		testStorage.delete(2, event1);
		List<ArrayList<Item>> testingLists = testStorage.getLists(); // get updated lists
		assertEquals(localLists, testingLists);
	}

	// tests whether list can be cleared.
	@Test
	public void testClearTaskList() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);
		localLists.get(0).clear(); // clear list 0

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		testStorage.clear(0);
		List<ArrayList<Item>> testingLists = testStorage.getLists(); // get updated lists
		assertEquals(localLists, testingLists);
	}

	// tests whether the correct position of an Item in its respective list can be retrieved.
	@Test
	public void testGetPos() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		int taskPos = testStorage.getPos(2, event1);
		assertEquals(taskPos, 0);
	}

	// tests whether data can be written into storage.
	@Test
	public void testWriteLists() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		testStorage.writeLists();
		List<ArrayList<Item>> testingLists = testStorage.getLists(); // get lists from .txt
		assertEquals(localLists, testingLists);	
	}

	// tests whether data can be read from storage.
	@Test
	public void testReadTaskList() throws IOException {

		List<ArrayList<Item>> localLists = new ArrayList<ArrayList<Item>>(NUM_LISTS);
		createLocalArray(localLists);

		Storage testStorage = new Storage();
		testStorage.clear(0); // clears previous tasks content
		testStorage.clear(2); // clears previous events content
		creatingItems(testStorage);

		testStorage.readLists(); // read lists back from .txt file first
		List<ArrayList<Item>> testingLists = testStorage.getLists();
		assertEquals(localLists, testingLists);
	}

}
