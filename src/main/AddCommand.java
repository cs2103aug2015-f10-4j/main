package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
	private Task task;
	
	private final String MESSAGE_ARGUMENT_PARAMS = "type/title/description/due date/start time/end time/recurrence";
	
	public AddCommand(String args) throws Exception {
		super(args);
		
		if(validNumArgs()){
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
		 
		
			if (!validType()) {
				error += "Type: " + type + "\n";
			}
			if (!validTitle()) {
				error += "No Title" + "\n";
			}
			if (!validDueDate()) {
				error += "Due date: " + dueDate + "\n";
			}
			if (!validStartTime()) {
				error += "Start time: " + startTime + "\n";
			}
			if (!validEndTime()) {
				error += "End time: " + endTime + "\n";
			}
			if (!validRecurrence()) {
				error += "Recurrence: " + recurrence + "\n";
			}
			if (!error.equals("")) {
				throw new Exception("\n----- Invalid arguments ---- \n" + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception("\n----- Invalid arguments ---- \n" + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}
	
	private boolean checkCount(){
		if(this.count != 7 && this.count != 6){
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
			String retMsg = "task added";
			if (isClashing()) {
				retMsg += ". Another task exists on the same date.";
			}
			Magical.storage.createTask(task);
			return retMsg;
		} catch (IOException e) {
			return "unable to add task";
		}
	}

	private boolean isClashing() {
		ArrayList<Task> tasks = Magical.storage.getTasks();
		for (Task t : tasks) {
			if (t.getDueDate().equals(task.getDueDate())) {
				return true;
			}
		}
		return false;
	}
}
