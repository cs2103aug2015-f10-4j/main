package Commands;

import java.io.IOException;
import java.util.Set;

import main.Magical;
import main.Task;
import main.UI;

public class DoneCommand extends Command{
	private String taskID;
	private String error = "";
	private Task task;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "done task_id";
	
	public DoneCommand(String args) throws Exception{
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
	
	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}
	
	public String execute() {
		Task task = UI.getLastTaskList().get(taskID);
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

