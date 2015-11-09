package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

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
		ArgsParserSkeleton tagTask = new PriorityParser("t1 high");
		ArgsParserSkeleton tagTaskAgain = new PriorityParser("t1 medium");
		ArgsParserSkeleton tagTaskWithSamePriorityAgain = new PriorityParser("t1 medium");
		ArgsParserSkeleton tagNextTask = new PriorityParser("t2 low");
		ArgsParserSkeleton tagLastTask = new PriorityParser("t7 high");
		ArgsParserSkeleton noPriority = new PriorityParser("t1");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			ArgsParserSkeleton noArgs = new PriorityParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton tooManyArgs = new PriorityParser("t1 high low medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			ArgsParserSkeleton invalidID = new PriorityParser("t11 high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton noLetter = new PriorityParser("1 low");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton wrongLetter = new PriorityParser("a1 medium");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton tooShort = new PriorityParser("t high");
		} catch (Exception e) {
			assertEquals(MESSAGE_ID_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton priorityNumber = new PriorityParser("t1 11");
		} catch (Exception e) {
			assertEquals(MESSAGE_VALUE_INVALID, e.getMessage());
		}
		try {
			ArgsParserSkeleton priorityNegNumber = new PriorityParser("t1 -1");
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
