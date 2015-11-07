package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Commands.Command;
import Commands.PriorityCommand;
import gui.GUIModel;
import main.Item;

public class PriorityCommandTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %spriority";
	protected static final String MESSAGE_ID_INVALID = "Invalid arguments: %sitemID, priority";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: set <task id> <priority>";

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
		PriorityCommand tagTask = new PriorityCommand("t1 high");
		PriorityCommand tagTaskAgain = new PriorityCommand("t1 medium");
		PriorityCommand tagTaskWithSamePriorityAgain = new PriorityCommand("t1 medium");
		PriorityCommand tagNextTask = new PriorityCommand("t2 low");
		PriorityCommand tagLastTask = new PriorityCommand("t7 high");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			PriorityCommand noArgs = new PriorityCommand("");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			PriorityCommand tooManyArgs = new PriorityCommand("t1 8 9 10");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			PriorityCommand tooFewArgs = new PriorityCommand("t1");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		// final String ERROR_MESSAGE = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_ID;
		try {
			PriorityCommand invalidID = new PriorityCommand("t11 8");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
		try {
			PriorityCommand noLetter = new PriorityCommand("1 6");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			PriorityCommand wrongLetter = new PriorityCommand("a1 5");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new PriorityCommand("t 10");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			Command priorityTooLarge = new PriorityCommand("t1 11");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command priorityTooSmall = new PriorityCommand("t1 -1");
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
