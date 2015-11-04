package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
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
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(STRING_EMPTY, 1)));
		this.count = argsArray.size();
		
		if(validNumArgs()){
			
			task = getTaskByID(argsArray.get(0).trim());
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray.get(0).trim());
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
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.delete(listIndex, task);
			return "task deleted";
		} catch (IOException e) {
			return "unable to delete task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage.getList(Storage.EVENTS_DONE_INDEX));
		}
	}
	
	public static void main(String[] args) throws Exception {
//		DelCommand d = new DelCommand("t1");
	}
}
