package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class ShowCommand extends Command {

	private ArrayList<String> tags;
	private String type;

	public ShowCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.type = argsArray.get(0).trim();
		this.tags = null;

		if (type.equals("")) {
			this.type = "all";
		} else if (!type.equalsIgnoreCase("events")
				&& !type.equalsIgnoreCase("tasks")) {
			this.type = "tag";
			this.tags = this.argsArray;
		}
	}

	public boolean validNumArgs() {
		return true;
	}

	/**
	 * This method executes the show command. Which filters the database
	 * according to the parameters specified. It then shows a subset of tasks
	 * and events to the GUI. The valid parameters are either (1) "title" (2)
	 * "event" OR (3) a list of tags.
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
		switch (type) {
		case "all":
			filteredTaskList = taskList;
			filteredTaskDoneList = taskDoneList;
			filteredEventList = eventList;
			filteredEventDoneList = eventDoneList;
			break;
		case "events":
			filteredEventList = eventList;
			filteredEventDoneList = eventDoneList;
			GUIModel.setCurrentTab("events");
			break;
		case "tasks":
			filteredTaskList = taskList;
			filteredTaskDoneList = taskDoneList;
			GUIModel.setCurrentTab("tasks");
			break;
		case "tag":
			Set<String> queryTags = new HashSet<String>(tags);
			for (Item t : taskList) {
				if (t.getTags().containsAll(queryTags)) {
					filteredTaskList.add(t);
				}
			}
			for (Item t : taskDoneList) {
				if (t.getTags().containsAll(queryTags)) {
					filteredTaskDoneList.add(t);
				}
			}
			for (Item t : eventList) {
				if (t.getTags().containsAll(queryTags)) {
					filteredEventList.add(t);
				}
			}
			for (Item t : eventDoneList) {
				if (t.getTags().containsAll(queryTags)) {
					filteredEventDoneList.add(t);
				}
			}
			break;
		default:
			break;
		}
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "show successful";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
