package command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class ShowCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_SHOW_RESULTS = "Show results for: %s";

	/** Command parameters **/
	private ArrayList<String> tags;
	private String type;

	/**
	 * Constructor for ShowCommand objects. Sets the command parameters with the
	 * proper inputs. Contains methods to display items that are tasks, events,
	 * containing specified tags, or all items
	 * 
	 * @param args
	 * @param tags2
	 * @throws Exception
	 */
	public ShowCommand(String type, ArrayList<String> tags) throws Exception {
		this.type = type;
		this.tags = tags;
	}

	/**
	 * This method executes the show command. Which filters the database
	 * according to the parameters specified. It then shows a subset of tasks
	 * and events to the GUI. The valid parameters are either (1) "title" (2)
	 * "event" OR (3) a list of tags.
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

		ArrayList<Item> showTaskList = new ArrayList<Item>(taskList);
		ArrayList<Item> showTaskDoneList = new ArrayList<Item>(taskDoneList);
		ArrayList<Item> showEventList = new ArrayList<Item>(eventList);
		ArrayList<Item> showEventDoneList = new ArrayList<Item>(eventDoneList);

		switch (type) {
		case "all":
			showTaskList = taskList;
			showTaskDoneList = taskDoneList;
			showEventList = eventList;
			showEventDoneList = eventDoneList;
			break;
		case "task":
			Magical.setCurrentTab("tasks");
			break;
		case "tasks":
			Magical.setCurrentTab("tasks");
			break;
		case "event":
			Magical.setCurrentTab("events");
			break;
		case "events":
			Magical.setCurrentTab("events");
			break;
		case "tag":
			showTaskList = filterList(taskList);
			showTaskDoneList = filterList(taskDoneList);
			showEventList = filterList(eventList);
			showEventDoneList = filterList(eventDoneList);
			break;
		default:
			break;
		}

		updateView(showTaskList, showTaskDoneList, showEventList,
				showEventDoneList);

		return String.format(MESSAGE_SHOW_RESULTS, tags);
	}

	/**
	 * Filter items according to tag in the given list and return it
	 * 
	 * @param itemList
	 * @return filtered list to show user
	 */
	private ArrayList<Item> filterList(ArrayList<Item> itemList) {
		ArrayList<Item> filteredItemList = new ArrayList<Item>();
		Set<String> queryTags = new HashSet<String>();
		for (String tag : tags) {
			queryTags.add(tag.toLowerCase());
		}
		for (Item i : itemList) {
			if (i.getTags().containsAll(queryTags)) {
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
