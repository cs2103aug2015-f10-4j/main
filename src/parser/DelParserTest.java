package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class DelParserTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %s";
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: delete <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "[item_id]";
	private static final String MESSAGE_ERROR = String.format(
			MESSAGE_HEADER_INVALID, MESSAGE_INVALID_ITEM_ID);

	@Before
	public void setUp() {
		ArrayList<Item> testList = new ArrayList<Item>();
		for (int i = 0; i < 10; i++) {
			testList.add(new Item());
		}
		GUIModel.setTaskList(testList);
		GUIModel.setTaskDoneList(testList);
	}

	@Test
	public void testNormalInputs() throws Exception {
		ArgsParserAbstract deleteTask = new DelParser("t1");
		ArgsParserAbstract deleteTaskAgain = new DelParser("t1");
		ArgsParserAbstract deleteNextTask = new DelParser("t2");
		ArgsParserAbstract deleteLastTask = new DelParser("t7");
		ArgsParserAbstract delDone = new DelParser("d1");
		ArgsParserAbstract delDoneAgain = new DelParser("d1");
		ArgsParserAbstract delNextDone = new DelParser("d2");
		ArgsParserAbstract delLastDone = new DelParser("d7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			ArgsParserAbstract noArgs = new DelParser("");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			ArgsParserAbstract moreArgs = new DelParser("t1 t2 t3 t4 t5 t6");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
	}

	@Test
	public void testInvalidId() {
		try {
			ArgsParserAbstract wrongLetter = new DelParser("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			DoneParser invalidID = new DoneParser("t100");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			ArgsParserAbstract tooLong = new DelParser("abcdefghijklmnop");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new DelParser("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			ArgsParserAbstract justNumber = new DelParser("1");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
