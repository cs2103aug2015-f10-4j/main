package Commands;

import java.io.IOException;
import java.util.Set;

import main.Magical;
import main.Task;
import main.UI;

public class TagCommand extends Command {

	private Task taskID;
	private String tag;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "\n1. tag tag_name\n2. tag task_id/tag_name";
	
	public TagCommand(String args) throws Exception {
		super(args);
		
		if (validNumArgs()) {
			taskID = getTaskID(argsArray[0].trim());
			tag = argsArray[1].trim();
			
			if(taskID == null){
				error += "Task ID: " + taskID + "\n";
			}

			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}

	public boolean validNumArgs() {
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}
	
	public String execute() {
		prevTask = UI.getLastTaskList().get(taskID);
		try {
			task = prevTask.copy();
		} catch (IOException e) {
			return "unable to add tag to task";
		} catch (ClassNotFoundException e) {
			return "unable to add tag to task";
		}

		Set<String> tags = task.getTags();
		tags.add(tag);
		task.setTags(tags);

		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to add tag to task";
		}

		return tag + " added to task";
	}
}
