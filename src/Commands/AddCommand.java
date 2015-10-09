package Commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import main.Magical;
import main.Task;

public class AddCommand extends Command{

	// Command params
	protected String type;
	protected String title;
	protected String desc;
	protected Date dueDate;
	protected int startTime;
	protected int endTime;
	protected String recurrence;
	private Task task;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "\nadd type/title/description/due date"
			+ "/start time/end time/recurrence";
	
	public AddCommand(String args) throws Exception {
		super(args);
		
		if(validNumArgs()){
			this.type = argsArray[0].trim();
			boolean isFloat = checkFloat(argsArray[3].trim(), argsArray[4].trim(),
										 argsArray[5].trim(), argsArray[0].trim());
			this.title = argsArray[1].trim();
			this.desc = argsArray[2].trim();
			this.dueDate = isFloat ? null : getDate(argsArray[3].trim());
			this.startTime = isFloat ? -1 : getTime(argsArray[4].trim());
			this.endTime = isFloat ? -1 : getTime(argsArray[5].trim());
			this.recurrence = getRecurrence(argsArray[6].trim());
			/*
			if(argsArray.length == 6){
				recurrence = null;
			} else {
				recurrence = argsArray[6].trim();
			}*/		 
		
			if (!validType()) {
				error += "Type: " + type + "\n";
			}
			if (!validTitle()) {
				error += "No Title" + "\n";
			}
			if (dueDate == null && !isFloat) {
				error += "Due date: " + argsArray[3].trim() + "\n";
			}
			if (startTime == -1 && !isFloat && !type.equals("task")) {
				error += "Start time: " + argsArray[4].trim() + "\n";
			}
			if (endTime == -1 && !isFloat) {
				error += "End time: " + argsArray[5].trim() + "\n";
			}
			if (recurrence == null) {
				error += "Recurrence: " + argsArray[6].trim() + "\n";
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}
	
	public boolean validNumArgs(){
		if(this.count != 7){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validType(){
		return checkType(this.type);
	}
	
	public boolean validTitle(){
		return checkTitle(this.title);
	}
	
	@Override
	public String execute() {
		task = new Task();
		task.setType(type);
		task.setTitle(title);
		task.setDescription(desc);
		task.setRecurrence(recurrence);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		task.setDueDate(dueDate);
		task.setStartTime(startTime);
		task.setEndTime(endTime);
		
		try {
			String retMsg = "task added";
			if (isClashing()) {
				retMsg += ". Another task exists on the same date.";
			}
			Magical.getStorage().createTask(task);
			return retMsg;
		} catch (IOException e) {
			return "unable to add task";
		}
	}

	private boolean isClashing() {
		ArrayList<Task> tasks = Magical.getStorage().getTasks();
		for (Task t : tasks) {
			if (t.getDueDate().equals(task.getDueDate())) {
				return true;
			}
		}
		return false;
	}
}
