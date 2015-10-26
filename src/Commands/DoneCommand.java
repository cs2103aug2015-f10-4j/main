package Commands;

import java.io.IOException;
import java.util.Set;

import main.Magical;
import main.Task;
import main.UI;

public class DoneCommand extends Command{
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "done task_id";
	
	public DoneCommand(String args) throws Exception{
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
		prevTask = task;
		try {
			task = prevTask.copy();
		} catch (IOException e) {
			return "unable to add tag to task";
		} catch (ClassNotFoundException e) {
			return "unable to add tag to task";
		}

		Set<String> tags = task.getTags();
		tags.add("done");
		task.setTags(tags);

		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to archive task";
		}

		return "task archived";
	}
}

