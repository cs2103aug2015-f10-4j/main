package Commands;

import java.io.IOException;

import main.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class DelCommand extends Command{

	private String error = STRING_EMPTY;
	private Task task;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+"Use Format: delete <task_id>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";

	public DelCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = args.split(STRING_EMPTY, 1);
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
			int listIndex = Storage.getListIndex(argsArray[0]);
			Magical.storage.delete(listIndex, task);
			return "task deleted";
		} catch (IOException e) {
			return "unable to delete task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}
	}
	
	public static void main(String[] args) throws Exception {
//		DelCommand d = new DelCommand("t1");
	}
}
