package Commands;

import gui.GUIModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import main.Magical;
import main.Storage;
import main.Task;

public class SortCommand extends Command{

	private String sortParam;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format:\n"
			+ "1. sort\n"
			+ "2. sort <sortParam>\n";

	private static final String MESSAGE_INVALID_SORT = "Sort Parameter: %s";
	
	
	public SortCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.sortParam = argsArray.get(0).trim().toLowerCase();
		
		if(validNumArgs()){
			if(sortParam.equals(STRING_EMPTY)){
				sortParam = "priority";
			} else if (!isValidSortParam()){
				error += String.format(MESSAGE_INVALID_SORT, sortParam);
			}
			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}	
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}
	
	private boolean isValidSortParam(){
		switch(sortParam){
			case "priority":
				return true;
			case "title":
				return true;
			case "date":
				return true;
			default:
				return false;
		}
	}

	@Override
	public String execute() throws Exception {
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> eventList = Magical.storage.getList(Storage.EVENTS_INDEX);
		ArrayList<Task> eventDoneList = Magical.storage.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>(taskList);
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>(taskDoneList);
		ArrayList<Task> filteredEventList = new ArrayList<Task>(eventList);
		ArrayList<Task> filteredEventDoneList = new ArrayList<Task>(eventDoneList);
		switch (sortParam) {
			case "priority":
				Collections.sort(filteredTaskList, Task.Comparators.PRIORITY);
				Collections.sort(filteredTaskDoneList, Task.Comparators.PRIORITY);
				Collections.sort(filteredEventList, Task.Comparators.PRIORITY);
				Collections.sort(filteredEventDoneList, Task.Comparators.PRIORITY);
				break;
			case "date":
				Collections.sort(filteredTaskList, Task.Comparators.DATE);
				Collections.sort(filteredTaskDoneList, Task.Comparators.DATE);
				Collections.sort(filteredEventList, Task.Comparators.DATE);
				Collections.sort(filteredEventDoneList, Task.Comparators.DATE);
				break;
			case "title":
				Collections.sort(filteredTaskList, Task.Comparators.TITLE);
				Collections.sort(filteredTaskDoneList, Task.Comparators.TITLE);
				Collections.sort(filteredEventList, Task.Comparators.TITLE);
				Collections.sort(filteredEventDoneList, Task.Comparators.TITLE);
			default:
				break;
		}
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "sort successful";
	}

	@Override
	public boolean validNumArgs() {
		if (count > 1){
			return false;
		} else {
			return true;
		}
	}

}
