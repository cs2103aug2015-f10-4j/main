package command;

import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class DateCommand extends Command {

	private static final String MESSAGE_DATE_SUCCESS = "Date command successful";

	/** Command parameters **/
	private CustomDate dateStart;
	private CustomDate dateEnd;

	/**
	 * Constructor for DateCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to display the date within the
	 * time range.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DateCommand(CustomDate dateStart, CustomDate dateEnd) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}


	/**
	 * Filter items according to date in the given list and return it
	 * 
	 * @param itemList
	 * @return filtered items to show user
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
	
	@Override
	public boolean isUndoable() {
		return false;
	}
	
	/**
	 * This method executes the date command, which filters through all the
	 * tasks and events and lists only those that fall within the specified date
	 * range.
	 * 
	 * @param None
	 *            .
	 * @return message to show user
	 */
	@Override
	public String execute() {
		ArrayList<Item> taskList = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		ArrayList<Item> taskDoneList = Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX);
		ArrayList<Item> eventList = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		ArrayList<Item> eventDoneList = Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX);

		ArrayList<Item> filteredTaskList = filterItems(taskList);
		ArrayList<Item> filteredTaskDoneList = filterItems(taskDoneList);
		ArrayList<Item> filteredEventList = filterItems(eventList);
		ArrayList<Item> filteredEventDoneList = filterItems(eventDoneList);


		updateView(filteredTaskList, filteredTaskDoneList, filteredEventList,
				filteredEventDoneList);

		return MESSAGE_DATE_SUCCESS;
	}
}
