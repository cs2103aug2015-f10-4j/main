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
			if(count == 2){
				this.query = argsArray[0].trim();
				
				this.type = argsArray[1].trim();
				this.type = type.equals("") ? "" : getType(type);
	
				if (type == null) {
					error += "Type: " + type + "\n";
				}
	
				if (!error.equals("")) {
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
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
	
	public String execute() {
		ArrayList<Task> results = Magical.storage.getTasks();
		if(count == 2){
			ArrayList<Task> filteredResults = new ArrayList<Task>();
			for (Task t : results) {
				if ((t.getTitle().contains(query) || t.getDescription().contains(query)) && t.getType().contains(type)) {
					filteredResults.add(t);
				}
			}
			UI.displayTaskList("Search results", filteredResults);
		} else {
			UI.displayTaskList("Search results", results);
		}
		return null;
	}
	
	@Override
	public boolean isUndoable(){
		return false;
	}
}
