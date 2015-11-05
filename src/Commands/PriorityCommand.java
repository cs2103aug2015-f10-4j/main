package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class PriorityCommand extends Command{

	private Task task;
	private int priority;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "Number of Arguments\n"
			+ "Use Format: set <task id> <priority>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	private static final String MESSAGE_INVALID_PRIORITY = "Priority: %s\n";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();
		
		if(validNumArgs()){
			task = getTaskByID(argsArray.get(0).trim());
			priority = getPriority(argsArray.get(1).trim());
			
			if(task == null){
				invalidArgs.add("taskID");
			}
			if(priority == -1){
				invalidArgs.add("priority");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID + String.join(", ", invalidArgs));
			}	
		} else {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_PARAMS);
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
		task.setPriority(priority);
		
		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.delete(listIndex, prevTask);
			Magical.storage.create(listIndex, task);
		} catch (IOException e) {
			return "unable to change priority";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage.getList(Storage.EVENTS_DONE_INDEX));
		}
		
		return "Priority updated.";
	}
}
