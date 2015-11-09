package parser;

import static org.junit.Assert.*;

/**
 * @@author A0127127L
 */

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gui.GUIModel;
import main.Item;

public class EventParserTest {

	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: ";
	private static final String MESSAGE_INVALID_FORMAT = "Use format: event <title> "
			+ "from <start date> <start time> "
			+ "to <end date> <end time>";
	private static final String MESSAGE_INVALID_DATETIME_END = "[End date/time]";
	private static final String MESSAGE_INVALID_DATETIME_START = "[Start date/time]";
	private static final String MESSAGE_INVALID_DATETIME_START_END = "[End date/time, Start date/time]";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "[End date/time is earlier than Start date/time]";
	private static final String MESSAGE_INVALID_TITLE = "[Title]";
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
		EventParser normalEvent = new EventParser("Event" + DATE_STRING);
		EventParser normalEvent2 = new EventParser("Event from today to tomorrow");
		EventParser normalEventDateFlipped = new EventParser("Event from 1 Jan 12pm to 1 Jan 1pm");
		EventParser normalEventWithYear = new EventParser("Event from January 1 2016 12pm to January 1 2016 1pm");
		EventParser normalEventToday = new EventParser("Event from today 12pm to today 1pm");
		EventParser normalEventLongPeriod = new EventParser("Event from today 12pm to Jan 1 2115 1pm");
		EventParser normalEventLongTitle = new EventParser(LONG_STRING + DATE_STRING);
		EventParser normalEventWeirdTitle = new EventParser(WEIRD_STRING + DATE_STRING);
		EventParser normalEventPastYear = new EventParser("Grad trip with Harry Potter from 17 Dec 11pm to 10 January 12am");
	}

	@Test
	public void testWrongNumInputs() throws Exception {
		try {
			EventParser noInput = new EventParser("");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
		try {
			EventParser justID = new EventParser("t1");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
		try {
			EventParser noFrom = new EventParser("t1 January 1 12pm to January 1 1pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
		try {
			EventParser noTo = new EventParser("t1 from January 1 12pm January 1 1pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_INVALID_FORMAT);
		}
	}

	@Test
	public void testInvalidInputs() throws Exception {
		try {
			EventParser noTitle = new EventParser(EMPTY_STRING + DATE_STRING);
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_HEADER_INVALID + MESSAGE_INVALID_TITLE);
		}
		try {
			EventParser nonExistentDate = new EventParser("Event from January 32 12pm to January 32 1pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_HEADER_INVALID + MESSAGE_INVALID_DATETIME_START_END);
		}
		try {
			EventParser nonExistentTime = new EventParser("Event from January 1 25pm to January 1 26pm");
		} catch (Exception e) {
			assertEquals(e.getMessage(), MESSAGE_HEADER_INVALID + MESSAGE_INVALID_DATETIME_START_END);
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
