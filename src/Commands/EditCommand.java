package Commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import main.CustomDate;
import main.Magical;
import main.RecurrencePeriod;
import main.Storage;
import main.Task;
import gui.GUIModel;

public class EditCommand extends Command{

	private String field;
	private String value;
	private Task task;
	private Task prevTask;
	private boolean toFloat;
	private boolean isTask;
	private Object editObject;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: edit <task_id> <field> <value>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	private static final String MESSAGE_INVALID_FIELD = "Field: %s\n";
	private static final String MESSAGE_INVALID_TITLE = "No Title\n";
	private static final String MESSAGE_INVALID_DATE = "Date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_TIME = "%s time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_RECURRENCE = "Recurrence: %s"
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	private static final String MESSAGE_INVALID_DATE_FLEXI = "Date: %s (Invalid flexi date)\n";
	private static final String MESSAGE_INVALID_TIME_FLEXI = "%s time: %s (Invalid flexi time)\n";
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		args = args + " ";
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split("(?<!end|start)\\s(?!time)", 3)));
		this.count = argsArray.size();
		for(int i = 0; i < argsArray.size(); i++){
			argsArray.set(i, argsArray.get(i).trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY));
		}
		System.out.println(argsArray);
		if(validNumArgs()){
			this.task = getTaskByID(argsArray.get(0).trim());
			this.field = argsArray.get(1).trim();
			this.value = argsArray.get(2).trim();
			
			if (task == null) {
				invalidArgs.add("taskID");
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID + String.join(", ", invalidArgs));
			}
			
			isTask = task.getType().equals("task");
			if (field.equalsIgnoreCase("title")) {
				if (getTitle(value) == null){
					invalidArgs.add("title");
				}
			} else if (field.equalsIgnoreCase("date")) {
				if(value.equals(STRING_EMPTY) && isTask){
					toFloat = true;
				} else if((editObject = getDate(value)) == null){
					invalidArgs.add("date");
				}
			} else if (field.equalsIgnoreCase("start time")) {
				if(isTask){
					invalidArgs.add(MESSAGE_INVALID_TASK_START);
				} else {
					if((editObject = getDate(value)) == null){
						invalidArgs.add("start time");
					} else {
						editObject = getDate(value).getTime();
					}
				}
				
				/*
				try{
					Integer.parseInt(value);
					if((editObject = getTime(value)).equals(-1)){
						error += String.format(MESSAGE_INVALID_TIME, "Start", value);
					}
				} catch (NumberFormatException e){
					//Note: If you enter flexi time like tomorrow, 12pm if the default time
					if((editObject = getDate(value)) == null){
						error += String.format(MESSAGE_INVALID_TIME_FLEXI, "Start", value);
					} else{
						editObject = getDate(value).getTime();
					}
				}*/

			} else if (field.equalsIgnoreCase("end time")) {
				if((editObject = getDate(value)) == null){
					invalidArgs.add("end time");
				} else {
					editObject = getDate(value).getTime();
				}
				/*
				try{
					Integer.parseInt(value);
					if((editObject = getTime(value)).equals(-1)){
						error += String.format(MESSAGE_INVALID_TIME, "End", value);
					}
				} catch (NumberFormatException e){
					if((editObject = flexiParse(value)) == null){
						error += String.format(MESSAGE_INVALID_TIME_FLEXI, "End", value);
					} else {
						Calendar c = Calendar.getInstance();
						c.setTime((Date) editObject);
						editObject = c.get(Calendar.HOUR_OF_DAY)*100 + c.get(Calendar.MINUTE);
						System.out.println(editObject);
					}
				}
				*/
			} else if (field.equalsIgnoreCase("recurrence")) {
				if(getRecurrence(value) == null){
					invalidArgs.add("recurrence");
				}
			} else {
				invalidArgs.add(MESSAGE_INVALID_FIELD);
			}
			
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID + String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
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
			if(toFloat){
				//change to float
				task.setEndDate(null);
				task.setEndTime(-1);
			} else {
				//unfloating the task
				if(task.getEndTime() == -1){
					task.setEndTime(0000);
				}
				
				CustomDate date = (CustomDate) editObject;
				date.setTime(task.getEndTime());
						
				task.setEndDate(date);
			}
			break;
		case "start time":
			task.setStartTime((int) editObject);
			break;
		case "end time":
			task.setEndTime((int) editObject);
			
			CustomDate date = null;
			//unfloating the task
			if(task.getEndDate() == null){
				date = getDate("today");
			//normal changing of date object
			} else {
				date = task.getEndDate();
			}
			assertNotNull(date);
			date.setTime(task.getEndTime());
			task.setEndDate(date);
			break;
		case "recurrence":
			task.setRecurrence(RecurrencePeriod.toRecurrence(value));
			break;
		default:
			return "Unable to edit task.";
		}
		
		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.update(listIndex, prevTask, task);
		} catch (IOException e) {
			return "unable to edit task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage.getList(Storage.EVENTS_DONE_INDEX));
		}
		
		return "Task edited.";
	}
	
	public static void main(String[] args) throws Exception {
		//EditCommand e = new EditCommand("t1 end time asfas");
		//EditCommand e1 = new EditCommand("t1 start time asfas");
	}
}
