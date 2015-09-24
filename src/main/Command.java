package main;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Command {

	protected String args;
	protected int count;

	public Command(String args){
		this.args = args;
		this.count = args.length() - args.replace("/", "").length();
	}
	
	protected boolean checkRecurrence(String recurrence) {
		if(recurrence.equals(null)){
			return true;
		}
		String r = recurrence.toLowerCase();
		if (!r.equals("daily") 
				&& !r.equals("weekly")
				&& !r.equals("monthly")
				&& !r.equals("yearly")) {
			return false;
		}
		return true;
	}

	protected boolean checkTime(String time) {
		if(time.length() != 4){
			return false;
		} else {
			int hour = Integer.parseInt(time.substring(0, 2));
			int min = Integer.parseInt(time.substring(2, 4));
			
			if(hour > 23 || hour < 0
				|| min > 59 || min < 0){
				return false;
			}
		}
		return true;
	}

	protected boolean checkDueDate(String dueDate, String type)  {
		if(dueDate.equals("") && type.equals("task")){
			return true;
		}
		if(dueDate.matches("^\\d+\\-\\d+\\-\\d+")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(dueDate);
			} catch (Exception e){
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	protected boolean checkTitle(String title) {
		if(title.equals("")){
			return false;
		}
		return true;
	}

	protected boolean checkType(String type){
		if(!type.toLowerCase().equals("event") 
			&& !type.toLowerCase().equals("task")){
			return false;
		}
		return true;
	}
	
	public HashMap<String, String> getArgs(){
		return null;
	}
	
}