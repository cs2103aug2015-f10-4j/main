package main;

import java.io.IOException;

public class DelCommand extends Command{
	
	private String taskID;
	private String error = "";
	private Task task;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "delete task_id";
	
	public DelCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = argsArray[0];
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
		task = UI.getLastTaskList().get(taskID);
		try {
			Magical.storage.deleteTask(task);
			return "task deleted";
		} catch (IOException e) {
			return "unable to delete task";
		}
	}
}