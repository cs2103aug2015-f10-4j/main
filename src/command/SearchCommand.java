package command;

import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class SearchCommand extends Command {

	private String query;

	public SearchCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split("", 1)));
		this.count = argsArray.size();
		this.query = args.trim();
	}

	public boolean validNumArgs() {
		return true;
	}

	/**
	 * This method executes the search command. Which searches for the specified
	 * query text in the titles of all events and tasks. It then displays the
	 * found tasks and events to the GUI.
	 * 
	 * @param None
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
		ArrayList<Item> filteredTaskList = new ArrayList<Item>();
		ArrayList<Item> filteredTaskDoneList = new ArrayList<Item>();
		ArrayList<Item> filteredEventList = new ArrayList<Item>();
		ArrayList<Item> filteredEventDoneList = new ArrayList<Item>();

		for (Item i : taskList) {
			if (i.getTitle().contains(query)) {
				filteredTaskList.add(i);
			}
		}
		for (Item i : taskDoneList) {
			if (i.getTitle().contains(query)) {
				filteredTaskDoneList.add(i);
			}
		}
		for (Item i : eventList) {
			if (i.getTitle().contains(query)) {
				filteredEventList.add(i);
			}
		}
		for (Item i : eventDoneList) {
			if (i.getTitle().contains(query)) {
				filteredEventDoneList.add(i);
			}
		}

		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "search results for: [" + query + "]";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
