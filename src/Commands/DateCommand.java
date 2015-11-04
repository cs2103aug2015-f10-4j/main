package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Task;
import gui.GUIModel;

public class DateCommand extends Command {

	
	private CustomDate startDate;
	private CustomDate endDate;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: date <start date> <end date>";
	private static final String MESSAGE_INVALID_DATE = "%s date: %s (Date should be dd-MM-yyyy)\n";

	public DateCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = new ArrayList(Arrays.asList(args.split("\\s")));
		
		System.out.println(argsArray);
		
		//checking if the 2 consecutive elements belong together as a date
		if(argsArray.size() > 1){	
			int i = 0;
			while(i+1 < argsArray.size()){
				
				if(getDate(argsArray.get(i))== null && getDate(argsArray.get(i+1))!= null){
					argsArray.set(i, argsArray.get(i) + " " + argsArray.get(i+1));
					argsArray.remove(i + 1);
				}
				i++;
				System.out.println(argsArray);
			}
		}
		this.count = argsArray.size();

		if (validNumArgs()) {
			String start = argsArray.get(0).trim();
			String end = count == 2 ? argsArray.get(1).trim() : STRING_EMPTY;
			
			
			startDate = start.equals(STRING_EMPTY) 
					? new CustomDate(new Date(0)) 
					: getDate(start);	
			endDate = end.equals(STRING_EMPTY) 
					? new CustomDate(new Date(Long.MAX_VALUE)) 
					: getDate(end);
			
			if (startDate == null) {
				error += String.format(MESSAGE_INVALID_DATE, "Start", start);
			}

			if (endDate == null) {
				error += String.format(MESSAGE_INVALID_DATE, "End", end);
			} 
			//System.out.println(startDate.toString());
			//System.out.println(endDate.toString());
			if (startDate != null && endDate != null && !validDateRange()) {
				error += "End date is earlier than start date";
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
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validDateRange() {
		return endDate.compareTo(startDate) != -1;
	}

	public String execute() {
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> eventList = Magical.storage.getList(Storage.EVENTS_INDEX);
		ArrayList<Task> eventDoneList = Magical.storage.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
		ArrayList<Task> filteredEventList = new ArrayList<Task>();
		ArrayList<Task> filteredEventDoneList = new ArrayList<Task>();
		for (Task t : taskList) {
			if (t.getDueDate() != null && t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0) {
				filteredTaskList.add(t);
			}
		}
		for (Task t : taskDoneList) {
			if (t.getDueDate() != null && t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0) {
				filteredTaskDoneList.add(t);
			}
		}
		for (Task t : eventList) {
			if (t.getDueDate() != null && t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0) {
				filteredEventList.add(t);
			}
		}
		for (Task t : eventDoneList) {
			if (t.getDueDate() != null && t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0) {
				filteredEventDoneList.add(t);
			}
		}
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "date command successful";
	}

	@Override
	public boolean isUndoable(){
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		//DateCommand d = new DateCommand("");
		//DateCommand d1 = new DateCommand("29-01-93");
		//DateCommand d2 = new DateCommand("29-01-93 28-01-92");
		//DateCommand d3 = new DateCommand("29-01-93 28-01-94");
		//DateCommand d4 = new DateCommand("29-01-93 28-01-9a2");
		//DateCommand d5 = new DateCommand("next monday next tuesday next wednesday next following thursday");
		//DateCommand d6 = new DateCommand("22a-09-94 22-09-1993");
	}
}
