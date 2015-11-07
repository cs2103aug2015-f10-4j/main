package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Commands.EventCommand;
import gui.GUIModel;
import main.Item;

public class EventCommandTests {

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
		String fromWhentoWhen = " from January 1 2015 12pm to January 1 2015 1pm";
		EventCommand normalEvent = new EventCommand("Event" + fromWhentoWhen);
		EventCommand normalEventToday = new EventCommand("Event from today 12pm to today 1pm");
		EventCommand normalEventLongTitle = new EventCommand(LONG_STRING + fromWhentoWhen);
		EventCommand normalEventWeirdTitle = new EventCommand(WEIRD_STRING + fromWhentoWhen);
	}

	@AfterClass
	public static void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
		GUIModel.eventDoneList = null;
		GUIModel.eventDoneList = null;
	}

}
