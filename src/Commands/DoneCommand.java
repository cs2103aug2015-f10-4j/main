package Commands;

import java.io.IOException;
import java.util.Set;

import main.GUIModel;
import main.Magical;
import main.Task;
import main.UI;

public class DoneCommand extends Command{
	private String error = STRING_EMPTY;
	private Task task;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: done <task_id>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	
	public DoneCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = args.split(STRING_EMPTY, 1);
		this.count = argsArray.length;
		
		if(validNumArgs()){
			task = getTaskByID(argsArray[0].trim());
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}			
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
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
			Magical.storage.createTaskDone(task);
		} catch (IOException e) {
			return "unable to archive task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getTasks());
			GUIModel.setDoneList(Magical.storage.getTasksDone());
		}

		return "task archived";
	}
}

