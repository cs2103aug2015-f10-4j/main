package command;

import java.util.ArrayList;
import java.util.Date;

import gui.GUIModel;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class DateCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: date <start date> to <end date>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End date/time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start date/time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	private static final String MESSAGE_DATE_SUCCESS = "Date command successful";
	
	/** Command parameters **/
	private CustomDate dateStart;
	private CustomDate dateEnd;

	/**
	 * Constructor for DateCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DateCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs("\\sto\\s", -1);

		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();

			checkDateTime(dateStart, 0);

			checkDateTime(dateEnd, 1);

			checkDateRange();

			errorInvalidArgs();
			
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}

	/**
	 * Adds error message if end date is before start date
	 */
	void checkDateRange() {
		if (dateStart != null && dateEnd != null && !validDateRange()) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME_RANGE);
		}
	}

	/**
	 * Adds error message if invalid date and time specified, according to if the date
	 * is the start or end date.
	 */
	private void checkDateTime(CustomDate date, int type) {
		assert(type == 0 || type == 1);
		if (date == null) {
			if(type == 0){
				invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
			} else {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
			}
		}
	}

	void setProperParams() {
		String start = argsArray.get(0).trim();
		String end = count == 2 ? argsArray.get(1).trim() : STRING_EMPTY;

		dateStart = start.equals(STRING_EMPTY) ? new CustomDate(new Date(0))
				: getDate(start);
		dateEnd = end.equals(STRING_EMPTY) ? new CustomDate(new Date(
				Long.MAX_VALUE)) : getDate(end);
	}

	public boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if the end date given is after the start date
	 * @return
	 */
	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) < 0;
	}

	/**
	 * This method executes the date command. Which simply filters through all
	 * the tasks and events and lists only those that fall within the specified
	 * date range.
	 * 
	 * @param None
	 *            .
	 * @return None
	 */
	@Override
	public String execute() {
		
		//Get unfiltered lists
		ArrayList<Item> taskList = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		ArrayList<Item> taskDoneList = Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX);
		ArrayList<Item> eventList = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		ArrayList<Item> eventDoneList = Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX);
		
		//Generated filtered lists
		ArrayList<Item> filteredTaskList = filterItems(taskList);
		ArrayList<Item> filteredTaskDoneList = filterItems(taskDoneList);
		ArrayList<Item> filteredEventList = filterItems(eventList);
		ArrayList<Item> filteredEventDoneList = filterItems(eventDoneList);
		
		updateView(filteredTaskList, filteredTaskDoneList, filteredEventList, filteredEventDoneList);
		
		return MESSAGE_DATE_SUCCESS;
	}

	/**
	 * Filter items according to date in the given list and return it
	 * @param itemList
	 * @return
	 */
	private ArrayList<Item> filterItems(ArrayList<Item> itemList) {
		ArrayList<Item> filteredItemList = new ArrayList<Item>();
		for (Item t : itemList) {
			if (t.getEndDate() != null
					&& t.getEndDate().compareTo(dateStart) >= 0
					&& t.getEndDate().compareTo(dateEnd) <= 0) {
				filteredItemList.add(t);
			}
		}
		return filteredItemList;
	}

	void updateView(ArrayList<Item> filteredTaskList, ArrayList<Item> filteredTaskDoneList,
			ArrayList<Item> filteredEventList, ArrayList<Item> filteredEventDoneList) {
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
