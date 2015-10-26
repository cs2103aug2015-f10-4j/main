package Commands;

import java.io.IOException;

import main.Magical;
import main.Task;
import main.UI;

public class PriorityCommand extends Command{

	private Task task;
	private int priority;
	private String error = "";
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "set task id/priority";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			task = getTaskByID(argsArray[0].trim());
			priority = getPriority(argsArray[1].trim());
			if(task == null){
				error += "Task ID: " + argsArray[0] + "\n";
			}
			if(priority == -1){
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

	public boolean validNumArgs() {
		if(this.count != 2){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute() {
		prevTask = task;
		try {
			task = prevTask.copy();
			task.setPriority(priority);
		} catch (ClassNotFoundException | IOException e) {
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
