package Commands;

import java.io.IOException;
import java.util.Arrays;

import main.Magical;
import main.Task;

public class DelCommand extends Command{

	private String error = STRING_EMPTY;
	private Task task;

	private static final String MESSAGE_ARGUMENT_PARAMS = "delete <task_id>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";

	public DelCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = args.split("", 1);
		this.count = argsArray.length;
		
		if(validNumArgs()){
			
			task = getTaskByID(argsArray[0].trim());
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}
			if (!error.equals(STRING_EMPTY)) {
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
	
	public static void main(String[] args) throws Exception {
		DelCommand d = new DelCommand("t1");
	}
}
