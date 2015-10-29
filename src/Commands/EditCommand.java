package Commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import main.GUIModel;
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
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	private static final String MESSAGE_INVALID_FIELD = "Field: %s\n";
	private static final String MESSAGE_INVALID_TITLE = "No Title\n";
	private static final String MESSAGE_INVALID_DATE = "Date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_START = "Start time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_END = "End time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_RECURRENCE = "Recurrence: %s"
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = args.split("(?<!end|start)\\s(?!time)", 3);
		this.count = argsArray.length;
		
		if(validNumArgs()){
			this.task = getTaskByID(argsArray[0].trim());
			this.field = argsArray[1].trim();
			this.value = argsArray[2].trim();
			
			if (task == null) {
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
			}
			
			if (field.equalsIgnoreCase("title")) {
				if (getTitle(value) == null){
					error += MESSAGE_INVALID_TITLE;
				}
			} else if (field.equalsIgnoreCase("date")) {
				if (getDate(value) == null){
					error +=  String.format(MESSAGE_INVALID_DATE, value);
				}
			} else if (field.equalsIgnoreCase("start time")) {
				if(getTime(value) == -1){
					error += String.format(MESSAGE_INVALID_START, value);
				}
				//NOTE: Shouldn't be able to edit this for tasks
			} else if (field.equalsIgnoreCase("end time")) {
				if(getTime(value) == -1){
					error += String.format(MESSAGE_INVALID_END, value);
				}
				//NOTE: Shouldn't be able to edit this for floating tasks
			} else if (field.equalsIgnoreCase("recurrence")) {
				if(getRecurrence(value) == null){
					error += String.format(MESSAGE_INVALID_RECURRENCE, value);
				}
			} else {
				error += String.format(MESSAGE_INVALID_FIELD, field);
			}
			
			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
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
		task = prevTask.copy();
		
		switch (field.toLowerCase()) {
		case "title":
			task.setTitle(value);
			break;
		case "date":
			if(task.getEndTime() == -1){
				task.setEndTime(0000);
			}
			task.setDueDate(addTime(getDate(value), task.getEndTime()));
			break;
		case "start time":
			task.setStartTime(Integer.parseInt(value));
			break;
		case "end time":
			task.setEndTime(Integer.parseInt(value));
			if(task.getDueDate() == null){
				task.setDueDate(addTime(flexiParse("today"), task.getEndTime()));
			} else {
				task.setDueDate(addTime(task.getDueDate(), task.getEndTime()));
			}
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
		} finally {
			GUIModel.setTaskList(Magical.storage.getTasks());
			GUIModel.setDoneList(Magical.storage.getTasksDone());
		}
		
		return "Task edited.";
	}
	
	public static void main(String[] args) throws Exception {
		//EditCommand e = new EditCommand("t1 end time asfas");
		EditCommand e1 = new EditCommand("t1 start time asfas");
	}
}
