package Commands;

import java.util.ArrayList;
import java.util.Arrays;

import main.Magical;
import main.Task;
import main.UI;

public class SearchCommand extends Command {

	private String query;
	private String type;
	private String error = STRING_EMPTY;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format:\n"
			+ "1. search\n"
			+ "2. search <query>";
	
	public SearchCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = args.split("", 1);
		this.count = argsArray.length;
		this.query = args.trim();			
	}
	
	public boolean validNumArgs() {
		return true;
	}
	
	//needs to be re-written
	public String execute() {
		ArrayList<Task> results = Magical.storage.getTasks();
		if(count == 2){
			ArrayList<Task> filteredResults = new ArrayList<Task>();
			for (Task t : results) {
				if ((t.getTitle().contains(query) || t.getType().contains(type))) {
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
	
	public static void main(String[] args) throws Exception {
		//SearchCommand s = new SearchCommand("asasd asdhfnasfd");
		//SearchCommand s = new SearchCommand(" ");
	}
}
