package Commands;

import java.util.ArrayList;
import java.util.Date;

import main.Magical;
import main.Task;
import main.UI;

public class DateCommand extends Command {

	private Date startDate;
	private Date endDate;
	private String error = "";
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "date start date/end date";

	public DateCommand(String args) throws Exception {
		super(args);

		if (validNumArgs()) {
			String start = argsArray[0].trim();
			String end = argsArray[1].trim();
			
			startDate = start.equals("") ? new Date(Long.MIN_VALUE) : getDate(start);
			endDate = end.equals("") ? new Date(Long.MAX_VALUE) : getDate(end);
			
			if (startDate == null) {
				error += "Start date: " + startDate + "\n";
			}

			if (endDate == null) {
				error += "End date: " + endDate + "\n";
			}
			
			if (!validDateRange()) {
				error += "End date greater than start date";
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
		ArrayList<Task> results = Magical.storage.getTasks();
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for (Task t : results) {
			if (t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0) {
				filteredResults.add(t);
			}
		}
		UI.displayTaskList("Search results", filteredResults);
		return null;
	}

	@Override
	public boolean isUndoable(){
		return false;
	}
}
