package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class EventCommandTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: ";
	private static final String MESSAGE_INVALID_FORMAT = "Use format: event <title> "
			+ "from <start date> <start time> "
			+ "to <end date> <end time>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_EVENT_ADDED = "event added";
	private static final String MESSAGE_EVENT_CLASH = ". Another event exists on the same date.";
	private static final String MESSAGE_EVENT_ERROR = "unable to add event";


	/** Strings to test with **/
	private static final String EMPTY_STRING = "";
	private static final String WHITESPACE_STRING = "                ";
	private static final String NUMBER_STRING = "1234567890";
	private static final String LONG_STRING = "This is a very long string made out of multiple words that can go on forever and ever and ever and ever";
	private static final String WEIRD_STRING = "T#is str/ng h@s we!rd $ymb^\\s. And punc!tuat!ion?";
	private static final String QUOTES_STRING = "\"This string has 'quotes' of both kinds\"";
	private static final String DATE_STRING = " from January 1 12pm to January 1 1pm";


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
	public void testNormalInputs() throws Exception {
		EventCommand normalEvent = new EventCommand("Event" + DATE_STRING);
		EventCommand normalEventDateFlipped = new EventCommand("Event from 1 Jan 12pm to 1 Jan 1pm");
		EventCommand normalEventWithYear = new EventCommand("Event from January 1 2016 12pm to January 1 2016 1pm");
		EventCommand normalEventToday = new EventCommand("Event from today 12pm to today 1pm");
		EventCommand normalEventLongPeriod = new EventCommand("Event from today 12pm to Jan 1 2115 1pm");
		EventCommand normalEventLongTitle = new EventCommand(LONG_STRING + DATE_STRING);
		EventCommand normalEventWeirdTitle = new EventCommand(WEIRD_STRING + DATE_STRING);
		EventCommand nonExistentTime = new EventCommand("Grad trip with Harry Potter from 17 Dec 11pm to 10 January 12am");
	}

	@Test
	public void testWrongNumInputs() throws Exception {
		try {
			EventCommand noInput = new EventCommand("");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
		try {
			EventCommand justID = new EventCommand("t1");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
		try {
			EventCommand noFrom = new EventCommand("t1 January 1 12pm to January 1 1pm");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
		try {
			EventCommand noTo = new EventCommand("t1 from January 1 12pm January 1 1pm");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
	}

	@Test
	public void testInvalidInputs() throws Exception {
		try {
			EventCommand noTitle = new EventCommand(EMPTY_STRING + DATE_STRING);
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID + MESSAGE_INVALID_TITLE, e.getMessage());
		}
		try {
			EventCommand nonExistentDate = new EventCommand("Event from January 32 12pm to January 32 1pm");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID + MESSAGE_INVALID_DATETIME_START, e.getMessage());
		}
		try {
			EventCommand nonExistentTime = new EventCommand("Event from January 1 25pm to January 1 26pm");
		} catch (Exception e) {
			assertEquals(MESSAGE_HEADER_INVALID + MESSAGE_INVALID_DATETIME_START, e.getMessage());
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
