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

	/** Command parameters **/
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
			this.type = getType(argsArray[0].trim());
			this.title = getTitle(argsArray[1].trim());
			this.desc = argsArray[2].trim();
			this.dueDate = getDate(argsArray[3].trim());
			this.startTime = getTime(argsArray[4].trim());
			this.endTime = getTime(argsArray[5].trim());
			this.recurrence = getRecurrence(argsArray[6].trim()); 
		
			boolean isFloat = checkFloat(argsArray[3].trim(), argsArray[4].trim(),
					 argsArray[5].trim(), argsArray[0].trim());
			boolean isTask = type == null ? false : type.equals("task");
			
			if (type == null) {
				error += "Type: " + argsArray[0].trim() + " (type should be event or task)\n";
			}
			if (title == null) {
				error += "No Title" + "\n";
			}
			if (dueDate == null ^ isFloat) {
				error += "Due date: " + argsArray[3].trim() + " (Date should be dd-MM-yyyy)\n";
			}
			if (startTime == -1 ^ isTask) {
				if(isTask){
					error += "Start time: " + argsArray[4].trim() + " (Task should not have start time)\n";
				} else {
					error += "Start time: " + argsArray[4].trim() + " (Time should be in 24hrs format)\n";
				}
			}
			if (endTime == -1 ^ isFloat) {
				error += "End time: " + argsArray[5].trim() + " (Time should be in 24hrs format)\n";
			}
			if (recurrence == null) {
				error += "Recurrence: " + argsArray[6].trim() 
						+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
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
