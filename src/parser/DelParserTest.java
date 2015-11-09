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
	private static final String MESSAGE_ERROR = String.format(MESSAGE_HEADER_INVALID, MESSAGE_INVALID_ITEM_ID);

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
		ArgsParserSkeleton deleteTask = new DelParser("t1");
		ArgsParserSkeleton deleteTaskAgain = new DelParser("t1");
		ArgsParserSkeleton deleteNextTask = new DelParser("t2");
		ArgsParserSkeleton deleteLastTask = new DelParser("t7");
		ArgsParserSkeleton delDone = new DelParser("d1");
		ArgsParserSkeleton delDoneAgain = new DelParser("d1");
		ArgsParserSkeleton delNextDone = new DelParser("d2");
		ArgsParserSkeleton delLastDone = new DelParser("d7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			ArgsParserSkeleton noArgs = new DelParser("");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			ArgsParserSkeleton moreArgs = new DelParser("t1 t2 t3 t4 t5 t6");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
	}

	@Test
	public void testInvalidId() {
		try {
			ArgsParserSkeleton wrongLetter = new DelParser("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			DoneParser invalidID = new DoneParser("t100");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			ArgsParserSkeleton tooLong = new DelParser("abcdefghijklmnop");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			ArgsParserSkeleton tooShort = new DelParser("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		try {
			ArgsParserSkeleton justNumber = new DelParser("1");
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
