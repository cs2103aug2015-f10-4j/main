package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class StorageTest {

	private static final String SORTED_TEXT_1 = "Apple Seoul Carrot";
	private static final String SORTED_TEXT_2 = "Amsterdam Copenhagen Stockholm";
	private static final String SORTED_TEXT_3 = "Seoul Tokyo Singapore";
	private static final String SORTED_TEXT_4 = "Football Skateboard Swimming";
	private static final String SORTED_TEXT_5 = "Zebra Seoul Dog";
	
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETED_LINE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE_ALL = "all content deleted from %1$s";
	
	@Test
	public void testAdd() {
		
		ArrayList<String> contents = new ArrayList<String>();
		String newContent = "paris baguette";
		String fileName = "mytextfile";
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		assertEquals(String.format(MESSAGE_ADDED, fileName, newContent), 
				Storage.writeFile(fileName, contents, "ADD", newContent));
	}
	
	@Test
	public void testDelete() {
		
		ArrayList<String> contents = new ArrayList<String>();
		String removedElement = SORTED_TEXT_5;
		String fileName = "mytextfile";
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		
		assertEquals(String.format(MESSAGE_DELETED_LINE, fileName, removedElement), 
				Storage.writeFile(fileName, contents, "DELETE", "1"));
	}
	
	@Test
	public void testClear() {
		ArrayList<String> contents = new ArrayList<String>();
		String fileName = "mytextfile";
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		Collections.sort(contents); // to make sure that this list to test against is sorted
		
		assertEquals(String.format(MESSAGE_DELETE_ALL, fileName), 
				Storage.writeFile(fileName, contents, "CLEAR", ""));
	}

}
