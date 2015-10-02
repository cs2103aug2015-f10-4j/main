package main;

public class ShowCommand extends Command{
	private String taskID;
	private String error = "";
	
	public ShowCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = argsArray[0];
			if(!validTaskID()){
				error += "Task ID: " + taskID + "\n";
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
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs() {
		return checkCount();
	}
	
	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}
	
	@Override
	public String execute() {
		Task task = UI.getLastTaskList().get(taskID);
		UI.displayTaskDetails(task);
		return null;
	}
	
	@Override
	public boolean isUndoable(){
		return false;
	}
}
