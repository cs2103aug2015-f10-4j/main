package main;

import java.io.IOException;

public class PriorityCommand extends Command{

	private String taskID;
	private String priority;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "set task id/priority";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = argsArray[0];
			priority = argsArray[1];
			if(!validTaskID()){
				error += "Task ID: " + taskID + "\n";
			}
			if(!validPriority()){
				error += "Priority: " + priority + "\n";
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}	
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}
	
	private boolean validPriority() {
		return checkPriority(priority);
	}

	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}

	private boolean validNumArgs() {
		return checkCount();
	}

	private boolean checkCount(){
		if(this.count != 2){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute() {
		prevTask = UI.getLastTaskList().get(taskID);
		try {
			task = (Task) prevTask.clone();
			task.setPriority(Integer.parseInt(priority));
		} catch (CloneNotSupportedException e1) {
			return "unable to change priority";
		}
		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to change priority";
		}
		
		return "Priority updated.";
	}
}
