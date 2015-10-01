package main;

import java.io.IOException;
import java.util.Set;

public class DoneCommand extends Command{
	private String taskID;
	private String error = "";
	private Task task;
	
	public DoneCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = argsArray[0];
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
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs() {
		return checkCount();
	}
	
	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}
	
	public String execute() {
		Task task = Magical.ui.getLastTaskList().get(taskID);
		Set<String> tags = task.getTags();
		tags.add("done");
		task.setTags(tags);
		try {
			Magical.storage.updateTask(task);
			return "task archived";
		} catch (IOException e) {
			return "unable to archive task";
		}
	}
}

