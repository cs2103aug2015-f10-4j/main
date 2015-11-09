package command;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import main.Magical;
import main.Storage;
import main.Item;

public class SearchCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_SEARCH_SUCCESS = "search results for: [ %s ]";
	/** Command parameters **/
	private String query;

	/**
	 * Constructor for SearchCommand objects. Stores the correct arguments
	 * properly. Contains methods to display items containing query
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SearchCommand(String query) throws Exception {
		assertNotNull(query);
		this.query = query.toLowerCase();
	}

	/**
	 * This method executes the search command. Which searches for the specified
	 * query text in the titles of all events and tasks. It then displays the
	 * found tasks and events to the GUI.
	 * 
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

		ArrayList<Item> filteredTaskList = filterList(taskList);
		ArrayList<Item> filteredTaskDoneList = filterList(taskDoneList);
		ArrayList<Item> filteredEventList = filterList(eventList);
		ArrayList<Item> filteredEventDoneList = filterList(eventDoneList);

		updateView(filteredTaskList, filteredTaskDoneList, filteredEventList,
				filteredEventDoneList);

		return String.format(MESSAGE_SEARCH_SUCCESS, query);
	}

	/**
	 * Filter items according to query in the given list and return it
	 * 
	 * @param itemList
	 * @return filtered list to show user
	 */
	private ArrayList<Item> filterList(ArrayList<Item> itemList) {
		ArrayList<Item> filteredItemList = new ArrayList<Item>();
		for (Item i : itemList) {
			if (i.getTitle().contains(query)) {
				filteredItemList.add(i);
			}
		}
		return filteredItemList;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
