package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import main.GUIModel;
import main.Magical;
import main.Task;
import main.UI;

public class DateCommand extends Command {

	
	private Date startDate;
	private Date endDate;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: date <start date> <end date>";
	private static final String MESSAGE_INVALID_DATE = "%s date: %s (Date should be dd-MM-yyyy)\n";

	public DateCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = args.split("(?<!next)\\s", 2);
		System.out.println(Arrays.toString(argsArray));
		this.count = argsArray.length;

		if (validNumArgs()) {
			String start = argsArray[0].trim();
			String end = count == 2 ? argsArray[1].trim() : STRING_EMPTY;
			
			startDate = start.equals(STRING_EMPTY) ? new Date(Long.MIN_VALUE) : getDate(start);
			endDate = end.equals(STRING_EMPTY) ? new Date(Long.MAX_VALUE) : getDate(end);
			
			
			if (startDate == null) {
				startDate = flexiParse(start);
				//error += String.format(MESSAGE_INVALID_DATE, "Start", start);
			}

			if (endDate == null) {
				endDate = flexiParse(end);
				//error += String.format(MESSAGE_INVALID_DATE, "End", end);
			} 

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
		return endDate.after(startDate);
	}

	public String execute() {
		ArrayList<Task> taskList = Magical.storage.getTasks();
		ArrayList<Task> taskDoneList = Magical.storage.getTasksDone();
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
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
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setDoneList(filteredTaskDoneList);
		return "date command successful";
	}

	@Override
	public boolean isUndoable(){
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		//DateCommand d = new DateCommand("");
		//DateCommand d = new DateCommand("29-01-93 28-01-92");
		//DateCommand d = new DateCommand("29-01-93 28-01-94");
		//DateCommand d = new DateCommand("29-01-93 28-01-9a2");
		//DateCommand d = new DateCommand("2a9-01-93 28-01-92");
	}
}
