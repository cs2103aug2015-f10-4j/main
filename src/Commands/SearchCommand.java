package Commands;

import java.util.ArrayList;

import main.Magical;
import main.Task;
import main.UI;

public class SearchCommand extends Command {

	private String query;
	private String type;
	private String error = "";
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "\n1. search\n2. search query/type";
	
	public SearchCommand(String args) throws Exception {
		super(args);
		
		if (validNumArgs()) {
			this.query = argsArray[0].trim();

			if (argsArray.length == 1) {
				type = "";
			} else {
				type = argsArray[1].trim();
			}

			if (!type.equals("") && !validType()) {
				error += "Type: " + type + "\n";
			}

			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
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
	
	public boolean validType() {
		return checkType(this.type);
	}
	
	public String execute() {
		ArrayList<Task> results = Magical.storage.getTasks();
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for (Task t : results) {
			if ((t.getTitle().contains(query) || t.getDescription().contains(query)) && t.getType().contains(type)) {
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
