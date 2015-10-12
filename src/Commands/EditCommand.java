package Commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import main.Magical;
import main.Task;
import main.UI;

public class EditCommand extends Command{

	private Task taskID;
	private String field;
	private String value;
	private String error = "";
	private Task task;
	private Task prevTask;
	
	private static final String MESSAGE_ARGUMENT_PARAMS = "edit task_id/field/value";
	
	public EditCommand(String args) throws Exception {
		super(args);
		
		if(validNumArgs()){
			this.taskID = getTaskID(argsArray[0].trim());
			this.field = argsArray[1].trim();
			this.value = argsArray[2].trim();
			
			if (taskID == null) {
				error += "Task ID: " + taskID + "\n";
			}
		
			if (field.equalsIgnoreCase("title") && (getTitle(value) != null)) {
				error += "No Title" + "\n";
			}
			else if (field.equalsIgnoreCase("due date") && (getDate(value) != null)) {
				error += "Due date: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("start time") && (getTime(value) != -1)) {
				error += "Start time: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("end time") && (getTime(value) != -1)) {
				error += "End time: " + value + "\n";
			}
			else if (field.equalsIgnoreCase("recurrence") && (getRecurrence(value) != null)) {
				error += "Recurrence: " + value + "\n";
			}
//			else {
//				error += "Invalid field.\n";
//			}
			if (!error.equals("")) {
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
