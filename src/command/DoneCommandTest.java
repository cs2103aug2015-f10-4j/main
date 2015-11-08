package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class DoneCommandTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: done <item_id>";

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
		DoneCommand doneTask = new DoneCommand("t1");
		DoneCommand doneTaskAgain = new DoneCommand("t1");
		DoneCommand doneNextTask = new DoneCommand("t2");
		DoneCommand doneLastTask = new DoneCommand("t7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			DoneCommand noArgs = new DoneCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			DoneCommand tooManyArgs = new DoneCommand("t1 t2");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			DoneCommand invalidID = new DoneCommand("t11");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			DoneCommand noLetter = new DoneCommand("1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			DoneCommand wrongLetter = new DoneCommand("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new DoneCommand("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command youCantDoneADone = new DoneCommand("d1");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
