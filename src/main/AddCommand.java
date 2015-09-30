package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	private boolean hasExecuted = false;
	private Task task;
	
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
			error += "No Title" + "\n";
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
		return checkDate(this.dueDate) || checkFloatingTask(this.dueDate, this.type);
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
	
	@Override
	public String execute() {
		task = new Task();
		task.setType(type);
		task.setTitle(title);
		task.setDescription(desc);
		task.setRecurrence(recurrence);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			task.setDueDate(dateFormat.parse(dueDate));
		} catch (ParseException e) {
			return "unable to parse due date";
		}
		task.setStartTime(Integer.parseInt(startTime));
		task.setEndTime(Integer.parseInt(endTime));
		
		try {
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to add task";
		}
		
		hasExecuted = true;
		return "task added";
	}
	
	@Override
	public String undo() {
		if (hasExecuted) {
			try {
				Magical.storage.deleteTask(task);
				return "task deleted";
			} catch (IOException e) {
				return "unable to delete task";
			}
		} else {
			return "cannot undo failed add command.";
		}
	}
}
