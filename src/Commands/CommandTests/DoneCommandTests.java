package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Commands.Command;
import Commands.DelCommand;
import Commands.DoneCommand;
import main.GUIModel;
import main.Task;

public class DoneCommandTests {

	protected static final String MESSAGE_HEADER_INVALID = "\n----- Invalid arguments ---- \n";
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: done <task_id>";
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
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			DoneCommand tooManyArgs = new DoneCommand("t1 t2");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		final String ERROR_MESSAGE = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_ID;
		try {
			DoneCommand invalidID = new DoneCommand("t11");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "t11"), e.getMessage());
		}
		try {
			DoneCommand noLetter = new DoneCommand("1");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "1"), e.getMessage());
		}
		try {
			DoneCommand wrongLetter = new DoneCommand("a1");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "a1"), e.getMessage());
		}
		try {
			Command tooShort = new DelCommand("a");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "a"), e.getMessage());
		}
		try {
			Command youCantDoneADone = new DelCommand("d1");
		} catch (Exception e) {
			assertEquals(String.format(ERROR_MESSAGE, "d1"), e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.doneList = null;
	}

}
