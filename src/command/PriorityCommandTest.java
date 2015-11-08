package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class PriorityCommandTest {

	private static final String MESSAGE_ID_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_VALUE_INVALID = "Invalid arguments: [priority]";
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
		PriorityCommand noPriority = new PriorityCommand("t1");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			PriorityCommand noArgs = new PriorityCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			PriorityCommand tooManyArgs = new PriorityCommand("t1 high low medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			PriorityCommand invalidID = new PriorityCommand("t11 high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			PriorityCommand noLetter = new PriorityCommand("1 low");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			PriorityCommand wrongLetter = new PriorityCommand("a1 medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new PriorityCommand("t high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			Command priorityNumber = new PriorityCommand("t1 11");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
		try {
			Command priorityNegNumber = new PriorityCommand("t1 -1");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
