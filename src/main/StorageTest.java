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
	public void testStorageConstructor() throws IOException {
			Storage testStorage = new Storage("mytasks.txt");
			assertTrue(testStorage.fileExist());
	}

	public void testCreateTask() throws IOException {
		// create an arraylist here to compare with
		// use createTask() to update the arraylist
		// use readTask() to read the updated arraylist
		// compare both arraylists
		ArrayList<Task> localArray = new ArrayList<Task> ();
		
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1); // adding the updated element into localArray
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding original items into Storage array
		
		ArrayList<Task> testingArray = testStorage.getTasks();
		assertEquals(localArray, testingArray);
		
	}

	public void testGetTasks() throws IOException {
		// create an arraylist here to compare with
		// add the same tasks into storage and read out the arraylist
		ArrayList<Task> localArray = new ArrayList<Task> ();
		
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1); // adding the updated element into localArray
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding original items into Storage array
		
		ArrayList<Task> testingArray = testStorage.getTasks();
		assertEquals(localArray, testingArray);

	}

	public void testUpdateTask() throws IOException {
		// supposed to put the updated task back into the same index
		// create an arraylist here with the already-updated element
		// create storage array with the old element
		// insert the new updated task using Storage methods
		// get the updated arraylist
		// compare both arraylists
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding original items into Storage array
		
		event1.setType("event");
		event1.setTitle("friend's birthday");
		event1.setDescription("need to buy present");
		event1.getStartTime();
		event1.setRecurrence("yearly"); // updating the content of event1
		
		ArrayList<Task> localArray = new ArrayList<Task> ();
		
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1); // adding the updated element into localArray
		localArray.add(event2); // adding items into local array
		
		testStorage.updateTask(event1); // updating with the newly updated event1
		ArrayList<Task> testingArray = testStorage.readTaskList();
		assertEquals(localArray, testingArray);

	}

	public void testDeleteTask() throws IOException {
		// create an arraylist here to compare with
		// delete the same task and read out the arraylist
		// compare both arraylists
		ArrayList<Task> localArray = new ArrayList<Task> ();
		
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding items into Storage array
		
		testStorage.deleteTask(event1);
		ArrayList<Task> testingArray = testStorage.readTaskList();
		assertEquals(localArray, testingArray);
	}

	public void testClearTaskList() throws IOException {
		// create an empty arraylist to compare with
		// clear arraylist using the method
		// compare both arraylists
		ArrayList<Task> localArray = new ArrayList<Task> (); // empty array to compare with
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding items into Storage array
		
		testStorage.clearTaskList();
		ArrayList<Task> testingArray = testStorage.readTaskList();
		assertEquals(localArray, testingArray);
	}

	public void testGetTaskPos() throws IOException {
		// create an arraylist, get the index of one of the items
		// using Storage method, get the index of the same items
		// compare if the indexes are the same
		ArrayList<Task> localArray = new ArrayList<Task> ();
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1);
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding items into Storage array
		
		int taskPos = testStorage.getTaskPos(event1);
		assertEquals(taskPos, 2);
	}

	
	public void testWriteTaskList() throws IOException {
		// write into the file using Storage methods
		// read out the arraylist from the Storage methods
		// compare if both arraylists are the same
		ArrayList<Task> localArray = new ArrayList<Task> ();
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1);
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2); //adding items into Storage array
		
		testStorage.writeTaskList();
		ArrayList<Task> testingArray = testStorage.readTaskList();
		assertEquals(localArray, testingArray);
		
		
	}
	

	
	public void testReadTaskList() throws IOException {
		// insert tasks and read tasks ArrayList using Storage class method
		// create an arraylist here to compare with
		// compare both arraylists
		ArrayList<Task> localArray = new ArrayList<Task> ();
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1);
		localArray.add(event2); // adding items into local array
		
		Storage testStorage = new Storage("mytasks.txt");
		
		testStorage.clearTaskList(); // clears previous content
		
		testStorage.createTask(task1);
		testStorage.createTask(task2);
		testStorage.createTask(event1);
		testStorage.createTask(event2);
		
		ArrayList<Task> testingArray = testStorage.readTaskList();
		assertEquals(localArray, testingArray);
		
	}
	
}
