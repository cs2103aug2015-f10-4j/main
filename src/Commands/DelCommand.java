package Commands;

import java.io.IOException;

import main.Magical;
import main.Task;
import main.UI;

public class DelCommand extends Command{
	
	private String error = "";
	private Task task;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "delete task_id";
	
	public DelCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			task = getTaskByID(argsArray[0].trim());
			if(task == null){
				error += "Task ID: " + argsArray[0] + "\n";
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
		try {
			Magical.storage.deleteTask(task);
			return "task deleted";
		} catch (IOException e) {
			return "unable to delete task";
		}
	}
}
