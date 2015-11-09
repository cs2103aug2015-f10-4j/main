package command;

import static org.junit.Assert.*;

import java.io.IOException;

import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class EditCommand extends Command {

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
	 * @param editObject2 
	 * @param item2 
	 * @param itemID2 
	 * @throws Exception
	 */
	public EditCommand(String field, Object editObject, String itemID, Item item, boolean isTask) throws Exception {
		this.field = field;
		this.editObject = editObject;
		this.itemID = itemID;
		this.item = item;
		this.isTask = isTask;
	}

	/**
	 * Checks whether the specified date range is valid for an event. If user
	 * tries to make start date after end date, he should be prompted with an
	 * error.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkTimeValidity() throws IllegalStateException {
		if (!isTask && item.getEndDate().compareTo(item.getStartDate()) < 0) {
			if (field.toLowerCase().equals(FIELD_TIME_START)
					|| field.toLowerCase().equals(FIELD_DATE_START)) {
				throw new IllegalStateException(
						"Start Date should be before End Date");
			}
			throw new IllegalStateException(
					"End Date should be after Start Date");
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
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().update(listIndex, prevItem, item);
		Magical.updateDisplayList(listIndex, prevItem, item);
	}

	/**
	 * Set item with float parameters
	 */
	void floatItem() {
		item.setEndDate(null);
		item.setEndTime(-1);
	}
	
	@Override
	public boolean isUndoable() {
		return true;
	}
	
	/**
	 * Clones the current item and changes the value in the field specified. Updates
	 * storage with the new item.
	 * 
	 * @return message to show user
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		duplicateItem();

		switch (field.toLowerCase()) {
		case FIELD_TITLE:
			item.setTitle((String) editObject);
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
			if(isTask){
				Magical.setCurrentTab("tasks");
			} else {
				Magical.setCurrentTab("events");
			}
		}

		return MESSAGE_ITEM_EDITED;
	}
}
