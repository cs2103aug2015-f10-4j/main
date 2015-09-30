package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchCommand extends Command {

	private String[] argsArray;
	private String query;
	private String type;
	private String error = "";
	
	public SearchCommand(String args) throws Exception {
		super(args);
		
		if (checkCount()) {
			this.argsArray = args.split("/");
			this.query = argsArray[0].trim();
			
			if(argsArray.length == 1){
				type = "";
			} else {
				type = argsArray[1].trim();
			}
		}
		 
		if (!validNumArgs()) {
			error += "Number of Arguments\n";
		}
		
		if (!type.equals("") && !validType()) {
			error += "Type: " + type + "\n";
		}

		if (!error.equals("")) {
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
	
	public boolean validType() {
		return checkType(this.type);
	}
	
	public void execute() {
		ArrayList<Task> results = Magical.storage.getTasks();
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for (Task t : results) {
			if ((t.getTitle().contains(query) || t.getDescription().contains(query)) && t.getType().contains(type)) {
				filteredResults.add(t);
			}
		}
		UI.displayTaskList("Search results", filteredResults);
	}
	
	public void undo() {
		// no changes done => nothing to undo
	}
}
