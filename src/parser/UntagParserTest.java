package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

/**
 * @@author A0126310Y
 */
public class UntagParserTest {

	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: [item_id]";
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: untag <item_id> <tag name>";

	@Before
	public void setUp() {
		ArrayList<Item> testList = new ArrayList<Item>();
		for (int i = 0; i < 10; i++) {
			testList.add(new Item());
		}
		Set<String> tags1 = new HashSet<String>() {
			{
				add("CS2103");
				add("CS2105");
			}
		};
		Item t1 = testList.get(0);
		t1.setTags(tags1);

		GUIModel.setTaskList(testList);
		GUIModel.setTaskDoneList(testList);
	}

	@Test
	public void testNormalInputs() throws Exception {
		UntagParser untagTask = new UntagParser("t1 CS2103");
		UntagParser untagTaskAgain = new UntagParser("t1 CS2105");
		// UntagParser untagNextTask = new UntagParser("t2 CS2102");
		// UntagParser untagLastTask = new UntagParser("t7 CS2010");
	}

	@Test
	public void testWrongNumArgs() {
		try {
			UntagParser noArgs = new UntagParser("");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
		try {
			UntagParser tooFewArgs = new UntagParser("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_PARAMS, e.getMessage());
		}
	}

	@Test
	public void testInvalidID() {
		try {
			UntagParser invalidID = new UntagParser("t11 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UntagParser noLetter = new UntagParser("1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			UntagParser wrongLetter = new UntagParser("a1 CS2103");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID, e.getMessage());
		}
		try {
			ArgsParserAbstract tooShort = new UntagParser("t CS2103");
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
