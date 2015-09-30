package main;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import java.io.File;

import junit.framework.TestCase;

public class StorageTest extends TestCase {

	// create task 1
	Task task1 = new Task();
	Task event1 = new Task();
	Task task2 = new Task();
	Task event2 = new Task();
	
	public void setUp() {
		task1.setType("task");
		task1.setTitle("help mum buy groceries");
		task1.setDescription("apples, pears");
		// task1.setDueDate(new Date());
		task1.getStartTime();
		// task1.setEndTime();
		task1.setRecurrence("weekly");
		
		task2.setType("task");
		task2.setTitle("study for midterms");
		task2.setDescription("sigh");
		// task2.setDueDate();
		task2.getStartTime();
		// task2.setEndTime();
		task2.setRecurrence("daily");
		
		event1.setType("event");
		event1.setTitle("my birthday");
		event1.setDescription("travelling to another country");
		// event1.setDueDate(new Date());
		event1.getStartTime();
		// event1.setEndTime();
		event1.setRecurrence("yearly");
		
		event2.setType("event");
		event2.setTitle("eat dinner at utown");
		event2.setDescription("apples, pears");
		// event1.setDueDate(new Date());
		event2.getStartTime();
		// event1.setEndTime();
		event2.setRecurrence("weekly");
	}

	// tests whether the file specified will be created when the constructor is called
	// successfully tested
	public void testStorageConstructor() {
		try {
			Storage testStorage = new Storage("mytasks.txt");
			assertTrue(testStorage.fileExist());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testCreateTask() {
		// create an arraylist here to compare with
		// use createTask() to update the arraylist
		// use readTask() to read the updated arraylist
		// compare both arraylists
	}

	public void testGetTasks() {
		// create an arraylist here to compare with
		// add the same tasks into storage and read out the arraylist
		// ArrayList<Task> localList = new ArrayList<Task> ();

		// assertEquals(sortedList, TextBuddyFeatures.sortContents(listToTest));
	}

	public void testUpdateTask() {
		// supposed to put the updated task back into the same index
		// create an arraylist here with the already-updated element
		// insert the new updated task using Storage methods
		// get the updated arraylist
		// compare both arraylists

	}

	public void testDeleteTask() {
		// create an arraylist here to compare with
		// delete the same task and read out the arraylist
		// compare both arraylists
	}

	public void testClearTaskList() {
		// create an empty arraylist to compare with
		// clear arraylist using the method
		// compare both arraylists
	}

	public void testGetTaskPos() {
		// create an arraylist, get the index of one of the items
		// using the method, get the index of the same items
		// compare if the indexes are the same
	}

	
	public void testWriteTaskList() {
		// write into the file using Storage methods
		// read out the arraylist from the Storage methods
		// compare if both arraylists are the same	
	}

	public void testReadTaskList() {
		// insert tasks and read tasks ArrayList using Storage class method
		// create an arraylist here to compare with
		// compare both arraylists

	}
}