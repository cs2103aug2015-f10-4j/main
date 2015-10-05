package main;

public class PriorityCommand extends Command{

	private String taskID;
	private String priority;
	private String error = "";
	
	private final String MESSAGE_ARGUMENT_PARAMS = "task id/priority";
	
	public PriorityCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = argsArray[0];
			priority = argsArray[1];
			if(!validTaskID()){
				error += "Task ID: " + taskID + "\n";
			}
			if(!validPriority()){
				error += "Priority: " + priority + "\n";
			}
			if (!error.equals("")) {
				throw new Exception("\n----- Invalid arguments ---- \n" + error);
			}	
		} else {
			error += "Number of Arguments\n";
			throw new Exception("\n----- Invalid arguments ---- \n" + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}
	
	private boolean validPriority() {
		return checkPriority(priority);
	}

	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}

	private boolean validNumArgs() {
		return checkCount();
	}

	private boolean checkCount(){
		if(this.count != 2){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute(){
		return null;
	}
}
