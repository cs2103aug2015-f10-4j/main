package Commands;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import main.Task;
import main.UI;
import main.GUIModel;
import main.RecurrencePeriod;

public abstract class Command {

	private static final String RECUR_YEARLY = "yearly";
	private static final String RECUR_MONTHLY = "monthly";
	private static final String RECUR_WEEKLY = "weekly";
	private static final String RECUR_NONE = "";
	private static final String RECUR_DAILY = "daily";
	//main variables
	protected String args;
	protected String[] argsArray;
	protected int count;
	protected boolean isFlexi;

	//messaging params
	protected String error = RECUR_NONE;
	protected static final String MESSAGE_HEADER_INVALID = "\n----- Invalid arguments ---- \n";

	public Command(String args){

		this.args = args;

		/*
		isFlexi = !args.contains("/") || !args.replace("\\/", "").contains("/");
		if(!isFlexi){
			this.argsArray = args.split("(?<![\\\\])/", -1);
			for (int i = 0; i < argsArray.length; i++){
				String param = argsArray[i];

				param = param.replaceAll("(?<![\\\\])\\\\", "");
				argsArray[i] = param;

			}

		} else {
			this.argsArray = args.split("(?<=\\s)by(?=\\s)|(?<=\\s)at(?=\\s)", -1);
			for(int i = 0; i < argsArray.length; i++){
				argsArray[i] = argsArray[i].trim().replaceAll("(?<![\\\\])\\\\", "");
			}
		}
		this.count = argsArray.length;

		for(int i = 0; i < count; i++){
			//assertNotNull(argsArray[i]);
		}*/
	}

	protected RecurrencePeriod getRecurrence(String recurrence) {
		String r = recurrence.toLowerCase();
		switch (r) {
			case RECUR_NONE:
				return RecurrencePeriod.NONE;
			case RECUR_DAILY:
				return RecurrencePeriod.DAILY;
			case RECUR_WEEKLY:
				return RecurrencePeriod.WEEKLY;
			case RECUR_MONTHLY:
				return RecurrencePeriod.MONTHLY;
			case RECUR_YEARLY:
				return RecurrencePeriod.YEARLY;
			default:
				return null;
		}
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
					return hour*100 + min;
				}
			} catch(Exception e){
				return -1;
			}
		}
	}

	protected Date getDate(String date)  {
		if(date.matches("^\\d+\\-\\d+\\-\\d+")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
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
		if(dueDate.equals(RECUR_NONE) && startTime.equals(RECUR_NONE) && endTime.equals(RECUR_NONE) && type.equals("task")){
			return true;
		} else {
			return false;
		}
	}

	protected String getTitle(String title) {
		if(title.equals(RECUR_NONE)){
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

	protected Task getTaskByID(String taskID){
		String type = taskID.substring(0, 1);
		Integer index = Integer.parseInt(taskID.substring(1)) - 1;
		if (type.equalsIgnoreCase("t")) {
			return GUIModel.taskList.get(index);
		} else {
			return GUIModel.eventList.get(index);
		}
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

	protected Date addTime(Date date, int time){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, time/100);
		cal.set(Calendar.MINUTE, time%100);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	protected Calendar dateToCal(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

	protected Date flexiParse(Calendar cal) {
		Parser p = new Parser();
		List<DateGroup> date = p.parse((cal.get(Calendar.MONTH)+1)
				+ "-" + cal.get(Calendar.DAY_OF_MONTH)
				+ "-" + cal.get(Calendar.YEAR)
				+ " " + argsArray[2]);
		return date.get(0).getDates().get(0);
	}

	protected Date flexiParse(String dueDate) {
		Parser p = new Parser();
		List<DateGroup> date = p.parse(dueDate);
		return date.get(0).getDates().get(0);
	}

	public abstract String execute();

	public boolean isUndoable(){
		return true;
	}
}
