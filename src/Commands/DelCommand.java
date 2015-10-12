package Commands;

import java.io.IOException;

import main.Magical;
import main.Task;
import main.UI;

public class DelCommand extends Command{
	
	private Task taskID;
	private String error = "";
	private Task task;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "delete task_id";
	
	public DelCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = getTaskID(argsArray[0].trim());
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
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
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
