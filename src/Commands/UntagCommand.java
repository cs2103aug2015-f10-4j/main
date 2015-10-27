package Commands;

import java.io.IOException;
import java.util.Set;

import main.Magical;
import main.Task;
import main.UI;

public class UntagCommand extends Command {

	private Task task;
	private String tag;
	private String error = STRING_EMPTY;
	private Task prevTask;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: untag <task_id> <tag name>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	
	public UntagCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = args.split(" ", 2);
		this.count = argsArray.length;
		
		if (validNumArgs()) {
			task = getTaskByID(argsArray[0].trim());
			tag = argsArray[1].trim();
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}

			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}
	
	public boolean validNumArgs() {
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}
	
	//Need to modify
	public String execute() {
		prevTask = task;
		try {
			task = prevTask.copy();
		} catch (IOException e) {
			return "unable to remove tag from task";
		} catch (ClassNotFoundException e) {
			return "unable to remove tag from task";
		}

		Set<String> tags = task.getTags();
		tags.remove(tag);
		task.setTags(tags);

		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to remove tag from task";
		}

		return tag + " removed from task";
	}
}
