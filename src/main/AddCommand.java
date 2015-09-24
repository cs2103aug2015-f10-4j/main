package main;

import java.util.Arrays;
import java.util.HashMap;

public class AddCommand extends Command{

	private String[] argsArray;
	private String type;
	private String title;
	private String desc;
	private String dueDate;
	private String startTime;
	private String endTime;
	private String recurrence;
	private String error = "";
	
	public AddCommand(String args) throws Exception {
		super(args);
		
		if(checkCount()){
			this.argsArray = args.split("/");
			this.type = argsArray[0].trim();
			this.title = argsArray[1].trim();
			this.desc = argsArray[2].trim();
			this.dueDate = argsArray[3].trim();
			this.startTime = argsArray[4].trim();
			this.endTime = argsArray[5].trim();
			
			if(argsArray.length == 6){
				recurrence = null;
			} else {
				recurrence = argsArray[6].trim();
			}
		}
		 
		if(!validNumArgs()){
			error += "Number of Arguments\n";
		}
		if(!validType()){
			error += "Type: " + type + "\n";
		}
		if(!validTitle()){
			error += "Title: " + title + "\n";
		}
		if(!validDueDate()){
			error += "Due date: " + dueDate + "\n";
		}
		if(!validStartTime()){
			error += "Start time: " + startTime + "\n";
		}
		if(!validEndTime()){
			error += "End time: " + endTime + "\n";
		}
		if(!validRecurrence()){
			error += "Recurrence: " + recurrence + "\n";
		}
		if(!error.equals("")){
			throw new Exception("\n----- Invalid arguments ---- \n" + error);
		}
	}
	
	private boolean checkCount(){
		if(this.count != 6){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs(){
		return checkCount();
	}
	
	public boolean validType(){
		return checkType(this.type);
	}
	
	public boolean validTitle(){
		return checkTitle(this.title);
	}
	
	public boolean validDueDate(){
		return checkDueDate(this.dueDate, this.type);
	}
	
	public boolean validStartTime(){
		return checkTime(this.startTime);
	}
	
	public boolean validEndTime(){
		return checkTime(this.endTime);
	}
	
	public boolean validRecurrence(){
		return checkRecurrence(this.recurrence);
	}
	
	public HashMap<String, String> getArgs(){
		HashMap<String, String> argsTable = new HashMap<String, String>();
		argsTable.put("type", type);
		argsTable.put("title", title);
		argsTable.put("description", desc);
		argsTable.put("dueDate", dueDate);
		argsTable.put("startTime", startTime);
		argsTable.put("endTime", endTime);
		argsTable.put("recurrence", recurrence);
		return argsTable;
	}
}
