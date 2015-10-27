package Commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import main.Magical;
import main.RecurrencePeriod;
import main.Task;
import main.UI;

public class EditCommand extends Command{

	private String field;
	private String value;
	private String error = STRING_EMPTY;
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	private static final String MESSAGE_INVALID_FIELD = "Field: %s\n";
	private static final String MESSAGE_ERROR_TITLE = "No Title\n";
	private static final String MESSAGE_ERROR_DATE = "Date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_ERROR_START = "Start time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_ERROR_END = "End time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_ERROR_RECURRENCE = "Recurrence: %s"
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = args.split(" ", 3);
		this.count = argsArray.length;
		
		System.out.println(Arrays.toString(argsArray));
		
		if(validNumArgs()){
			this.task = getTaskByID(argsArray[0].trim());
			this.field = argsArray[1].trim();
			this.value = argsArray[2].trim();
			
			if (task == null) {
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}
			
			if (field.equalsIgnoreCase("title") && (getTitle(value) == null)) {
				error += MESSAGE_ERROR_TITLE;
			} else if (field.equalsIgnoreCase("date") && (getDate(value) == null)) {
				error +=  String.format(MESSAGE_ERROR_DATE, value);
			} else if (field.equalsIgnoreCase("start time") && (getTime(value) == -1)) {
				//NOTE: Shouldn't be able to edit this for tasks
				error += String.format(MESSAGE_ERROR_START, value);
			} else if (field.equalsIgnoreCase("end time") && (getTime(value) == -1)) {
				//NOTE: Shouldn't be able to edit this for floating tasks
				error += String.format(MESSAGE_ERROR_END, value);
			} else if (field.equalsIgnoreCase("recurrence") && (getRecurrence(value) == null)) {
				error += String.format(MESSAGE_ERROR_RECURRENCE, value);
			} else {
				error += String.format(MESSAGE_INVALID_FIELD, field);
			}
			
			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}

	public boolean validNumArgs(){
		if(this.count != 3){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute() {
		prevTask = task;
		try {
			task = prevTask.copy();
		} catch (ClassNotFoundException | IOException e1) {
			return "unable to edit task";
		}
		
		switch (field.toLowerCase()) {
		case "title":
			task.setTitle(value);
			break;
		case "date":
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
			task.setRecurrence(RecurrencePeriod.toRecurrence(value));
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
	
	public static void main(String[] args) throws Exception {
		EditCommand e = new EditCommand("t1 date aihfaf");
	}
}
