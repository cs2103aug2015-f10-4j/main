package command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.mdimension.jchronic.Chronic;

import main.CustomDate;
import main.Item;
import main.Magical;
import main.Storage;

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
		Magical.setDisplayList(Storage.TASKS_INDEX, Magical.getStorage()
				.getList(Storage.TASKS_INDEX));
		Magical.setDisplayList(Storage.TASKS_DONE_INDEX, Magical.getStorage()
				.getList(Storage.TASKS_DONE_INDEX));
		Magical.setDisplayList(Storage.EVENTS_INDEX, Magical.getStorage()
				.getList(Storage.EVENTS_INDEX));
		Magical.setDisplayList(Storage.EVENTS_DONE_INDEX, Magical.getStorage()
				.getList(Storage.EVENTS_DONE_INDEX));
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
