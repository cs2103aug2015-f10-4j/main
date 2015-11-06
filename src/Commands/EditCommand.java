package Commands;

import static org.junit.Assert.*;

import java.io.IOException;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class EditCommand extends Command {

	private String field;
	private String value;
	private Item item;
	private Item prevItem;
	private boolean toFloat;
	private boolean isTask;
	private Object editObject;

	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Field: %s\n";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	
	public EditCommand(String args) throws Exception {
		super(args);

		args = args + " ";
		
		this.argsArray = splitArgs(args, "(?<!end|start)\\s(?!time|date)", 3);
		
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();

			checkTaskExists();
//			errorInvalidArgs();

			isTask = item.getType().equals("task");
			
			if (field.equalsIgnoreCase("title")) {
				if (getTitle(value) == null) {
					invalidArgs.add("title");
				}
			} else if (field.equalsIgnoreCase("date")) {
				if (value.equals(STRING_EMPTY) && isTask) {
					toFloat = true;
				} else if ((editObject = getDate(value)) == null) {
					invalidArgs.add("date");
				}
			} else if (field.equalsIgnoreCase("start time")) {
				if (isTask) {
					invalidArgs.add(MESSAGE_INVALID_TASK_START);
				} else {
					if ((editObject = getDate(value)) == null) {
						invalidArgs.add("start time");
					} else {
						editObject = getDate(value).getTime();
					}
				}

			} else if (field.equalsIgnoreCase("end time")) {
				if ((editObject = getDate(value)) == null) {
					invalidArgs.add("end time");
				} else {
					editObject = getDate(value).getTime();
				}
			} else {
				invalidArgs.add(MESSAGE_INVALID_FIELD);
			}

			errorInvalidArgs();
			
		} else {
			errorInvalidFormat();
		}
	}

	/**
	 * Adds error message if task does not exist or unable to get
	 */
	void checkTaskExists() {
		if (item == null) {
			invalidArgs.add("taskID");
		}
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
	 * Throws exception if error messages for format are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
	}
	
	/**
	 * Set the relevant parameters of AddCommand to that of the specified task
	 */
	private void setProperParams() {
		this.item = getItemByID(argsArray.get(0).trim());
		this.field = argsArray.get(1).trim();
		this.value = argsArray.get(2).trim();
	}

	public boolean validNumArgs() {
		if (this.count != 3) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String execute() {
		prevItem = item;
		item = prevItem.copy();

		switch (field.toLowerCase()) {
		case "title":
			item.setTitle(value);
			break;
		case "date":
			if (toFloat) {
				// change to float
				item.setEndDate(null);
				item.setEndTime(-1);
			} else {
				// unfloating the task
				if (item.getEndTime() == -1) {
					item.setEndTime(0000);
				}

				CustomDate date = (CustomDate) editObject;
				date.setTime(item.getEndTime());

				item.setEndDate(date);
			}
			break;
		case "start time":
			item.setStartTime((int) editObject);
			break;
		case "end time":
			item.setEndTime((int) editObject);

			CustomDate date = null;
			// unfloating the task
			if (item.getEndDate() == null) {
				date = getDate("today");
				// normal changing of date object
			} else {
				date = item.getEndDate();
			}
			assertNotNull(date);
			date.setTime(item.getEndTime());
			item.setEndDate(date);
			break;
		default:
			return "Unable to edit task.";
		}

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.getStorage().update(listIndex, prevItem, item);
		} catch (IOException e) {
			return "unable to edit task";
		} finally {
			GUIModel.setTaskList(Magical.getStorage().getList(
					Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.getStorage().getList(
					Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.getStorage().getList(
					Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.getStorage().getList(
					Storage.EVENTS_DONE_INDEX));
		}

		return "Task edited.";
	}

	public static void main(String[] args) throws Exception {
		// EditCommand e = new EditCommand("t1 end time asfas");
		// EditCommand e1 = new EditCommand("t1 start time asfas");
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
