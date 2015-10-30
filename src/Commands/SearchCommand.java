package Commands;

import java.util.ArrayList;

import main.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

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
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();

		for (Task t : taskList) {
			if (t.getTitle().contains(query)) {
				filteredTaskList.add(t);
			}
		}
		for (Task t : taskDoneList) {
			if (t.getTitle().contains(query)) {
				filteredTaskDoneList.add(t);
			}
		}

		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setDoneList(filteredTaskDoneList);
		return "search successful";
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
