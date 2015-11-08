package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class UntagCommandTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: untag <item_id> <tag name>";

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
		UntagCommand untagTask = new UntagCommand("t1 CS2103");
		UntagCommand untagTaskAgain = new UntagCommand("t1 CS2105");
		UntagCommand untagNextTask = new UntagCommand("t2 CS2102");
		UntagCommand untagLastTask = new UntagCommand("t7 CS2010");
		UntagCommand multipleUntags = new UntagCommand("t1 t2 t3 t4");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			UntagCommand noArgs = new UntagCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			UntagCommand tooFewArgs = new UntagCommand("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			UntagCommand invalidID = new UntagCommand("t11 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UntagCommand noLetter = new UntagCommand("1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UntagCommand wrongLetter = new UntagCommand("a1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new UntagCommand("t CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
