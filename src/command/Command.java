package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.mdimension.jchronic.Chronic;

import main.CustomDate;
import main.Item;
import main.Magical;
import main.Storage;

/**
 * @@author A0131729E
 */
public abstract class Command {

	/** Messaging **/
	protected String returnMsg = "";

	/** For checking **/
	protected static final CustomDate today = new CustomDate(Chronic
			.parse("today 00:00").getEndCalendar().getTime());
	
	/**
	 * Constructor for Command objects. Stores the arguments passed in when the
	 * constructor is called.
	 * 
	 * @param args
	 */
	public Command() {

	}

	/**
	 * Updates the new view in the GUI
	 */
	void updateView() {
		ArrayList<Item> updatedTaskList = Magical
				.getDisplayList(Storage.TASKS_INDEX);
		ArrayList<Item> updatedTaskDoneList = Magical
				.getDisplayList(Storage.TASKS_DONE_INDEX);
		ArrayList<Item> updatedEventList = Magical
				.getDisplayList(Storage.EVENTS_INDEX);
		ArrayList<Item> updatedEventDoneList = Magical
				.getDisplayList(Storage.EVENTS_DONE_INDEX);
		Magical.setDisplayList(Storage.TASKS_INDEX, updatedTaskList);
		Magical.setDisplayList(Storage.TASKS_DONE_INDEX, updatedTaskDoneList);
		Magical.setDisplayList(Storage.EVENTS_INDEX, updatedEventList);
		Magical.setDisplayList(Storage.EVENTS_DONE_INDEX, updatedEventDoneList);
	}

	/**
	 * Updates the new view in the GUI
	 */
	void updateViewStorage() {
		ArrayList<Item> updatedTaskList = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		ArrayList<Item> updatedTaskDoneList = Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX);
		ArrayList<Item> updatedEventList = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		ArrayList<Item> updatedEventDoneList = Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX);
		Magical.setDisplayList(Storage.TASKS_INDEX, updatedTaskList);
		Magical.setDisplayList(Storage.TASKS_DONE_INDEX, updatedTaskDoneList);
		Magical.setDisplayList(Storage.EVENTS_INDEX, updatedEventList);
		Magical.setDisplayList(Storage.EVENTS_DONE_INDEX, updatedEventDoneList);
	}

	/**
	 * Updates the new view in the GUI
	 */
	void updateView(ArrayList<Item> newTaskList,
			ArrayList<Item> newTaskDoneList, ArrayList<Item> newEventList,
			ArrayList<Item> newEventDoneList) {

		Magical.setDisplayList(Storage.TASKS_INDEX, newTaskList);
		Magical.setDisplayList(Storage.TASKS_DONE_INDEX, newTaskDoneList);
		Magical.setDisplayList(Storage.EVENTS_INDEX, newEventList);
		Magical.setDisplayList(Storage.EVENTS_DONE_INDEX, newEventDoneList);
	}
	
	/**
	 * Indicates if the command can be undone or not
	 * 
	 * @return boolean true/false
	 */
	public abstract boolean isUndoable();
	
	/**
	 * Implements functionality for each Command subclass.
	 * 
	 * @return String success/failure
	 * @throws Exception
	 */
	public abstract String execute() throws Exception;
}
