package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class AddCommand extends Command {

	/** Messaging */
	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time>";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_INVALID_DATETIME = "Date/Time";
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";
	
	/** Command parameters **/
	private String title;
	private CustomDate dueDate;
	private int endTime;
	private boolean isFloat;
	private Item task;

	/**
	 * Constructor for AddCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public AddCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs("\\sby\\s", 2);
		removeEscapeCharacters();

		this.count = argsArray.size();

		splitArgsAfterDateTime();

		this.count = argsArray.size();
		this.isFloat = false;
		
		for (int i = 0; i < count; i++) {
			assertNotNull(argsArray.get(i));
		}

		if (validNumArgs()) {

			if (count == 1) {
				setFloatParams();
			} else {
				setProperParams();
			}

			checkTitle();

			checkDateTime();

			errorInvalidArgs();
			
		} else {
			errorInvalidFormat();
		}
	}

	/**
	 * Throws exception if error messages for format are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
	}

	/**
	 * Throws exception if error messages for invalid arguments are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidArgs() throws IllegalArgumentException {
		if (invalidArgs.size() > 0) {
			throw new IllegalArgumentException(String.format(MESSAGE_HEADER_INVALID, invalidArgs));
		}
	}

	/**
	 * Adds error message if invalid date and time specified, and task to
	 * be added is not a floating task
	 */
	private void checkDateTime() {
		if (this.dueDate == null && !isFloat) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME);
		}
	}

	/**
	 * Adds error message if title is invalid
	 */
	private void checkTitle() {
		if (title == null) {
			invalidArgs.add(MESSAGE_INVALID_TITLE);
		}
	}

	/**
	 * Set the relevant parameters of AddCommand to that of the specified task
	 */
	private void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dueDate = getDate(argsArray.get(1).trim());
		this.endTime = dueDate == null ? -1 : dueDate.getTime();
		assertFalse(isFloat);
	}

	/**
	 * Set the relevant parameters of AddCommand to that of a floating task
	 */
	private void setFloatParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dueDate = null;
		this.endTime = -1;
		this.isFloat = true;
	}

	/**
	 * Date/time argument might be concatenated with other arguments, thus
	 * the method splits the arguments properly
	 */
	private void splitArgsAfterDateTime() {
		if (argsArray.size() > 1 && argsArray.get(count - 1).contains(" ")) {
			while (true) {
				String last = getLastWord(argsArray.get(count - 1));
				if (getDate(last) != null) {
					break;
				} else {
					splitOnce(last);
				}
			}
		}
	}

	/**
	 * Adds last word to the argsArray and removes it from the date/time argument
	 * 
	 * @param last
	 */
	private void splitOnce(String last) {
		argsArray.add(count, last);
		argsArray.set(count - 1, removeLastWord(argsArray.get(count - 1)));
	}

	/**
	 * Removes last word from a string
	 * 
	 * @param string
	 * @return String with last word removed
	 */
	private String removeLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[0];
	}

	/**
	 * Gives last word of a string
	 * 
	 * @param string
	 * @return String last word
	 */
	private String getLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[1];
	}

	/**
	 * Replaces characters that were used for escaping the keyword argument
	 * that was used for splitting
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(i,
						  argsArray.get(i).trim().replaceAll("(?<=by)\"|\"(?=by)", 
															 STRING_EMPTY));
		}
	}

	/**
	 * Checks if the task to be added clashes with another task and adds to the return
	 * message to inform the user
	 */
	private void checkTaskClash() {
		if (isClashing()) {
			returnMsg += MESSAGE_TASK_CLASH;
		}
	}

	/**
     * Stores the created Item Object as task
	 * 
	 * @throws IOException
	 */
	private void storeTask() throws IOException {
		Magical.getStorage().create(Storage.TASKS_INDEX, task);
	}

	/**
	 * Updates the new view in the GUI
	 */
	private void updateView() {
		GUIModel.setTaskList(Magical.getStorage().getList(
				Storage.TASKS_INDEX));
		GUIModel.setTaskDoneList(Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX));
		GUIModel.setCurrentTab("tasks");
	}

	/**
	 * Create an Item object with the correct argument parameters for a task
	 */
	private void setTaskParams() {
		task = new Item();
		task.setType("task");
		task.setTitle(title);
		task.setStartDate(null);
		task.setStartTime(-1);
		task.setEndDate(dueDate);
		task.setEndTime(endTime);
	}

	/**
	 * Checks if the current task to be added clashes with another task
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
	 * Checks if a given task has end date and is the same as the the created task
	 * @param t
	 * @return
	 */
	private boolean isSameEndDate(Item t) {
		return t.getEndDate() != null
				&& t.getEndDate().equals(task.getEndDate());
	}

	/**
	 * get task list from storage
	 * @return
	 */
	private ArrayList<Item> getTasks() {
		ArrayList<Item> tasks = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		return tasks;
	}

	protected boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	public String execute() {
		setTaskParams();

		try {
			returnMsg = MESSAGE_TASK_ADDED;
			checkTaskClash();
			storeTask();
			return returnMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
		} finally {
			updateView();
		}
	}
	
	public boolean isUndoable() {
		return true;
	}
}