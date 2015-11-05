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
	 * Description: Checks if the string is a valid recurrence and returns the corresponding
	 * RecurrencePeriod, or null if the string is not valid
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

	/**
	 * Method: CustomDate
	 * Description: Checks if a given string contains a valid date and/or time and parses it
	 * into a CustomDate object, or null if the string is invalid. Uses default values if 
	 * a date field or time is not given. Default date is current day, current month, current
	 * year. Default time is 2359.
	 * 
	 * @param date
	 * @return CustomDate object with valid date and time 
	 */
	protected CustomDate getDate(String date)  {
		date = formatCorrectTime(date);
		date = dateWithYear(date);
		return dateWithTime(date);
	}

	/**
	 * Method: formatCorrectTime
	 * Description: Formats the time in a given string properly for parsing, if present. 
	 * 
	 * @param date
	 * @return String with correct time format
	 */
	private String formatCorrectTime(String date) {
		Pattern time= Pattern.compile("\\D*\\d{4}\\D*");
		Matcher m = time.matcher(date);
		if(m.find()){
			String s = m.group(0);
			date = date.replaceAll(s, s.substring(0, 2) + ":" + s.substring(2,s.length()));
		}
		date = date.replaceAll("(?<=[0-9]+)\\.(?=[0-9])+", ":");
		return date;
	}
	
	/**
	 * Method: dateWithYear
	 * Description: Checks if a given string has date in the format dd/MM or dd-MM and adds 
	 * the current year onto the string, for correct parsing. Does nothing if year is already 
	 * present in string.
	 * 
	 * @param date
	 * @return String with year
	 */
	private String dateWithYear(String date) {
		Pattern noYear = Pattern.compile("\\D*\\d{2}(/|-)\\d{2}\\D*");
		Matcher m;
		m = noYear.matcher(date);
		if(m.find()){
			String s = m.group(0);
			date = date.replaceAll(s, s.trim() + "/" + new CustomDate(new Date()).getYear() + " ");
		}
		return date;
	}

	/**
	 * Method: dateWithTime
	 * Description: Adds the default time of 23:59 to a given string if no time has been 
	 * specified in the string originally. Parses the string into a CustomDate object
	 * using jChronic natural date parser. Returns null if date string cannot be parsed.
	 * 
	 * @param date
	 * @return CustomDate objects with given date and time, default time if not specified
	 */
	private CustomDate dateWithTime(String date) {
		Span span;
		if(Chronic.parse(date) != null ){
			if((span = Chronic.parse(date + " 23:59")) != null){
				return new CustomDate(span.getBeginCalendar().getTime());
			} else {
				span = Chronic.parse(date);
				return new CustomDate(span.getBeginCalendar().getTime());
			}
		} else {
			return null;
		}
	}

	/**
	 * Method: getTitle
	 * Description: Checks if the title is empty and returns null or the title 
	 * 
	 * @param title
	 * @return String title
	 */
	protected String getTitle(String title) {
		if(title.equals(STRING_EMPTY)){
			return null;
		}
		return title;
	}

	/**
	 * Method: getItemByID
	 * Description: Get both item type and item index using the itemID and return the 
	 * corresponding item. Returns null if the itemID is invalid or item does not exist.
	 *  
	 * @param itemID
	 * @return Task object corresponding
	 */
	protected Task getItemByID(String itemID){
		String type = getItemID(itemID);
		Integer index = getItemIdIndex(itemID);
		
		if(index != -1){
			switch(type){
				case "t":
					return GUIModel.taskList.get(index);
				case "d":
					return GUIModel.taskDoneList.get(index);
				case "e":
					return GUIModel.eventList.get(index);
				case "p":
					return GUIModel.eventDoneList.get(index);
				default:
					return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Method: getItemIdIndex
	 * Description: Gets the index from the itemID and returns it as a number.
	 * Returns -1 if the index is not a valid number.
	 * 
	 * @param itemID
	 * @return Integer index of the item
	 */
	private Integer getItemIdIndex(String itemID) {
		try {
			return Integer.parseInt(itemID.substring(1)) - 1;
		} catch (Exception e){
			return -1;
		}
	}

	/**
	 * Method: getItemID
	 * @param itemID
	 * @return
	 */
	private String getItemID(String itemID) {
		return itemID.substring(0, 1).toLowerCase();
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
		Span s = Chronic.parse("00om");
		System.out.println(s);
		s = Chronic.parse("today");
		//System.out.println(s);
		//System.out.println(s.getBeginCalendar().getTime());
		//System.out.println(s.getEndCalendar().toString());
		ExitCommand e = new ExitCommand("");
		System.out.println(e.getDate(""));
	}
}
