package parser;

import command.Command;
import command.EditCommand;
import main.Item;

/**
 * @@author A0129654X
 */
public class EditParser extends ArgsParserAbstract {
	
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

	/** For checking **/
	private static final String FIELD_TIME_END = "end time";
	private static final String FIELD_TIME_START = "start time";
	private static final String FIELD_TIME = "time";
	private static final String FIELD_DATE_START = "start date";
	private static final String FIELD_DATE_END = "end date";
	private static final String FIELD_DATE = "date";
	private static final String FIELD_TITLE = "title";
	private static final int START = 0;
	private static final int END = 1;

	/** Command parameters **/
	private String field;
	private String value;
	private String itemID;
	private Item item;
	private boolean isTask;
	private Object editObject;

	/**
	 * Constructor for EditParser objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to update the item in storage.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public EditParser(String args) throws Exception {
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
				checkDate(START);
				break;
			case FIELD_DATE_END:
				checkDate(END);
				break;
			case FIELD_DATE:
				checkDate(END);
				break;
			case FIELD_TIME_START:
				checkTime(START);
				break;
			case FIELD_TIME_END:
				checkTime(END);
				break;
			case FIELD_TIME:
				checkTime(END);
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
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		}
	}
	
	/**
	 * Adds error message if title is invalid
	 */
	void checkTitle() {
		if ((editObject = getTitle(value)) == null) {
			invalidArgs.add(MESSAGE_INVALID_TITLE);
		}
	}
	
	/**
	 * Adds error message if date is invalid or if task is to be made floating,
	 * sets toFloat to true.
	 */
	void checkDate(int type) {
		assert (type == START || type == END);
		if (isTask && type == START) {
			invalidArgs.add(MESSAGE_INVALID_TASK_START_DATE);
		} else {
			if (value.equals(STRING_EMPTY) && isTask) {
			} else if ((editObject = getDate(value)) == null) {
				addInvalidDateType(type);
			}
		}
	}

	/**
	 * Adds the proper invalid date message to error message
	 * @param type
	 */
	void addInvalidDateType(int type) {
		if (type == START) {
			invalidArgs.add(MESSAGE_INVALID_DATE_START);
		} else {
			invalidArgs.add(MESSAGE_INVALID_DATE_END);
		}
	}
	
	/**
	 * Adds error message if time is invalid, according to type, or error
	 * message if the start time is provided for tasks, or get the correct time
	 * if valid.
	 */
	void checkTime(int type) {
		assert (type == START || type == END);
		if (isTask && type == START) {
			invalidArgs.add(MESSAGE_INVALID_TASK_START_TIME);
		} else {
			if ((editObject = getDate(value)) == null) {
				addInvalidTimeType(type);
			} else {
				editObject = getDate(value).getTime();
			}
		}
	}

	/**
	 * Adds the proper invalid time message to error message
	 * @param type
	 */
	void addInvalidTimeType(int type) {
		if (type == START) {
			invalidArgs.add(MESSAGE_INVALID_TIME_START);
		} else {
			invalidArgs.add(MESSAGE_INVALID_TIME_END);
		}
	}
	
	/**
	 * Adds invalid field message to error messages
	 */
	void invalidField() {
		invalidArgs.add(MESSAGE_INVALID_FIELD);
	}
	
	/**
	 * Set the relevant parameters of EditCommand to that of the specified task
	 */
	@Override
	void setProperParams() {
		this.itemID = argsArray.get(0).trim();
		this.item = getItemByID(itemID);
		this.field = argsArray.get(1).trim();
		this.value = argsArray.get(2).trim();
	}

	@Override
	boolean validNumArgs() {
		if (this.count != 3) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command edit = new EditCommand(field, editObject, itemID, item, isTask);
		return edit;
	}
}
