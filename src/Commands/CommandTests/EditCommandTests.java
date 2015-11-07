package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
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

	@Before
	public void setUp() {
		ArrayList<Item> testList = new ArrayList<Item>();
		for (int i = 0; i < 10; i ++) {
			testList.add(new Item());
		}
		GUIModel.setTaskList(testList);
		GUIModel.setTaskDoneList(testList);
		GUIModel.setEventList(testList);
		GUIModel.setEventDoneList(testList);
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

	public void testEditDateNormalInputs() throws Exception {
		Command normalEdit = new EditCommand("t1 date November 7 2015");
		Command normalEditPastDate = new EditCommand("t1 date January 1 1915");
	}

	@After
	public void tearDown() {
		GUIModel.taskList = null;
		GUIModel.taskDoneList = null;
		GUIModel.eventList = null;
		GUIModel.eventDoneList = null;
	}

}
