package Commands;

import static org.junit.Assert.*;
import com.joestelmach.natty.*;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
	protected boolean isFloat;
	protected boolean isTask;
	private Task task;
	
	private static final String MESSAGE_ERROR_PARAMS = "Number of Arguments\n"
			+ "Use Format: \nadd type/title/description/due date"
			+ "/start time/end time/recurrence";
	private static final String MESSAGE_ERROR_FLEXI = "Use format: add <title> by <date> at <time>";
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
		
		if(!isFlexi){
			if(validNumArgs()){
				
				this.type = getType(argsArray[0].trim());
				this.title = getTitle(argsArray[1].trim());
				this.desc = argsArray[2].trim();
				this.dueDate = getDate(argsArray[3].trim());
				this.startTime = getTime(argsArray[4].trim());
				this.endTime = getTime(argsArray[5].trim());
				this.recurrence = getRecurrence(argsArray[6].trim()); 
			
				isFloat = checkFloat(argsArray[3].trim(), argsArray[4].trim(),
						 argsArray[5].trim(), argsArray[0].trim());
				isTask = type == null ? false : type.equals("task");
				
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
				
				assertNotNull(type);
				if(type.equals("event")){
					dueDate = addTime(dueDate, startTime);
				} else if(type.equals("task") && !isFloat){
					dueDate = addTime(dueDate, endTime);
				}
			} else {
				error += MESSAGE_ERROR_PARAMS;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			if(!argsArray[0].equals("") && argsArray.length <= 3){
				//System.out.println(Arrays.toString(argsArray));
				this.type = "task";
				isTask = true;
				this.title = argsArray[0];
				this.desc = "";
				this.recurrence = "";
				this.startTime = -1;
				if(argsArray.length == 1){
					this.dueDate = null;
					this.endTime = -1;
					isFloat = true;
				} else {
					this.dueDate = getDate(argsArray[1]);
					if(dueDate == null){
						if(argsArray.length == 2){
							dueDate = flexiParse(argsArray[1]);
						} else {
							dueDate = flexiParse(argsArray[1] + " " + argsArray[2]);
						}
					} else if(argsArray.length == 3){
						Calendar cal = dateToCal(dueDate);
						dueDate = flexiParse(cal);
					}
					Calendar cal = dateToCal(dueDate);
					this.endTime = cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
				}
			} else {
				error += MESSAGE_ERROR_FLEXI;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
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
	
	public static void main(String[] args) throws Exception {
		//AddCommand a = new AddCommand("wash my butt by 1st January at 12pm");
		//AddCommand b = new AddCommand("pass \\by the river \\at St.George by 1st January at 12pm");
		//AddCommand c = new AddCommand("smack him by 12-01-1993 at 1pm");
		//AddCommand d = new AddCommand("");
		AddCommand e = new AddCommand("eat chocolate"); 
	}
}
