package main;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import java.io.File;

import junit.framework.TestCase;

public class StorageTest extends TestCase {
	
		// create task 1
		Task task1 = new Task();
		
		task1.setType("task");
		// task1.setTitle("help mum buy groceries");
		// task1.setDescription("apples, pears");
		// task1.setDueDate();
	 	// task1.getStartTime();
		// task1.setEndTime();
		// task1.setRecurrence("weekly");
		// create event 1
		Task event1 = new Task();
		Task task2 = new Task();
		Task event2 = new Task();
		// create more events and tasks to add it into the ArrayList 
		
		
	
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
			ArrayList<Task> localList = new ArrayList<Task> ();
			
			// assertEquals(sortedList, TextBuddyFeatures.sortContents(listToTest));
		}
		
		public void testUpdateTask() {
			// not sure what's update task doing
			
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
			
		}
		
		/*
		public void testWriteTaskList {
			// write a file here
			// write a file using Storage 
			File file1 = new File("localFile");
			File file2 = 

			FileUtils.contentEquals(file1, file2);		
		}
		*/
		
		public void testReadTaskList() {
			
		}
}
