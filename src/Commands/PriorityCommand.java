package Commands;

import java.io.IOException;

import main.Magical;
import main.Task;
import main.UI;

public class PriorityCommand extends Command{

	private Task task;
	private int priority;
	private String error = STRING_EMPTY;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "Number of Arguments\n"
			+ "Use Format: set <task id> <priority>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	private static final String MESSAGE_INVALID_PRIORITY = "Priority: %s\n";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = args.split(" ", 2);
		this.count = argsArray.length;
		
		if(validNumArgs()){
			task = getTaskByID(argsArray[0].trim());
			priority = getPriority(argsArray[1].trim());
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}
			if(priority == -1){
				error += String.format(MESSAGE_INVALID_PRIORITY, argsArray[1].trim());
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}	
		} else {
			error += MESSAGE_ARGUMENT_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
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
		task = prevTask.copy();
		
		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to change priority";
		}
		
		return "Priority updated.";
	}
}
