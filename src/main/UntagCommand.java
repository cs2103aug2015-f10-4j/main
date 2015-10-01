package main;

import java.io.IOException;
import java.util.Set;

public class UntagCommand extends Command {

	private String taskID;
	private String tag;
	private String error = "";
	private boolean hasExecuted = false;
	private Task task;
	private Task prevTask;
	
	public UntagCommand(String args) throws Exception {
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
		task = Magical.ui.getLastTaskList().get(taskID);
		try {
			prevTask = (Task) task.clone();
			Magical.storage.deleteTask(prevTask);
		} catch (CloneNotSupportedException e1) {
			prevTask = null;
		} catch (IOException e) {
			// TODO Fix Magical.storage.deleteTask(prevTask) location
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
		
		hasExecuted = true;
		return tag + " removed from task";
	}
	
	@Override
	public String undo() {
		if (hasExecuted) {
			if (prevTask == null) {
				return "unable to undo edit";
			}
			try {
				Magical.storage.deleteTask(task);
				Magical.storage.createTask(prevTask);
				return "Undo successful.";
			} catch (IOException e) {
				return "unable to undo untag";
			}
		} else {
			return "cannot undo failed untag command.";
		}
	}
}
