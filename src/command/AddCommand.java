package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class AddCommand extends Command {

	/** Messaging */
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";

	/** Command parameters **/
	private Item task;

	/**
	 * Constructor for AddCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to add a task to storage.
	 * 
	 * @param args
	 * @param endTime 
	 * @param dueDate 
	 * @throws Exception
	 */
	public AddCommand(String title, CustomDate dueDate, int endTime) throws Exception {
		task = new Item();
		task.setType("task");
		task.setTitle(title);
		task.setStartDate(null);
		task.setStartTime(-1);
		task.setEndDate(dueDate);
		task.setEndTime(endTime);
	}

	/**
	 * Checks if the task to be added clashes with another task and adds to the
	 * return message to inform the user
	 */
	private void checkTaskClash() {
		if (isClashing()) {
			returnMsg += MESSAGE_TASK_CLASH;
		}
	}

	/**
	 * Checks if the current task to be added clashes with another task
	 * 
	 * @return
	 */
	private boolean isClashing() {
		ArrayList<Item> tasks = getTasks();
		for (Item t : tasks) {
			if (isSameEndDate(t)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * get task list from storage
	 * 
	 * @return
	 */
	private ArrayList<Item> getTasks() {
		ArrayList<Item> tasks = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		return tasks;
	}

	/**
	 * Checks if a given task has end date and is the same as the the created
	 * task
	 * 
	 * @param t
	 * @return
	 */
	private boolean isSameEndDate(Item t) {
		return t.getEndDate() != null
				&& t.getEndDate().equals(task.getEndDate());
	}

	/**
	 * Stores the created Item Object as task
	 * 
	 * @throws IOException
	 */
	private void storeTask() throws IOException {
		Magical.getStorage().create(Storage.TASKS_INDEX, task);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
	
	/**
	 * Adds a new task to the storage using the parameters stored
	 * 
	 * @return message to show user
	 */
	public String execute() {

		try {
			returnMsg = MESSAGE_TASK_ADDED;
			checkTaskClash();
			storeTask();
			return returnMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
		} finally {
			updateView();
			Magical.setCurrentTab("tasks");
		}
	}
}
