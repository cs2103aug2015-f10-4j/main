package Commands;

import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

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
	 * 
	 * @param None
	 *            .
	 * @return message to show user
	 */
	@Override
	public String execute() {
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage
				.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> eventList = Magical.storage
				.getList(Storage.EVENTS_INDEX);
		ArrayList<Task> eventDoneList = Magical.storage
				.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
		ArrayList<Task> filteredEventList = new ArrayList<Task>();
		ArrayList<Task> filteredEventDoneList = new ArrayList<Task>();

		for (Task t : taskList) {
			if (t.getTitle().contains(query)) {
				filteredTaskList.add(t);
			}
		}
		for (Task t : taskDoneList) {
			if (t.getTitle().contains(query)) {
				filteredTaskDoneList.add(t);
			}
		}
		for (Task t : eventList) {
			if (t.getTitle().contains(query)) {
				filteredTaskList.add(t);
			}
		}
		for (Task t : eventDoneList) {
			if (t.getTitle().contains(query)) {
				filteredTaskDoneList.add(t);
			}
		}

		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "search successful";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
