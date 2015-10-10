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
	
	private static final String MESSAGE_ERROR_PARAMS = "Use Format: \nadd type/title/description/due date"
			+ "/start time/end time/recurrence";
	private static final String MESSAGE_ERROR_TYPE = "Type: %s (type should be event or task)\n";
	private static final String MESSAGE_ERROR_TITLE = "No Title" + "\n";
	private static final String MESSAGE_ERROR_DATE = "Due date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_ERROR_START_TASK = "Start time: %s "
			+ "(Task should not have start time)\n";
	private static final String MESSAGE_ERROR_START = "Start time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_ERROR_END = "End time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_ERROR_RECURRENCE = "Recurrence: %s" 
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	private static final String MESSAGE_TASK_ADDED = "task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "unable to add task";
	
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
				error += String.format(MESSAGE_ERROR_TYPE, argsArray[0].trim());
			}
			if (title == null) {
				error += MESSAGE_ERROR_TITLE;
			}
			if (dueDate == null ^ isFloat) {
				error +=  String.format(MESSAGE_ERROR_DATE, argsArray[3].trim());
			}
			if (startTime == -1 ^ isTask) {
				if(isTask){
					error += String.format(MESSAGE_ERROR_START_TASK, argsArray[4].trim()); 
				} else {
					error += String.format(MESSAGE_ERROR_START, argsArray[4].trim());
				}
			}
			if (endTime == -1 ^ isFloat) {
				error += String.format(MESSAGE_ERROR_END, argsArray[5].trim());
			}
			if (recurrence == null) {
				error += String.format(MESSAGE_ERROR_RECURRENCE, argsArray[6].trim()); 
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + MESSAGE_ERROR_PARAMS);
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
			String retMsg = MESSAGE_TASK_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_TASK_CLASH;
			}
			Magical.getStorage().createTask(task);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
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
