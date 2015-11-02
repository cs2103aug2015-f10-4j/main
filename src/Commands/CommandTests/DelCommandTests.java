package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Commands.Command;
import Commands.DelCommand;
import Commands.DoneCommand;
import gui.GUIModel;
import main.Task;

public class DelCommandTests {

	protected static final String MESSAGE_HEADER_INVALID = "\n----- Invalid arguments ---- \n";
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+"Use Format: delete <task_id>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";

	@Before
	public void setUp() {
		ArrayList<Task> testList = new ArrayList<Task>();
		for (int i = 0; i < 10; i ++) {
			testList.add(new Task());
		}
		GUIModel.setTaskList(testList);
		GUIModel.setDoneList(testList);
	}

	@Test
	public void testNormalInputs() throws Exception {
		Command deleteTask = new DelCommand("t1");
		Command deleteTaskAgain = new DelCommand("t1");
		Command deleteNextTask = new DelCommand("t2");
		Command deleteLastTask = new DelCommand("t7");
		Command delDone = new DelCommand("d1");
		Command delDoneAgain = new DelCommand("d1");
		Command delNextDone = new DelCommand("d2");
		Command delLastDone = new DelCommand("d7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			Command noArgs = new DelCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			Command moreArgs = new DelCommand("t1 t2 t3 t4 t5 t6");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidId() {
		final String ERROR_MESSAGE = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_ID;
		try {
			Command wrongLetter = new DelCommand("a1");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "a1"), e.getMessage());
		}
		try {
			DoneCommand invalidID = new DoneCommand("t100");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "t100"), e.getMessage());
		}
		try {
			Command tooLong = new DelCommand("abcdefghijklmnop");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "abcdefghijklmnop"), e.getMessage());
		}
		try {
			Command tooShort = new DelCommand("a");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "a"), e.getMessage());
		}
		try {
			Command justNumber = new DelCommand("1");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "1"), e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.doneList = null;
	}

}
