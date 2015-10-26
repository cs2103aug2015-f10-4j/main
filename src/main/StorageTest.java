package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

import junit.framework.TestCase;

public class StorageTest extends TestCase {

	Task task1 = new Task();
	Task event1 = new Task();
	Task task2 = new Task();
	Task event2 = new Task();
	
	@Before
	public void setUp() {
		task1.setType("task");
		task1.setTitle("help mum buy groceries");
		task1.setDescription("apples, pears");
		task1.setDueDate(createDateObjects(1992, 3, 17, 15, 9, 17));
		task1.setStartTime(900);
		task1.setEndTime(2200);
		task1.setRecurrence(RecurrencePeriod.WEEKLY);
		task1.setPriority(1);
		
		task2.setType("task");
		task2.setTitle("study for midterms");
		task2.setDescription("sigh");
		task2.setDueDate(createDateObjects(1993, 10, 12, 3, 8, 16));
		task2.setStartTime(800);
		task2.setEndTime(2000);
		task2.setRecurrence(RecurrencePeriod.DAILY);
		task2.setPriority(2);
		
		event1.setType("event");
		event1.setTitle("my birthday");
		event1.setDescription("travelling to another country");
		event1.setDueDate(createDateObjects(1988, 2, 16, 8, 18, 58));
		event1.setStartTime(700);
		event1.setEndTime(1800);
		event1.setRecurrence(RecurrencePeriod.YEARLY);
		event1.setPriority(3);
		
		event2.setType("event");
		event2.setTitle("eat dinner at utown");
		event2.setDescription("apples, pears");
		event2.setDueDate(createDateObjects(1988, 8, 18, 3, 19, 16));
		event2.setStartTime(500);
		event2.setEndTime(1400);
		event2.setRecurrence(RecurrencePeriod.DAILY);
		event2.setPriority(4);
	}
	
	/******************* HELPER METHODS *******************/
	
	// creates localArray by adding default tasks to test against
	private void createLocalArray(ArrayList<Task> localArray) {
		localArray.add(task1);
		localArray.add(task2);
		localArray.add(event1); // adding the updated element into localArray
		localArray.add(event2); // adding items into local array
	}
	
	// this method helps to add everything into the testing array using createTask()
	// method in Storage.java
	private void creatingTasks(Storage testStorage) {
		try {
			testStorage.createTask(task1);
			testStorage.createTask(task2);
			testStorage.createTask(event1);
			testStorage.createTask(event2); //adding original items into Storage array
		} catch (IOException e) {
			// print exception
		}
	}
	
	// creates localArray by adding default tasks to test against
	private Date createDateObjects(int year, int month, int day, int hour, int min, int sec) {
		Calendar date = Calendar.getInstance();
		date.clear();
			
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DATE, day);
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, min);
		date.set(Calendar.SECOND, sec);
			
		return date.getTime();
	}
		
	/******************* END OF HELPER METHODS *******************/

	/******************* UNIT TEST CASES *******************/
	// tests whether the file specified will be created when the constructor is called
	// successfully tested
	@Test
	public void testStorageConstructor() throws IOException {
			Storage testStorage = new Storage("mytasks.txt");
			assertTrue(testStorage.fileExist());
	}

	@Test
	public void testCreateTask() throws IOException {

		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		
		Storage testStorage = new Storage("mytasks.txt");
		testStorage.clearTaskList(); // clears previous content
		creatingTasks(testStorage);
		
		ArrayList<Task> testingArray = testStorage.getTasks();
		// System.out.println("size of array read from file - createTasks: " + testingArray.size());
		assertEquals(localArray.toString(), testingArray.toString());
		
	}

	@Test
	public void testGetTasks() throws IOException {

		ArrayList<Task> localArray = new ArrayList<Task> ();	
		createLocalArray(localArray);
		
		Storage testStorage = new Storage("mytasks.txt");
		testStorage.clearTaskList(); // clears previous content
		creatingTasks(testStorage);
		
		ArrayList<Task> testingArray = testStorage.getTasks();
		// System.out.println("size of array read from file - getTasks: " + testingArray.size());
		assertEquals(localArray, testingArray);

	}

	@Test
	public void testUpdateTask() throws IOException {

		Storage testStorage = new Storage("mytasks.txt");
		testStorage.clearTaskList(); // clears previous content	
		creatingTasks(testStorage);
		
		event1.setType("event");
		event1.setTitle("friend's birthday");
		event1.setDescription("need to buy present");
		event1.setRecurrence(RecurrencePeriod.YEARLY); // updating the content of event1
		
		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		
		testStorage.updateTask(event1); // updating with the newly updated event1
		ArrayList<Task> testingArray = testStorage.readTaskList();
		// System.out.println("size of array read from file - updateTask: " + testingArray.size());
		assertEquals(localArray.toString(), testingArray.toString());
	}
	
	@Test
	public void testDeleteTask() throws IOException {
		
		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		localArray.remove(2);
		
		Storage testStorage = new Storage("mytasks.txt");	
		testStorage.clearTaskList(); // clears previous content	
		creatingTasks(testStorage);
		
		testStorage.deleteTask(event1);
		ArrayList<Task> testingArray = testStorage.readTaskList();
		// System.out.println("size of array read from file - deleteTask " + testingArray.size());
		assertEquals(localArray.toString(), testingArray.toString());
	}
	
	
	@Test
	public void testClearTaskList() throws IOException {

		ArrayList<Task> localArray = new ArrayList<Task> (); // empty array to compare with
		
		Storage testStorage = new Storage("mytasks.txt");	
		testStorage.clearTaskList(); // clears previous content	
		creatingTasks(testStorage);
		
		testStorage.clearTaskList();
		ArrayList<Task> testingArray = testStorage.readTaskList();
		// System.out.println("size of array read from file - clearTaskList: " + testingArray.size());
		assertEquals(localArray.toString(), testingArray.toString());
	}
	

	@Test
	public void testGetTaskPos() throws IOException {
		
		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		
		Storage testStorage = new Storage("mytasks.txt");	
		testStorage.clearTaskList(); // clears previous content	
		creatingTasks(testStorage);
		
		int taskPos = testStorage.getTaskPos(event1);
		// System.out.println("taskPos: " + taskPos);
		assertEquals(taskPos, 2);
	}

	@Test
	public void testWriteTaskList() throws IOException {

		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		
		Storage testStorage = new Storage("mytasks.txt");
		testStorage.clearTaskList(); // clears previous content
		creatingTasks(testStorage);
		
		testStorage.writeTaskList();
		ArrayList<Task> testingArray = testStorage.readTaskList();
		// System.out.println("size of array read from file - writeTaskList: " + testingArray.size());
		assertEquals(localArray.toString(), testingArray.toString());
		
		
	}
	
	@Test
	public void testReadTaskList() throws IOException {

		ArrayList<Task> localArray = new ArrayList<Task> ();
		createLocalArray(localArray);
		
		Storage testStorage = new Storage("mytasks.txt");
		testStorage.clearTaskList(); // clears previous content
		creatingTasks(testStorage);
		
		ArrayList<Task> testingArray = testStorage.readTaskList();
		for (int i = 0; i < testingArray.size(); i++) {
			Task local = localArray.get(i);
			Task test = testingArray.get(i);
			
			System.out.println("local: " + local.getRecurrence());
			System.out.println("test: " + test.getRecurrence());
		}
		System.out.println();
		assertEquals(localArray, testingArray);
	}
	
}
