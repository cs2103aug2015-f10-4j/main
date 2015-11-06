package Commands;

import java.io.IOException;
import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class AddCommand extends Command {

	/** Command parameters **/
	protected String title;
	protected CustomDate dueDate;
	protected int endTime;
	protected boolean isFloat;
	private Item item;

	/** Messaging */
	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time>";
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";

	/**
	 * Constructor for AddCommand objects
	 * Description: Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public AddCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(args, "\\s+by\\s+", 2);
		removeEscapeCharacters();

		this.count = argsArray.size();

		splitArgsAfterDateTime();

		this.count = argsArray.size();
		this.isFloat = false;

		if (validNumArgs()) {

			this.title = getTitle(argsArray.get(0).trim());
			
			if (count == 1) {
				setFloatParams();
			} else {
				setProperParams();
			}

			checkTitle();

			checkDueDate();

			errorInvalidArgs();
			
		} else {
			errorInvalidFormat();
		}
	}

	private void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
	}

	private void errorInvalidArgs() throws IllegalArgumentException {
		if (invalidArgs.size() > 0) {
			throw new IllegalArgumentException(String.format(MESSAGE_HEADER_INVALID, invalidArgs));
		}
	}

	private void checkDueDate() {
		if (this.dueDate == null && !isFloat) {
			invalidArgs.add("Date");
			invalidArgs.add("Time");
		}
	}

	private void checkTitle() {
		if (title == null) {
			invalidArgs.add("Title");
		}
	}

	private void setProperParams() {
		this.dueDate = getDate(argsArray.get(1).trim());
		this.endTime = dueDate == null ? -1 : dueDate.getTime();
	}

	private void setFloatParams() {
		this.dueDate = null;
		this.endTime = -1;
		this.isFloat = true;
	}

	private void splitArgsAfterDateTime() {
		if (argsArray.size() > 1 && argsArray.get(count - 1).contains(" ")) {
			while (true) {
				String last = getLastUncheckedWord();
				if (getDate(last) != null) {
					break;
				} else {
					argsArray.add(count, last);
					removeCheckedWord();
				}
			}
		}
	}

	private void removeCheckedWord() {
		argsArray.set(count - 1, argsArray.get(count - 1).split("\\s(?=\\S+$)")[0]);
	}

	/**
	 * Method: getLastUncheckedWord
	 * Description: 
	 * 
	 * @return
	 */
	private String getLastUncheckedWord() {
		return argsArray.get(count - 1).split("\\s(?=\\S+$)")[1];
	}

	/**
	 * Method: removeEscapeCharacters
	 * Description: Replaces characters that were used for escaping the keyword argument
	 * were split about
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(
					i,
					argsArray.get(i).trim()
							.replaceAll("(?<=by)\"|\"(?=by)", STRING_EMPTY));
		}
	}

	/**
	 * Method validNumArgs
	 * Description: Checks if the number of arguments provided is correct
	 * 
	 * @return boolean true/false
	 */
	public boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String execute() {
		item = new Item();
		item.setType("task");
		item.setTitle(title);
		item.setStartDate(null);
		item.setStartTime(-1);
		item.setEndDate(dueDate);
		item.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_TASK_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_TASK_CLASH;
			}
			Magical.getStorage().create(Storage.TASKS_INDEX, item);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
		} finally {
			GUIModel.setTaskList(Magical.getStorage().getList(
					Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.getStorage().getList(
					Storage.TASKS_DONE_INDEX));
			GUIModel.setCurrentTab("tasks");
		}
	}

	private boolean isClashing() {
		ArrayList<Item> tasks = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		for (Item t : tasks) {
			if (t.getEndDate() != null
					&& t.getEndDate().equals(item.getEndDate())) {
				return true;
			}
		}
		return false;
	}
}
