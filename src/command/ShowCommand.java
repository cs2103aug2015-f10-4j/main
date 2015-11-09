package command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

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
	 * @throws Exception
	 */
	public ShowCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		setProperParams();
	}

	/**
	 * Sets the type of the show command to be all if no arguments are given, or
	 * to display tags if event/task is not given as the argument.
	 */
	void checkType() {
		if (type.equals("")) {
			this.type = "all";
		} else if (!type.equalsIgnoreCase("task")
				&& !type.equalsIgnoreCase("tasks")
				&& !type.equalsIgnoreCase("event")
				&& !type.equalsIgnoreCase("events")) {
			this.type = "tag";
		}
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
			GUIModel.setCurrentTab("tasks");
			break;
		case "tasks":
			GUIModel.setCurrentTab("tasks");
			break;
		case "event":
			GUIModel.setCurrentTab("events");
			break;
		case "events":
			GUIModel.setCurrentTab("events");
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
		Set<String> queryTags = new HashSet<String>(tags); 
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

	void setProperParams() {
		this.type = argsArray.get(0).trim();
		this.tags = null;
		checkType();
		this.tags = this.argsArray;
	}

	public boolean validNumArgs() {
		return true;
	}
}
