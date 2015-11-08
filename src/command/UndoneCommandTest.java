package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class UndoneCommandTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_FORMAT_INVALID = "Use Format: undone <item_id>";
	private static final String MESSAGE_UNDONE_INVALID = 
			"Invalid arguments: [Undone tasks cannot be undone]";

	@Before
	public void setUp() {
		ArrayList<Item> testList = new ArrayList<Item>();
		for (int i = 0; i < 10; i ++) {
			testList.add(new Item());
		}
		GUIModel.setTaskList(testList);
		GUIModel.setTaskDoneList(testList);
	}

	@Test
	public void testNormalInputs() throws Exception {
		UndoneCommand UndoneTask = new UndoneCommand("d1");
		UndoneCommand UndoneTaskAgain = new UndoneCommand("d1");
		UndoneCommand UndoneNextTask = new UndoneCommand("d2");
		UndoneCommand UndoneLastTask = new UndoneCommand("d7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			UndoneCommand noArgs = new UndoneCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneCommand tooManyArgs = new UndoneCommand("d1 d2");
		} catch (Exception e) {
			assertEquals(MESSAGE_FORMAT_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			UndoneCommand invalidID = new UndoneCommand("d11");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneCommand noLetter = new UndoneCommand("1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneCommand wrongLetter = new UndoneCommand("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new UndoneCommand("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command youCantUndoneAUndone = new UndoneCommand("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_UNDONE_INVALID, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
