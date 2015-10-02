package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateCommand extends Command {

	private String startDate;
	private String endDate;
	private String error = "";
	private Date start;
	private Date end;

	public DateCommand(String args) throws Exception {
		super(args);

		if (validNumArgs()) {
			startDate = argsArray[0].trim();

			if (argsArray.length == 1) {
				endDate = "";
			} else {
				endDate = argsArray[1].trim();
			}

			if (!startDate.equals("") && !validStartDate()) {
				error += "Start date: " + startDate + "\n";
			}

			if (!endDate.equals("") && !validEndDate()) {
				error += "End date: " + endDate + "\n";
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				start = startDate.equals("") ? new Date(Long.MIN_VALUE) : dateFormat.parse(startDate);
				end = endDate.equals("") ? new Date(Long.MAX_VALUE) : dateFormat.parse(endDate); 
			} catch (ParseException e) {
				error += "Unable to parse dates\n";
			}

			if (!validDateRange()) {
				error += "End date greater than start date";
			}

			if (!error.equals("")) {
				throw new Exception("\n----- Invalid arguments ---- \n" + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception("\n----- Invalid arguments ---- \n" + error);
		}
	}

	private boolean checkCount() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validNumArgs() {
		return checkCount();
	}

	public boolean validStartDate() {
		return checkDate(this.startDate);
	}

	public boolean validEndDate() {
		return checkDate(this.endDate);
	}

	public boolean validDateRange() {
		return end.after(start);
	}

	public String execute() {
		ArrayList<Task> results = Magical.storage.getTasks();
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for (Task t : results) {
			if (t.getDueDate().compareTo(start) >= 0 && t.getDueDate().compareTo(end) <= 0) {
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
