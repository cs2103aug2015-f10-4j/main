package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
			+ "3. show tag <tag 1> <tag 2> ...";
	
	public ShowCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.type = argsArray.get(0).trim();
		this.tags = null;
		
		if(type.equals("")){
			this.type = "all";
		} else if(!type.equalsIgnoreCase("event")&&!type.equalsIgnoreCase("task")){
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
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
		switch (type) {
			case "all":
				filteredTaskList = taskList;
				filteredTaskDoneList = taskDoneList;
				break;
			case "event":
				for (Task t : taskList) {
					if (t.getType().equals("event")) {
						filteredTaskList.add(t);
					}
				}
				for (Task t : taskDoneList) {
					if (t.getType().equals("event")) {
						filteredTaskDoneList.add(t);
					}
				}
				break;
			case "task":
				for (Task t : taskList) {
					if (t.getType().equals("task")) {
						filteredTaskList.add(t);
					}
				}
				for (Task t : taskDoneList) {
					if (t.getType().equals("task")) {
						filteredTaskDoneList.add(t);
					}
				}
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
				break;
			default:
				break;
		}
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
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
