package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class TagCommand extends Command {

	private Task task;
	private String tag;
	private String error = STRING_EMPTY;
	private Task prevTask;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: tag <task_id> <tag name>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	
	public TagCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();
		
		if (validNumArgs()) {
			task = getTaskByID(argsArray.get(0).trim());
			tag = argsArray.get(1).trim();
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray.get(0).trim());
			}

			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}

	public boolean validNumArgs() {
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}
	
	//Need to modify
	public String execute() {
		prevTask = task;
		task = prevTask.copy();
		
		Set<String> tags = task.getTags();
		tags.add(tag);
		task.setTags(tags);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.delete(listIndex, prevTask);
			Magical.storage.create(listIndex, task);
		} catch (IOException e) {
			return "unable to add tag to task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}

		return tag + " added to task";
	}
	
	public static void main(String[] args) throws Exception {
//		TagCommand t = new TagCommand("lalaa");
//		TagCommand t = new TagCommand("t1 adsgasg");
	}
}
