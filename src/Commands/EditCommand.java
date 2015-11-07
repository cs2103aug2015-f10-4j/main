package Commands;

import static org.junit.Assert.*;

import java.io.IOException;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class EditCommand extends Command {
	
	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Unknown field";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	private static final String MESSAGE_INVALID_DATE = "Date";
	private static final String MESSAGE_INVALID_TIME_END = "End time";
	private static final String MESSAGE_INVALID_TIME_START = "Start time";
	private static final String MESSAGE_INVALID_ITEM_ID = "taskID";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_ITEM_ERROR = "Unable to edit task.";
	private static final String MESSAGE_ITEM_EDITED = "Item edited.";
	
	/** For checking **/
	private static final String FIELD_TIME_END = "end time";
	private static final String FIELD_TIME_START = "start time";
	private static final String FIELD_DATE = "date";
	private static final String FIELD_TITLE = "title";
	private final CustomDate today = getDate("today");
	
	/** Command parameters **/
	private String field;
	private String value;
	private Item item;
	private Item prevItem;
	private boolean toFloat;
	private boolean isTask;
	private Object editObject;

	/**
	 * Constructor for EditCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public EditCommand(String args) throws Exception {
		super(args);

		args = args + " ";
		
		this.argsArray = splitArgs(args, "(?<!end|start)\\s(?!time|date)", 3);
		
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();

			checkItemExists();

			isTask = item.getType().equals("task");
			
			switch(field.toLowerCase()){
			case FIELD_TITLE:
				checkTitle();
				break;
			case FIELD_DATE:
				checkDate();
				break;
			case FIELD_TIME_START:
				checkTime(0);
				break;
			case FIELD_TIME_END:
				checkTime(1);
				break;
			default:
				invalidField();
			}

			errorInvalidArgs();
			
		} else {
			errorInvalidFormat();
		}
	}

	/**
	 * Adds invalid field message to error messages
	 */
	void invalidField() {
		invalidArgs.add(MESSAGE_INVALID_FIELD);
	}

	/**
	 * Adds error message if time is invalid, according to type, or error message if the 
	 * start time is provided for tasks, or get the correct time if valid.
	 */
	void checkTime(int type) {
		assert(type == 0 || type == 1);
		if (isTask && type == 0) {
			invalidArgs.add(MESSAGE_INVALID_TASK_START);
		} else {
			if ((editObject = getDate(value)) == null) {
				if(type == 0){
					invalidArgs.add(MESSAGE_INVALID_TIME_START);
				} else {
					invalidArgs.add(MESSAGE_INVALID_TIME_END);
				}
			} else {
				editObject = getDate(value).getTime();
			}
		}
	}

	/**
	 * Adds error message if date is invalid or if task is to be made floating, sets
	 * toFloat to true.
	 */
	void checkDate() {
		if (value.equals(STRING_EMPTY) && isTask) {
			toFloat = true;
		} else if ((editObject = getDate(value)) == null) {
			invalidArgs.add(MESSAGE_INVALID_DATE);
		}
	}

	/**
	 * Adds error message if title is invalid
	 */
	void checkTitle() {
		if (getTitle(value) == null) {
			invalidArgs.add(MESSAGE_INVALID_TITLE);
		}
	}

	/**
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
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
		case FIELD_TITLE:
			item.setTitle(value);
			break;
		case FIELD_DATE:
			if (toFloat) {
				floatItem();
			} else {
				unfloatItemForDate();

				CustomDate date = (CustomDate) editObject;
				date.setTime(item.getEndTime());
				
				assertNotNull(date);

				item.setEndDate(date);
			}
			break;
		case FIELD_TIME_START:
			item.setStartTime((int) editObject);
			break;
		case FIELD_TIME_END:
			item.setEndTime((int) editObject);

			CustomDate date = unfloatItemForTime();
			
			assertNotNull(date);
			
			date.setTime(item.getEndTime());
			item.setEndDate(date);
			break;
		default:
			return MESSAGE_ITEM_ERROR;
		}

		try {
			updateItem();
		} catch (IOException e) {
			return MESSAGE_ITEM_ERROR;
		} finally {
			updateView();
		}

		return MESSAGE_ITEM_EDITED;
	}

	/**
	 * Set item with float parameters
	 */
	void floatItem() {
		item.setEndDate(null);
		item.setEndTime(-1);
	}

	/**
	 * Sets the end time of an item to be 2359, the default time, if item 
	 * is floating
	 */
	void unfloatItemForDate() {
		if (item.getEndTime() == -1) {
			item.setEndTime(2359);
		}
	}

	/**
	 * Gives a CustomDate object if the item is floating or a CustomDate object with
	 * the end date of the item
	 * 
	 * @return
	 */
	CustomDate unfloatItemForTime() {
		CustomDate date;
		if (item.getEndDate() == null) {
			date = today;
			// normal changing of date object
		} else {
			date = item.getEndDate();
		}
		return date;
	}

	/**
     * Updates the original item with the new modified item
	 * 
	 * @throws IOException
	 */
	void updateItem() throws IOException {
		int listIndex = Storage.getListIndex(argsArray.get(0));
		Magical.getStorage().update(listIndex, prevItem, item);
	}
	
	/**
	 * Updates the new view in the GUI
	 */
	void updateView() {
		GUIModel.setTaskList(Magical.getStorage().getList(
				Storage.TASKS_INDEX));
		GUIModel.setTaskDoneList(Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX));
		GUIModel.setEventList(Magical.getStorage().getList(
				Storage.EVENTS_INDEX));
		GUIModel.setEventDoneList(Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX));
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
