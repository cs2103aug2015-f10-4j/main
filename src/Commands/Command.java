package Commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

import gui.GUIModel;
import main.Task;
import main.CustomDate;
import main.RecurrencePeriod;

public abstract class Command {

	/** Recurrence types*/
	private static final String RECUR_YEARLY = "yearly";
	private static final String RECUR_MONTHLY = "monthly";
	private static final String RECUR_WEEKLY = "weekly";
	private static final String RECUR_DAILY = "daily";
	
	protected static final String STRING_EMPTY = "";
	
	/** Main variables */
	protected String args;
	protected ArrayList<String> argsArray;
	protected int count;
	protected boolean isFlexi;

	/** Messaging */
	protected TreeSet<String> invalidArgs = new TreeSet<String>();
	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: ";

	/**
	 * Constructor for Command objects. Stores the arguments passed in when the 
	 * constructor is called.
	 * 
	 * @param args
	 */
	public Command(String args){
		assertNotNull(args);
		this.args = args;
	}

	/**
	 * Method: getRecurrence
	 * Description: Gives the correct recurrence period according to the string provided 
	 * or null if the recurrencePeriod is not valid.
	 *  
	 * @param recurrence
	 * @return RecurrencePeriod for the given string
	 */
	protected RecurrencePeriod getRecurrence(String recurrence) {
		String r = recurrence.toLowerCase();
		switch (r) {
			case STRING_EMPTY:
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

	protected CustomDate getDate(String date)  {
		if(date.trim().equalsIgnoreCase("today")){
			Span span = Chronic.parse(date + " 23:59");
			return new CustomDate(span.getBeginCalendar().getTime());
		} else {
			Pattern time= Pattern.compile("\\D*\\d{4}\\D*");
			Matcher m = time.matcher(date);
			if(m.find()){
				String s = m.group(0);
				date = date.replaceAll(s, s.substring(0, 2)+":"+s.substring(2,s.length()));
			}
			//System.out.println(date);
			Pattern noYear = Pattern.compile("\\D*\\d{2}/\\d{2}\\D*");
			m = noYear.matcher(date);
			if(m.find()){
				String s = m.group(0);
				date = date.replaceAll(s, s.trim() +"/"+new CustomDate(new Date()).getYear()+" ");
			}
			//System.out.println(date);
			date = date.replaceAll("(?<=[0-9]+)\\.(?=[0-9])+", ":");
			//System.out.println(date);
			Span span = Chronic.parse(date);
			if(span == null){
				return null;
			} else {
				return new CustomDate(span.getBeginCalendar().getTime());
			}
		}
	}

	protected boolean checkFloat(String dueDate, String endTime){
		if(dueDate.equals(STRING_EMPTY) && endTime.equals(STRING_EMPTY)){
			return true;
		} else {
			return false;
		}
	}

	protected String getTitle(String title) {
		if(title.equals(STRING_EMPTY)){
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
		Integer index;
		try {
			index = Integer.parseInt(taskID.substring(1)) - 1;
		} catch (Exception e){
			return null;
		}
		if (type.equalsIgnoreCase("t")) {
			return GUIModel.taskList.get(index);
		} else if (type.equalsIgnoreCase("d")) {
			return GUIModel.taskDoneList.get(index);
		} else if (type.equalsIgnoreCase("e")) {
			return GUIModel.eventList.get(index);
		} else if (type.equalsIgnoreCase("p")) {
			return GUIModel.eventDoneList.get(index);
		} else {
			return null;
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

	protected Calendar dateToCal(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

	protected boolean needsFlexi(String date){
		if(date.contains("-")||date.contains("/")){
			return false;
		} else {
			return true;
		}
	}

	protected Date flexiParse(Calendar cal) {
		Parser p = new Parser();
		List<DateGroup> date = p.parse((cal.get(Calendar.MONTH)+1)
				+ "-" + cal.get(Calendar.DAY_OF_MONTH)
				+ "-" + cal.get(Calendar.YEAR)
				+ " " + argsArray.get(2));
		if(date.isEmpty()){
			return null;
		} else {
			return date.get(0).getDates().get(0);
		}
	}

	protected Date flexiParse(String dueDate) {
		Parser p = new Parser();
		List<DateGroup> date = p.parse(dueDate);
		if(date.isEmpty()){
			return null;
		} else {
			return date.get(0).getDates().get(0);
		}
	}

	public abstract String execute() throws Exception;
	public abstract boolean validNumArgs();

	public boolean isUndoable(){
		return true;
	}

	public static void main(String[] args) throws Exception {
		//Command c = new DateCommand("");
		//c.flexiParse("audgsf");
		Span s = Chronic.parse("Today");
		System.out.println(s);
		s = Chronic.parse("today");
		//System.out.println(s);
		//System.out.println(s.getBeginCalendar().getTime());
		//System.out.println(s.getEndCalendar().toString());
		ExitCommand e = new ExitCommand("");
		System.out.println(e.getDate("Today"));
	}
}
