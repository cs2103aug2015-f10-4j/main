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
public class DoneParserTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: done <item_id>";

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
		ArgsParserAbstract doneTask = new DoneParser("t1");
		ArgsParserAbstract doneTaskAgain = new DoneParser("t1");
		ArgsParserAbstract doneNextTask = new DoneParser("t2");
		ArgsParserAbstract doneLastTask = new DoneParser("t7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			ArgsParserAbstract noArgs = new DoneParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooManyArgs = new DoneParser("t1 t2");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			ArgsParserAbstract invalidID = new DoneParser("t11");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract noLetter = new DoneParser("1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract wrongLetter = new DoneParser("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new DoneParser("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract youCantDoneADone = new DoneParser("d1");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
