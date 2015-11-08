package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Commands.Command;
import Commands.EditCommand;
import gui.GUIModel;
import main.Item;

public class EditCommandTests {

	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <item_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Unknown field";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	private static final String MESSAGE_INVALID_DATE = "Date";
	private static final String MESSAGE_INVALID_TIME_END = "End time";
	private static final String MESSAGE_INVALID_TIME_START = "Start time";
	private static final String MESSAGE_INVALID_ITEM_ID = "taskID";
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
		Command normalEdit = new EditCommand("t1 title Title");
		Command normalEditLong = new EditCommand("t1 title " + LONG_STRING);
		Command normalEditWeird = new EditCommand("t1 title " + WEIRD_STRING);
		Command normalEditDiffTask = new EditCommand("t2 title Title");
		Command normalEditLastTask = new EditCommand("t10 title Title");
		Command normalEditWithPunctuation = new EditCommand("t10 title Title");
		Command normalEditDoneTask = new EditCommand("d1 title Title");
		Command normalEditEvent = new EditCommand("e1 title Title");
		Command normalEditDoneEvent = new EditCommand("p1 title Title");
	}

	@Test
	public void testEditDateNormalInputs() throws Exception {
		Command normalEdit = new EditCommand("t1 date 1 January 2015");
		Command normalEditDateFormat = new EditCommand("t1 date January 1 2015");
		Command normalEditShortDate = new EditCommand("t1 date Jan 1");
		Command normalEditShortDateFormat = new EditCommand("t1 date 1 Jan");
		Command normalEditDateNoYear = new EditCommand("t1 date January 1");
		Command normalEditFlexiDate = new EditCommand("t1 date today");
		Command normalEditEvent = new EditCommand("e1 date 1 January 2015");
		Command normalEditDoneTask = new EditCommand("d1 date 1 January 2015");
		Command normalEditDoneEvent = new EditCommand("p1 date 1 January 2015");
	}

	@Test
	public void testEditStartTimeNormalInputs() throws Exception {
		Command normalEdit = new EditCommand("e1 start time 12pm");
		Command normalEditDiffFormat = new EditCommand("e1 start time 1200");
		Command normalEditDiffFormat2 = new EditCommand("e1 start time 12:00");
		Command normalEditDiffFormat3 = new EditCommand("e1 start time 12 p.m.");
		Command normalEditDiffFormat4 = new EditCommand("e1 start time 12 pm");
		Command normalEditDoneTask = new EditCommand("p1 start time 12pm");
	}

	@Test
	public void testEditEndTime() throws Exception {
		Command normalEdit = new EditCommand("e1 end time 12pm");
		Command normalEditDiffFormat = new EditCommand("e1 end time 1200");
		Command normalEditDiffFormat2 = new EditCommand("e1 end time 12:00");
		Command normalEditDiffFormat3 = new EditCommand("e1 end time 12 p.m.");
		Command normalEditDiffFormat4 = new EditCommand("e1 end time 12 pm");
		Command normalEditDoneTask = new EditCommand("p1 end time 12pm");
	}

	@Test
	public void testEditTime() throws Exception {
		Command normalEdit = new EditCommand("t1 time 12pm");
		Command normalEditDiffFormat = new EditCommand("t1 time 1200");
		Command normalEditDiffFormat2 = new EditCommand("t1 time 12:00");
		Command normalEditDiffFormat3 = new EditCommand("t1 time 12 p.m.");
		Command normalEditDiffFormat4 = new EditCommand("t1 time 12 pm");
		Command normalEditDoneTask = new EditCommand("d1 time 12pm");
	}

	@AfterClass
	public static void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
		GUIModel.eventDoneList = null;
		GUIModel.eventDoneList = null;
	}

}
