package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gui.GUIController;
import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class ShowCommand extends Command{

	private String error = STRING_EMPTY;
	private ArrayList<String> tags;
	private String type;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format:\n"
			+ "1. show\n"
			+ "2. show <type>\n"
			+ "3. show <tag 1> <tag 2> ...";

	public ShowCommand(String args) throws Exception{
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.type = argsArray.get(0).trim();
		this.tags = null;

		if(type.equals("")){
			this.type = "all";
		} else if(!type.equalsIgnoreCase("events")&&!type.equalsIgnoreCase("tasks")){
			this.type = "tag";
			this.tags = this.argsArray;
		}

		if (!error.equals(STRING_EMPTY)) {
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}

	public boolean validNumArgs() {
		return true;
	}

	@Override
	public String execute() {
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> eventList = Magical.storage.getList(Storage.EVENTS_INDEX);
		ArrayList<Task> eventDoneList = Magical.storage.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
		ArrayList<Task> filteredEventList = new ArrayList<Task>();
		ArrayList<Task> filteredEventDoneList = new ArrayList<Task>();
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
				for (Task t : taskList) {
					if (t.getTags().containsAll(queryTags)) {
						filteredTaskList.add(t);
					}
				}
				for (Task t : taskDoneList) {
					if (t.getTags().containsAll(queryTags)) {
						filteredTaskDoneList.add(t);
					}
				}
				for (Task t : eventList) {
					if (t.getTags().containsAll(queryTags)) {
						filteredEventList.add(t);
					}
				}
				for (Task t : eventDoneList) {
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
	public boolean isUndoable(){
		return false;
	}

	public static void main(String[] args) throws Exception {
//		ShowCommand s = new ShowCommand("");
//		ShowCommand s = new ShowCommand("event");
//		ShowCommand s = new ShowCommand("food work play");
	}
}
