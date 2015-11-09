package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

/**
 * @@author A0126310Y
 */
public class PriorityParserTest {

	private static final String MESSAGE_ID_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_VALUE_INVALID = "Invalid arguments: [Priority]";
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
		ArgsParserAbstract tagTask = new PriorityParser("t1 high");
		ArgsParserAbstract tagTaskAgain = new PriorityParser("t1 medium");
		ArgsParserAbstract tagTaskWithSamePriorityAgain = new PriorityParser("t1 medium");
		ArgsParserAbstract tagNextTask = new PriorityParser("t2 low");
		ArgsParserAbstract tagLastTask = new PriorityParser("t7 high");
		ArgsParserAbstract noPriority = new PriorityParser("t1");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			ArgsParserAbstract noArgs = new PriorityParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooManyArgs = new PriorityParser("t1 high low medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			ArgsParserAbstract invalidID = new PriorityParser("t11 high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract noLetter = new PriorityParser("1 low");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract wrongLetter = new PriorityParser("a1 medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new PriorityParser("t high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract priorityNumber = new PriorityParser("t1 11");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract priorityNegNumber = new PriorityParser("t1 -1");
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
