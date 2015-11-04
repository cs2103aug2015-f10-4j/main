package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class DoneCommand extends Command{
	private String error = STRING_EMPTY;
	private Task task;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: done <task_id>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	
	public DoneCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(STRING_EMPTY, 1)));
		this.count = argsArray.size();
		
		if(validNumArgs()){
			task = getTaskByID(argsArray.get(0).trim());
			
			if(task == null){
				error += String.format(MESSAGE_INVALID_ID, argsArray.get(0).trim());
			} else if (argsArray.get(0).trim().contains("d")){
				error += "Done tasks cannot be done!";
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
			int listIndex = Storage.getListIndex(argsArray.get(0));
			int complementListIndex = Storage.getComplementListIndex(listIndex);
			Magical.storage.delete(listIndex, task);
			Magical.storage.create(complementListIndex, task);
		} catch (IOException e) {
			return "unable to archive task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}

		return "task archived";
	}
}

