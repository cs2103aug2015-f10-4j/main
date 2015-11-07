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

	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Field: %s\n";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";

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
		Command normalEdit = new EditCommand("t1 date today");
	}

	@AfterClass
	public static void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
		GUIModel.eventDoneList = null;
		GUIModel.eventDoneList = null;
	}

}
