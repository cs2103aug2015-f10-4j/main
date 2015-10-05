package main;

import java.text.SimpleDateFormat;

public class Command {

	protected String args;
	protected String[] argsArray;
	protected int count;
	protected static final String MESSAGE_HEADER_INVALID = "\n----- Invalid arguments ---- \n"; 

	public Command(String args){
		this.args = args;
		this.argsArray = args.split("/");
		this.count = argsArray.length;
	}
	
	protected boolean checkRecurrence(String recurrence) {
		if(recurrence == null){
			return true;
		}
		String r = recurrence.toLowerCase();
		if (!r.equals("daily") 
				&& !r.equals("weekly")
				&& !r.equals("monthly")
				&& !r.equals("yearly")) {
			return false;
		}
		return true;
	}

	protected boolean checkTime(String time) {
		if(time.length() != 4){
			return false;
		} else {
			int hour = Integer.parseInt(time.substring(0, 2));
			int min = Integer.parseInt(time.substring(2, 4));
			
			if(hour > 23 || hour < 0
				|| min > 59 || min < 0){
				return false;
			}
		}
		return true;
	}

	protected boolean checkDate(String date)  {
		if(date.matches("^\\d+\\-\\d+\\-\\d+")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(date);
				return true;
			} catch (Exception e){
				return false;
			}
		} else {
			return false;
		}
	}
	
	protected boolean checkFloat(String time, String type){
		if(time.equals("") && type.equals("task")){
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean checkTitle(String title) {
		if(title.equals("")){
			return false;
		}
		return true;
	}

	protected boolean checkType(String type){
		if(!type.toLowerCase().equals("event") 
			&& !type.toLowerCase().equals("task")){
			return false;
		}
		return true;
	}
	protected boolean checkTaskID(String taskID){
		return UI.getLastTaskList().containsKey(taskID);
	}
	
	protected boolean checkPriority(String priority){
		try {
			int p = Integer.parseInt(priority);
			return (p >= 1 && p <= 10);
		} catch (Exception e){
			return false;
		}
	}
	
	public String execute(){
		return null;
	}
	
	public boolean isUndoable(){
		return true;
	}
}
