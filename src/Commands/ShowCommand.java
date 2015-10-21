package Commands;

import main.Task;
import main.UI;

public class ShowCommand extends Command{
	private Task task;
	private String error = "";
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "show task_id";
	
	public ShowCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			task = getTaskByID(argsArray[0].trim());
			if(task == null){
				error += "Task ID: " + argsArray[0] + "\n";
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
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute() {
		UI.displayTaskDetails(task);
		return null;
	}
	
	@Override
	public boolean isUndoable(){
		return false;
	}
}
