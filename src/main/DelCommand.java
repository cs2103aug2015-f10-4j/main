package main;

import java.io.IOException;

public class DelCommand extends Command{
	private String taskID;
	private String error = "";
	private boolean hasExecuted = false;
	private Task task;
	
	public DelCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = args;
			if(!validTaskID()){
				error += "Task ID" + taskID + "\n";
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
		if (this.count != 1) {
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
		try {
			Magical.storage.deleteTask(task);
			hasExecuted = true;
			return "task deleted";
		} catch (IOException e) {
			return "unable to delete task";
		}
	}
	
	public String undo() {
		if (hasExecuted) {
			try {
				Magical.storage.createTask(task);
				return "task added";
			} catch (IOException e) {
				return "unable to undo delete task";
			}
		} else {
			return "unable to undo failed delete command";
		}
	}
}
