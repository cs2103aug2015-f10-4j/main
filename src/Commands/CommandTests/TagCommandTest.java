package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Commands.Command;
import Commands.TagCommand;
import gui.GUIModel;
import main.Item;

public class TagCommandTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: taskID";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: tag <task_id> <tag name>";

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
		TagCommand tagTask = new TagCommand("t1 CS2103");
		TagCommand tagTaskAgain = new TagCommand("t1 CS2105");
		TagCommand tagNextTask = new TagCommand("t2 CS2102");
		TagCommand tagLastTask = new TagCommand("t7 CS2010");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			TagCommand noArgs = new TagCommand("");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			TagCommand tooManyArgs = new TagCommand("t1 t2 t3 t4");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			TagCommand tooFewArgs = new TagCommand("t1");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		// final String ERROR_MESSAGE = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_ID;
		try {
			TagCommand invalidID = new TagCommand("t11 CS2103");
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
		try {
			TagCommand noLetter = new TagCommand("1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			TagCommand wrongLetter = new TagCommand("a1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new TagCommand("t CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command cannotAddSameTag = new TagCommand("t1 CS2103");
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
