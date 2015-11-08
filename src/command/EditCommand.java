package command;

import static org.junit.Assert.*;

import java.io.IOException;

import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class EditCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use format: edit <item_id> <field> <value>";
	private static final String MESSAGE_INVALID_FIELD = "Unknown field";
	private static final String MESSAGE_INVALID_TASK_START_TIME = "Task cannot have start time";
	private static final String MESSAGE_INVALID_TASK_START_DATE = "Task cannot have start date";
	private static final String MESSAGE_INVALID_DATE_START = "Start date";
	private static final String MESSAGE_INVALID_DATE_END = "End date";
	private static final String MESSAGE_INVALID_TIME_START = "Start time";
	private static final String MESSAGE_INVALID_TIME_END = "End time";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_ITEM_ERROR = "Unable to edit item %s";
	private static final String MESSAGE_ITEM_EDITED = "Item edited.";

	/** For checking **/
	private static final String FIELD_TIME_END = "end time";
	private static final String FIELD_TIME_START = "start time";
	private static final String FIELD_TIME = "time";
	private static final String FIELD_DATE_START = "start date";
	private static final String FIELD_DATE_END = "end date";
	private static final String FIELD_DATE = "date";
	private static final String FIELD_TITLE = "title";

	/** Command parameters **/
	private String field;
	private String value;
	private String itemID;
	private Item item;
	private Item prevItem;
	private boolean toFloat;
	private boolean isTask;
	private Object editObject;

	/**
	 * Constructor for EditCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to update the item in storage.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public EditCommand(String args) throws Exception {
		super(args);

		// For empty sting value when only 2 args are provided
		args = args + " ";

		this.argsArray = splitArgs("(?<!end|start)\\s", 3);

		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemExists();
			errorInvalidArgs();
			isTask = item.getType().equals("task");

			switch (field.toLowerCase()) {
			case FIELD_TITLE:
				checkTitle();
				break;
			case FIELD_DATE_START:
				checkDate(0);
				break;
			case FIELD_DATE_END:
				checkDate(1);
				break;
			case FIELD_DATE:
				checkDate(1);
				break;
			case FIELD_TIME_START:
				checkTime(0);
				break;
			case FIELD_TIME_END:
				checkTime(1);
				break;
			case FIELD_TIME:
				checkTime(1);
				break;
			default:
				invalidField();
			}
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}

	/**
	 * Adds invalid field message to error messages
	 */
	void invalidField() {
		invalidArgs.add(MESSAGE_INVALID_FIELD);
	}

	/**
	 * Adds error message if time is invalid, according to type, or error
	 * message if the start time is provided for tasks, or get the correct time
	 * if valid.
	 */
	void checkTime(int type) {
		assert (type == 0 || type == 1);
		if (isTask && type == 0) {
			invalidArgs.add(MESSAGE_INVALID_TASK_START_TIME);
		} else {
			if ((editObject = getDate(value)) == null) {
				if (type == 0) {
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
	 * Adds error message if date is invalid or if task is to be made floating,
	 * sets toFloat to true.
	 */
	void checkDate(int type) {
		assert (type == 0 || type == 1);
		if (isTask && type == 0) {
			invalidArgs.add(MESSAGE_INVALID_TASK_START_DATE);
		} else {
			if (value.equals(STRING_EMPTY) && isTask) {
				toFloat = true;
			} else if ((editObject = getDate(value)) == null) {
				if (type == 0) {
					invalidArgs.add(MESSAGE_INVALID_DATE_START);
				} else {
					invalidArgs.add(MESSAGE_INVALID_DATE_END);
				}

			}
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
	 * Set the relevant parameters of EditCommand to that of the specified task
	 */
	void setProperParams() {
		this.itemID = argsArray.get(0).trim();
		this.item = getItemByID(itemID);
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
	/**
	 * Clones the current item and changes the value in the field specified. Updates
	 * storage with the new item.
	 */
	public String execute() throws Exception {

		duplicateItem();

		switch (field.toLowerCase()) {
		case FIELD_TITLE:
			item.setTitle(value);
			break;
		case FIELD_DATE_START:
			if (toFloat) {
				floatItem();
			} else {
				unfloatItemForDate();
				CustomDate date = (CustomDate) editObject;
				date.setTime(item.getEndTime());
				assertNotNull(date);
				item.setStartDate(date);
			}
			break;
		case FIELD_DATE_END:
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
			CustomDate date = unfloatItemForTime();
			assertNotNull(date);
			date.setTime(item.getStartTime());
			item.setStartDate(date);
			break;
		case FIELD_TIME_END:
			item.setEndTime((int) editObject);
			date = unfloatItemForTime();
			assertNotNull(date);
			date.setTime(item.getEndTime());
			item.setEndDate(date);
			break;
		case FIELD_TIME:
			item.setEndTime((int) editObject);
			date = unfloatItemForTime();
			assertNotNull(date);
			date.setTime(item.getEndTime());
			item.setEndDate(date);
			break;
		default:
			return MESSAGE_ITEM_ERROR;
		}

		try {
			checkTimeValidity();
			updateItem();
		} catch (IOException e) {
			throw new Exception(String.format(MESSAGE_ITEM_ERROR, itemID));
		} finally {
			updateView();
		}

		return MESSAGE_ITEM_EDITED;
	}

	/**
	 * Checks whether the specified date range is valid for an event. If user tries to make
	 * start date after end date, he should be prompted with an error.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkTimeValidity() throws IllegalStateException {
		if (!isTask && item.getEndDate().compareTo(item.getStartDate()) < 0) {
			if (field.toLowerCase().equals(FIELD_TIME_START) || field.toLowerCase().equals(FIELD_DATE_START)) {
				throw new IllegalStateException("Start Date should be before End Date");
			}
			throw new IllegalStateException("End Date should be after Start Date");
		}
	}

	/**
	 * Make 2 copies of the item to be stored in prevItem and item
	 */
	void duplicateItem() {
		prevItem = item;
		item = prevItem.copy();
	}

	/**
	 * Set item with float parameters
	 */
	void floatItem() {
		item.setEndDate(null);
		item.setEndTime(-1);
	}

	/**
	 * Sets the end time of an item to be 2359, the default time, if item is
	 * floating
	 */
	void unfloatItemForDate() {
		if (item.getEndTime() == -1) {
			item.setEndTime(2359);
		}
	}

	/**
	 * Gives a CustomDate object if the item is floating or a CustomDate object
	 * with the end date of the item
	 * 
	 * @return
	 */
	CustomDate unfloatItemForTime() {
		CustomDate date;
		if (item.getEndDate() == null) {
			date = today;
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
		GUIModel.setTaskList(Magical.getStorage().getList(Storage.TASKS_INDEX));
		GUIModel.setTaskDoneList(Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX));
		GUIModel.setEventList(Magical.getStorage()
				.getList(Storage.EVENTS_INDEX));
		GUIModel.setEventDoneList(Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX));
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
