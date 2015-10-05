package main;

import java.io.IOException;
import java.util.Set;

public class UntagCommand extends Command {

	private String taskID;
	private String tag;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "untag task_id/tag_name";
	
	public UntagCommand(String args) throws Exception {
		super(args);
		
		if (validNumArgs()) {
			taskID = argsArray[0].trim();
			tag = argsArray[1].trim();
			
			if(!validTaskID()){
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
	
	private boolean checkCount() {
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs() {
		return checkCount();
	}
	
	public boolean validTaskID() {
		return checkTaskID(this.taskID);
	}
	
	public String execute() {
		prevTask = UI.getLastTaskList().get(taskID);
		try {
			task = prevTask.copy();
		} catch (IOException e) {
			return "unable to remove tag from task";
		} catch (ClassNotFoundException e) {
			return "unable to remove tag from task";
		}

		Set<String> tags = task.getTags();
		tags.remove(tag);
		task.setTags(tags);

		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to remove tag from task";
		}

		return tag + " removed from task";
	}
}
