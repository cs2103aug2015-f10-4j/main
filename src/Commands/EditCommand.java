package Commands;

import java.io.IOException;
import java.util.Calendar;

import main.GUIModel;
import main.Magical;
import main.RecurrencePeriod;
import main.Storage;
import main.Task;

public class EditCommand extends Command{

	private String field;
	private String value;
	private String error = STRING_EMPTY;
	private Task task;
	private Task prevTask;
	private boolean toFloat;
	private boolean isTask;
	
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
	private static final String MESSAGE_INVALID_TASK_START = "Task cannot have start time";
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		args = args + " ";
		this.argsArray = args.split("(?<!end|start)\\s(?!time)", 3);
		this.count = argsArray.length;
		for(int i = 0; i < argsArray.length; i++){
			argsArray[i] = argsArray[i].trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
		}
		//System.out.println(Arrays.toString(argsArray));

		if(validNumArgs()){
			this.task = getTaskByID(argsArray[0].trim());
			this.field = argsArray[1].trim();
			this.value = argsArray[2].trim();
			
			if (task == null) {
				error += String.format(MESSAGE_INVALID_ID, argsArray[0].trim());
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
			
			isTask = task.getType().equals("task");
			if (field.equalsIgnoreCase("title")) {
				if (getTitle(value) == null){
					error += MESSAGE_INVALID_TITLE;
				}
			} else if (field.equalsIgnoreCase("date")) {
				if(value.equals("") && isTask){
					toFloat = true;
				} //else if (getDate(value) == null){
					//error +=  String.format(MESSAGE_INVALID_DATE, value);
					//flexi commands may be null but also supports rubbish though
				//}
			} else if (field.equalsIgnoreCase("start time")) {
				if(isTask){
					error += MESSAGE_INVALID_TASK_START;
				} else if (value.equals(STRING_EMPTY)){
					error += String.format(MESSAGE_INVALID_START, value);
				}
				//else if(getTime(value) == -1){
					//error += String.format(MESSAGE_INVALID_START, value);
				//}
			} else if (field.equalsIgnoreCase("end time")) {
				if (value.equals(STRING_EMPTY)){
					error += String.format(MESSAGE_INVALID_END, value);
				}
				//if(getTime(value) == -1){
					//error += String.format(MESSAGE_INVALID_END, value);
				//}
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
			if(toFloat){
				task.setDueDate(null);
				task.setEndTime(-1);
			} else {
				if(task.getEndTime() == -1){
					task.setEndTime(0000);
				}
				if(getDate(value) == null){
					task.setDueDate(addTime(flexiParse(value), task.getEndTime()));
				} else {
					task.setDueDate(addTime(getDate(value), task.getEndTime()));
				}
			}
			break;
		case "start time":
			if(getTime(value) == -1){
				Calendar c = Calendar.getInstance();
				c.setTime(flexiParse(value));
				task.setStartTime(c.get(Calendar.HOUR_OF_DAY)*100 + c.get(Calendar.MINUTE));
			} else {
				task.setStartTime(getTime(value));
			}
			break;
		case "end time":
			if(getTime(value) == -1){
				Calendar c = Calendar.getInstance();
				System.out.println(value);
				c.setTime(flexiParse(value));
				task.setEndTime(c.get(Calendar.HOUR_OF_DAY)*100 + c.get(Calendar.MINUTE));
			} else {
				task.setEndTime(getTime(value));
			}
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
			int listIndex = Storage.getListIndex(argsArray[0]);
			Magical.storage.delete(listIndex, prevTask);
			Magical.storage.create(listIndex, task);
		} catch (IOException e) {
			return "unable to edit task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}
		
		return "Task edited.";
	}
	
	public static void main(String[] args) throws Exception {
//		EditCommand e = new EditCommand("t1 end time asfas");
//		EditCommand e1 = new EditCommand("t1 start time asfas");
	}
}
