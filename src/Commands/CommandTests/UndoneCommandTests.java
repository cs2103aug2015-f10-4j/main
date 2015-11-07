package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Commands.Command;
import Commands.DelCommand;
import Commands.UndoneCommand;
import gui.GUIModel;
import main.Item;

public class UndoneCommandTests {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %sitemID";

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
			System.out.println("e: " + e);
			assertTrue(e instanceof StringIndexOutOfBoundsException);
		}
		try {
			UndoneCommand tooManyArgs = new UndoneCommand("d1 d2");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		// final String ERROR_MESSAGE = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_ID;
		try {
			UndoneCommand invalidID = new UndoneCommand("d11");
		} catch (Exception e) {
			System.out.println("e: " + e);
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
		try {
			UndoneCommand noLetter = new UndoneCommand("1");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneCommand wrongLetter = new UndoneCommand("a1");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command tooShort = new UndoneCommand("a");
		} catch (Exception e) {
			// System.out.println("e: " + e);
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			Command youCantUndoneAUndone = new UndoneCommand("t1");
		} catch (Exception e) {
			System.out.println("e: " + e);
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
