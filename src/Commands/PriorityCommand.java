package Commands;

import java.io.IOException;

import main.Magical;
import main.Task;
import main.UI;

public class PriorityCommand extends Command{

	private Task taskID;
	private int priority;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "set task id/priority";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = getTaskID(argsArray[0].trim());
			priority = getPriority(argsArray[1].trim());
			if(taskID == null){
				error += "Task ID: " + taskID + "\n";
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

	private boolean validNumArgs() {
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
			task.setPriority(priority);
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