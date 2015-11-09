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
public class TagParserTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: tag <item_id> <tag name>";

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
		TagParser tagTask = new TagParser("t1 CS2103");
		TagParser tagTaskAgain = new TagParser("t1 CS2105");
		TagParser tagManyTagsAtOnce = new TagParser("t1 t2 t3 t4");
		TagParser tagNextTask = new TagParser("t2 CS2102");
		TagParser tagLastTask = new TagParser("t7 CS2010");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			TagParser noArgs = new TagParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			TagParser tooFewArgs = new TagParser("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			TagParser invalidID = new TagParser("t11 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			TagParser noLetter = new TagParser("1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			TagParser wrongLetter = new TagParser("a1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new TagParser("t CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
	}

}
