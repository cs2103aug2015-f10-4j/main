package main;

import java.io.IOException;
import java.util.Set;

public class TagCommand extends Command {

	private String taskID;
	private String tag;
	private String error = "";
	private boolean hasExecuted = false;
	private Task task;
	
	public TagCommand(String args) throws Exception {
		super(args);
		
		if (validNumArgs()) {
			taskID = argsArray[0].trim();
			tag = argsArray[1].trim();
			
			if(!validTaskID()){
				error += "Task ID: " + taskID + "\n";
			}

			if (!error.equals("")) {
				throw new Exception("\n----- Invalid arguments ---- \n" + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception("\n----- Invalid arguments ---- \n" + error);
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
		task = Magical.ui.getLastTaskListDisplayed().get(taskID);
		Set<String> tags = task.getTags();
		tags.add(tag);
		task.setTags(tags);
		try {
			Magical.storage.updateTask(task);
			hasExecuted = true;
			return tag + " added to task";
		} catch (IOException e) {
			return "unable to add tag to task";
		}
	}
	
	public String undo() {
		if (hasExecuted) {
			Set<String> tags = task.getTags();
			tags.remove(tag);
			task.setTags(tags);
			try {
				Magical.storage.updateTask(task);
				return tag + " removed from task";
			} catch (IOException e) {
				return "unable to remove tag from task";
			}
		} else {
			return "cannot undo failed tag command";
		}
	}
}
