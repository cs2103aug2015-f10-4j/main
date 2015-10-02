package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditCommand extends Command{

	private String taskID;
	private String field;
	private String value;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		if(validNumArgs()){
			this.argsArray = args.split("/");
			this.taskID = argsArray[0];
			this.field = argsArray[1];
			this.value = argsArray[2];
			
			if (!validTaskID()) {
				error += "Task ID: " + taskID + "\n";
			}
		
			if (field.equalsIgnoreCase("title") && !validTitle()) {
				error += "No Title" + "\n";
			}
			else if (field.equalsIgnoreCase("due date") && !validDueDate()) {
				error += "Due date: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("start time") && !validStartTime()) {
				error += "Start time: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("end time") && !validEndTime()) {
				error += "End time: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("recurrence") && !validRecurrence()) {
				error += "Recurrence: " + value + "\n";
			}
//			else {
//				error += "Invalid field.\n";
//			}
			if (!error.equals("")) {
				throw new Exception("\n----- Invalid arguments ---- \n" + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception("\n----- Invalid arguments ---- \n" + error);
		}
	}
	
	private boolean checkCount(){
		if(this.count != 3){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs(){
		return checkCount();
	}
	
	public boolean validTaskID(){
		return checkTaskID(this.taskID);
	}
	
	public boolean validTitle(){
		return checkTitle(this.value);
	}
	
	public boolean validDueDate(){
		return checkDate(this.value);
	}
	
	public boolean validStartTime(){
		return checkTime(this.value);
	}
	
	public boolean validEndTime(){
		return checkTime(this.value);
	}
	
	public boolean validRecurrence(){
		return checkRecurrence(this.value);
	}
	
	@Override
	public String execute() {
		prevTask = UI.getLastTaskList().get(taskID);
		try {
			task = (Task) prevTask.clone();
		} catch (CloneNotSupportedException e1) {
			return "unable to edit task";
		}
		switch (field.toLowerCase()) {
		case "title":
			task.setTitle(value);
			break;
		case "desc":
			task.setDescription(value);
			break;
		case "due date":
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				task.setDueDate(dateFormat.parse(value));
			} catch (ParseException e) {
				return "unable to parse due date";
			}
			break;
		case "start time":
			task.setStartTime(Integer.parseInt(value));
			break;
		case "end time":
			task.setEndTime(Integer.parseInt(value));
			break;
		case "recurrence":
			task.setRecurrence(value);
			break;
		default:
			return "Unable to edit task.";
		}
		
		try {
			Magical.storage.deleteTask(prevTask);
			Magical.storage.createTask(task);
		} catch (IOException e) {
			return "unable to edit task";
		}
		
		return "Task edited.";
	}
}
