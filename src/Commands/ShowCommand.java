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
		} else if (!type.equalsIgnoreCase("task")
				&& !type.equalsIgnoreCase("tasks")
				&& !type.equalsIgnoreCase("event")
				&& !type.equalsIgnoreCase("events")) {
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
			showTaskList = new ArrayList<Item>();
			showTaskDoneList = new ArrayList<Item>();
			showEventList = new ArrayList<Item>();
			showEventDoneList = new ArrayList<Item>();
			Set<String> queryTags = new HashSet<String>(tags);
			for (Item t : taskList) {
				if (t.getTags().containsAll(queryTags)) {
					showTaskList.add(t);
				}
			}
			for (Item t : taskDoneList) {
				if (t.getTags().containsAll(queryTags)) {
					showTaskDoneList.add(t);
				}
			}
			for (Item t : eventList) {
				if (t.getTags().containsAll(queryTags)) {
					showEventList.add(t);
				}
			}
			for (Item t : eventDoneList) {
				if (t.getTags().containsAll(queryTags)) {
					showEventDoneList.add(t);
				}
			}
			break;
		default:
			break;
		}
		GUIModel.setTaskList(showTaskList);
		GUIModel.setTaskDoneList(showTaskDoneList);
		GUIModel.setEventList(showEventList);
		GUIModel.setEventDoneList(showEventDoneList);
		return "show successful";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
