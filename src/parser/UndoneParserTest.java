package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class UndoneParserTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_FORMAT_INVALID = "Use Format: undone <item_id>";
	private static final String MESSAGE_UNDONE_INVALID = 
			"Invalid arguments: [Undone tasks cannot be undone]";

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
		UndoneParser UndoneTask = new UndoneParser("d1");
		UndoneParser UndoneTaskAgain = new UndoneParser("d1");
		UndoneParser UndoneNextTask = new UndoneParser("d2");
		UndoneParser UndoneLastTask = new UndoneParser("d7");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			UndoneParser noArgs = new UndoneParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneParser tooManyArgs = new UndoneParser("d1 d2");
		} catch (Exception e) {
			assertEquals(MESSAGE_FORMAT_INVALID, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			UndoneParser invalidID = new UndoneParser("d11");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneParser noLetter = new UndoneParser("1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UndoneParser wrongLetter = new UndoneParser("a1");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new UndoneParser("a");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract youCantUndoneAUndone = new UndoneParser("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_UNDONE_INVALID, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
