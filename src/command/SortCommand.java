package command;

import java.util.ArrayList;
import java.util.Collections;

import main.Item;
import main.Magical;
import main.Storage;

/**
 * @@author A0131729E
 */
public class SortCommand extends Command {

	private static final String MESSAGE_SORT_SUCCESS = "sort successful";

	/** Command parameters **/
	private ArrayList<String> sortParams;

	/**
	 * Constructor for SortCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to sort the displayed tasks.
	 * 
	 * @param sortParams
	 * @throws Exception
	 */
	public SortCommand(ArrayList<String> sortParams) throws Exception {
		this.sortParams = sortParams;
	}

	/**
	 * This method executes the sort command. Which sorts all the tasks and
	 * events currently displayed by the GUI. Sorting is done using the
	 * parameter specified.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {

		ArrayList<Item> sortedTaskList = new ArrayList<Item>(
				Magical.getDisplayList(Storage.TASKS_INDEX));
		ArrayList<Item> sortedTaskDoneList = new ArrayList<Item>(
				Magical.getDisplayList(Storage.TASKS_DONE_INDEX));
		ArrayList<Item> sortedEventList = new ArrayList<Item>(
				Magical.getDisplayList(Storage.EVENTS_INDEX));
		ArrayList<Item> sortedEventDoneList = new ArrayList<Item>(
				Magical.getDisplayList(Storage.EVENTS_DONE_INDEX));

		if (sortParams.contains("title")) {
			Collections.sort(sortedTaskList, Item.Comparators.TITLE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.TITLE);
			Collections.sort(sortedEventList, Item.Comparators.TITLE);
			Collections.sort(sortedEventDoneList, Item.Comparators.TITLE);
		}
		if (sortParams.contains("date")) {
			Collections.sort(sortedTaskList, Item.Comparators.DATE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.DATE);
			Collections.sort(sortedEventList, Item.Comparators.DATE);
			Collections.sort(sortedEventDoneList, Item.Comparators.DATE);
		}
		if (sortParams.contains("priority")) {
			Collections.sort(sortedTaskList, Item.Comparators.PRIORITY);
			Collections.sort(sortedTaskDoneList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventDoneList, Item.Comparators.PRIORITY);
		}

		updateView(sortedTaskList, sortedTaskDoneList, sortedEventList,
				sortedEventDoneList);

		return MESSAGE_SORT_SUCCESS;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
