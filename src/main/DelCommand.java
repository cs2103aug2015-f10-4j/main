package main;

public class DelCommand extends Command{
	private String taskID;
	private String error = "";
	
	public DelCommand(String args) throws Exception{
		super(args);
		
		if(validNumArgs()){
			taskID = args;
			if(!validTaskID()){
				error += "Task ID" + taskID + "\n";
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
}
