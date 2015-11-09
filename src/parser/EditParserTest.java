package parser;

import static org.junit.Assert.*;

/**
 * @@author A0127127L
 */

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class EditParserTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %s";
	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <item_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Unknown field";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	private static final String MESSAGE_INVALID_DATE = "Date";
	private static final String MESSAGE_INVALID_TIME_END = "End time";
	private static final String MESSAGE_INVALID_TIME_START = "Start time";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_ITEM_ERROR = "Unable to edit item %s";
	private static final String MESSAGE_ITEM_EDITED = "Item edited.";

	/** Strings to test with **/
	private static final String EMPTY_STRING = "";
	private static final String WHITESPACE_STRING = "                ";
	private static final String NUMBER_STRING = "1234567890";
	private static final String LONG_STRING = "This is a very long string made out of multiple words that can go on forever and ever and ever and ever";
	private static final String WEIRD_STRING = "T#is str/ng h@s we!rd $ymb^\\s. And punc!tuat!ion?";
	private static final String QUOTES_STRING = "\"This string has 'quotes' of both kinds\"";

	@BeforeClass
	public static void setUp() {
		ArrayList<Item> taskList = new ArrayList<Item>();
		Item task = new Item();
		task.setType("task");
		for (int i = 0; i < 10; i ++) {
			taskList.add(task);
		}
		GUIModel.setTaskList(taskList);
		GUIModel.setTaskDoneList(taskList);
		ArrayList<Item> eventList = new ArrayList<Item>();
		Item event = new Item();
		event.setType("event");
		for (int i = 0; i < 10; i ++) {
			eventList.add(event);
		}
		GUIModel.setEventList(eventList);
		GUIModel.setEventDoneList(eventList);
	}

	@Test
	public void testEditTitleNormalInputs() throws Exception {
		ArgsParserAbstract normalEdit = new EditParser("t1 title Title");
		ArgsParserAbstract normalEditLong = new EditParser("t1 title " + LONG_STRING);
		ArgsParserAbstract normalEditWeird = new EditParser("t1 title " + WEIRD_STRING);
		ArgsParserAbstract normalEditDiffTask = new EditParser("t2 title Title");
		ArgsParserAbstract normalEditLastTask = new EditParser("t10 title Title");
		ArgsParserAbstract normalEditWithPunctuation = new EditParser("t10 title Title");
		ArgsParserAbstract normalEditDoneTask = new EditParser("d1 title Title");
		ArgsParserAbstract normalEditEvent = new EditParser("e1 title Title");
		ArgsParserAbstract normalEditDoneEvent = new EditParser("p1 title Title");
	}

	@Test
	public void testEditDateNormalInputs() throws Exception {
		ArgsParserAbstract normalEdit = new EditParser("t1 date 1 January 2015");
		ArgsParserAbstract normalEditDateFormat = new EditParser("t1 date January 1 2015");
		ArgsParserAbstract normalEditShortDate = new EditParser("t1 date Jan 1");
		ArgsParserAbstract normalEditShortDateFormat = new EditParser("t1 date 1 Jan");
		ArgsParserAbstract normalEditDateNoYear = new EditParser("t1 date January 1");
		ArgsParserAbstract normalEditFlexiDate = new EditParser("t1 date today");
		ArgsParserAbstract normalEditEvent = new EditParser("e1 date 1 January 2015");
		ArgsParserAbstract normalEditDoneTask = new EditParser("d1 date 1 January 2015");
		ArgsParserAbstract normalEditDoneEvent = new EditParser("p1 date 1 January 2015");
	}

	@Test
	public void testEditStartTimeNormalInputs() throws Exception {
		ArgsParserAbstract normalEdit = new EditParser("e1 start time 12pm");
		ArgsParserAbstract normalEditDiffFormat = new EditParser("e1 start time 1200");
		ArgsParserAbstract normalEditDiffFormat2 = new EditParser("e1 start time 12:00");
		ArgsParserAbstract normalEditDiffFormat3 = new EditParser("e1 start time 12 p.m.");
		ArgsParserAbstract normalEditDiffFormat4 = new EditParser("e1 start time 12 pm");
		ArgsParserAbstract normalEditDoneTask = new EditParser("p1 start time 12pm");
	}

	@Test
	public void testEditEndTime() throws Exception {
		ArgsParserAbstract normalEdit = new EditParser("e1 end time 12pm");
		ArgsParserAbstract normalEditDiffFormat = new EditParser("e1 end time 1200");
		ArgsParserAbstract normalEditDiffFormat2 = new EditParser("e1 end time 12:00");
		ArgsParserAbstract normalEditDiffFormat3 = new EditParser("e1 end time 12 p.m.");
		ArgsParserAbstract normalEditDiffFormat4 = new EditParser("e1 end time 12 pm");
		ArgsParserAbstract normalEditDoneTask = new EditParser("p1 end time 12pm");
	}

	@Test
	public void testEditTime() throws Exception {
		ArgsParserAbstract normalEdit = new EditParser("t1 time 12pm");
		ArgsParserAbstract normalEditDiffFormat = new EditParser("t1 time 1200");
		ArgsParserAbstract normalEditDiffFormat2 = new EditParser("t1 time 12:00");
		ArgsParserAbstract normalEditDiffFormat3 = new EditParser("t1 time 12 p.m.");
		ArgsParserAbstract normalEditDiffFormat4 = new EditParser("t1 time 12 pm");
		ArgsParserAbstract normalEditDoneTask = new EditParser("d1 time 12pm");
	}

	@Test
	public void testWrongNumArgs() throws Exception {
		try {
			ArgsParserAbstract noInput = new EditParser(EMPTY_STRING);
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
		try {
			ArgsParserAbstract singleArg = new EditParser("t1");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
		try {
			ArgsParserAbstract twoArgs = new EditParser("t1 title");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
	}

	@Test
	public void testInvalidField() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_FIELD;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract noSuchField = new EditParser("t1 invalidField invalid nonsense");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testEditTaskStartTime() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_TASK_START;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract taskStartTime = new EditParser("t1 start time 12pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testInvalidID() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_ITEM_ID;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract itemOutOfBounds = new EditParser("t100 title test");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
		try {
			ArgsParserAbstract itemZero = new EditParser("t0 title test");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testInvalidDate() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_DATE;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract taskInvalidDate = new EditParser("t1 date Jan 32");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
		try {
			ArgsParserAbstract eventInvalidDate = new EditParser("e1 date Jan 32");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testInvalidTimeStart() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_TIME_START;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract eventStartTime = new EditParser("e1 start time 25pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testInvalidTimeEnd() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_TIME_END;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			ArgsParserAbstract taskEndTime = new EditParser("t1 end time 25pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
		try {
			ArgsParserAbstract eventEndTime = new EditParser("e1 end time 25pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@Test
	public void testInvalidTitle() throws Exception {
		String[] invalidMsg = new String[1];
		invalidMsg[0] = MESSAGE_INVALID_TITLE;
		String result = String.format(MESSAGE_HEADER_INVALID, Arrays.toString(invalidMsg));
		try {
			EditParser taskEndTime = new EditParser("t1 title " + EMPTY_STRING);
		} catch (Exception e) {
			assertEquals(e.getMessage(), result);
		}
	}

	@AfterClass
	public static void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
		GUIModel.eventDoneList = null;
		GUIModel.eventDoneList = null;
	}

}
