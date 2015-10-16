package Commands;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import main.Task;
import main.UI;

public abstract class Command {

	//main variables
	protected String args;
	protected String[] argsArray;
	protected int count;
	
	//messaging params
	protected String error = "";
	protected static final String MESSAGE_HEADER_INVALID = "\n----- Invalid arguments ---- \n"; 

	public Command(String args){
		this.args = args;
		this.argsArray = args.split("(?<![\\\\])/", -1);
		for (int i = 0; i < argsArray.length; i++){
			String param = argsArray[i];
			
			param = param.replaceAll("(?<![\\\\])\\\\", "");
			argsArray[i] = param;
			
		}
		this.count = argsArray.length;
		for(int i = 0; i < count; i++){
			assertNotNull(argsArray[i]);
		}
	}
	
	protected String getRecurrence(String recurrence) {
		if(recurrence.equals("")){
			return recurrence;
		}
		String r = recurrence.toLowerCase();
		if (!r.equals("daily") 
				&& !r.equals("weekly")
				&& !r.equals("monthly")
				&& !r.equals("yearly")) {
			return null;
		}
		return recurrence;
	}

	protected int getTime(String time) {
		if(time.length() != 4){
			return -1;
		} else {
			try {
				int hour = Integer.parseInt(time.substring(0, 2));
				int min = Integer.parseInt(time.substring(2, 4));
				
				if(hour > 23 || hour < 0 || min > 59 || min < 0){
					return -1;
				} else {
					return hour*1000 + min;
				}
			} catch(Exception e){
				return -1;
			}
		}
	}

	protected Date getDate(String date)  {
		if(date.matches("^\\d+\\-\\d+\\-\\d+")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			try {
				return dateFormat.parse(date);
			} catch (Exception e){
				return null;
			}
		} else {
			return null;
		}
	}
	
	protected boolean checkFloat(String dueDate, String startTime, String endTime, String type){
		if(dueDate.equals("") && startTime.equals("") && endTime.equals("") && type.equals("task")){
			return true;
		} else {
			return false;
		}
	}
	
	protected String getTitle(String title) {
		if(title.equals("")){
			return null;
		}
		return title;
	}

	protected String getType(String type){
		if(!type.toLowerCase().equals("event") 
			&& !type.toLowerCase().equals("task")){
			return null;
		}
		return type;
	}
	protected Task getTaskID(String taskID){
		return UI.getLastTaskList().get(taskID);
	}
	
	protected int getPriority(String priority){
		try {
			int p = Integer.parseInt(priority);
			if (p >= 0 && p <= 10){
				return p;
			} else {
				return -1;
			}
		} catch (Exception e){
			return -1;
		}
	}
	
	public abstract String execute();
	
	public boolean isUndoable(){
		return true;
	}
}
