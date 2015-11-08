package command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

import gui.GUIModel;
import main.Item;
import main.CustomDate;

public abstract class Command {

	/** Checking */
	protected static final String STRING_EMPTY = "";
	protected static final CustomDate today = new CustomDate(Chronic
			.parse("today").getEndCalendar().getTime());

	/** Messaging */
	protected static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %s";
	protected TreeSet<String> invalidArgs = new TreeSet<String>();
	protected String returnMsg = STRING_EMPTY;

	/** Main variables */
	protected String args;
	protected ArrayList<String> argsArray;
	protected int count;
	protected boolean isFlexi;

	/**
	 * Constructor for Command objects. Stores the arguments passed in when the
	 * constructor is called.
	 * 
	 * @param args
	 */
	public Command(String args) {
		assertNotNull(args);
		this.args = args;
	}

	/**
	 * Adds the default time of 23:59 to a given string if no time has been
	 * specified in the string originally. Parses the string into a CustomDate
	 * object using jChronic natural date parser. Returns null if date string
	 * cannot be parsed.
	 * 
	 * @param date
	 * @return CustomDate objects with given date and time, default time if not
	 *         specified
	 */
	private CustomDate dateWithTime(String date) {
		assertNotNull(date);
		Span span;
		if (Chronic.parse(date) != null) {
			if ((span = Chronic.parse(date + " 23:59")) != null) {
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
	 * Checks if a given string has date in the format dd/MM or dd-MM and adds
	 * the current year onto the string, for correct parsing. Does nothing if
	 * year is already present in string.
	 * 
	 * @param date
	 * @return String with year
	 */
	private String dateWithYear(String date) {
		assertNotNull(date);
		Matcher m = getMatcher(date,
				"(?<=\\s{0,1})\\d{1,2}(/|-)\\d{1,2}(?=\\s{0,1})(?!(/|-|\\d))");
		assertNotNull(m);

		if (m.find()) {
			String s = m.group(0);
			assertNotNull(s);
			String temp = date.replaceAll(s, s + "/"
					+ new CustomDate(new Date()).getYear());
			if (getDate(temp).compareTo(today) == -1) {
				date = date.replaceAll(s,
						s + "/" + (new CustomDate(new Date()).getYear() + 1));
			} else {
				date = temp;
			}

		}
		return date;
	}

	/**
	 * Implements functionality for each Command subclass.
	 * 
	 * @return String success/failure
	 * @throws Exception
	 */
	public abstract String execute() throws Exception;

	/**
	 * Formats the time in a given string properly for parsing, if present.
	 * 
	 * @param date
	 * @return String with correct time format
	 */
	private String formatCorrectTime(String date) {
		assertNotNull(date);
		Matcher m = getMatcher(date, "(?<=\\s{0,1})(?<!/|-)\\d{4}(?=\\s{1})");
		assertNotNull(m);

		if (m.find()) {
			String s = m.group(0);
			assertNotNull(s);
			date = date.replaceAll(s,
					s.substring(0, 2) + ":" + s.substring(2, s.length()));
		}
		date = date.replaceAll("(?<=[0-9]+)\\.(?=[0-9])+", ":");
		return date;
	}

	/**
	 * Ensure that all correct input are properly formatted for the jChronic
	 * parser
	 * 
	 * @param date
	 * @return
	 */
	String formatDate(String date) {
		date = date.trim();
		System.out.println("Date 1: " + date);
		date = formatCorrectTime(date);
		System.out.println("Date 2 " + date);
		date = dateWithYear(date);
		System.out.println("Date 3: " + date);
		date = swapDayMonth(date);
		System.out.println("Date 4: " + date);
		date = swapDayMonthFlexi(date);
		System.out.println("Date 5: " + date);
		return date;
	}

	/**
	 * Checks if a given string contains a valid date and/or time and parses it
	 * into a CustomDate object, or null if the string is invalid. Uses default
	 * values if a date field or time is not given. Default date is current day,
	 * current month, current year. Default time is 2359.
	 * 
	 * @param date
	 * @return CustomDate object with valid date and time
	 */
	protected CustomDate getDate(String date) {
		assertNotNull(date);
		date = formatDate(date);
		return dateWithTime(date);
	}

	/**
	 * Get both item type and item index using the itemID and return the
	 * corresponding item. Checks the validity of item type. Returns null if the
	 * itemID is invalid or item does not exist.
	 * 
	 * @param itemID
	 * @return Task object corresponding
	 */
	protected Item getItemByID(String itemID) {
		assertNotNull(itemID);
		String type = getItemIdType(itemID);
		Integer index = getItemIdIndex(itemID);
		try {
			if (index != -1) {
				switch (type) {
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
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the index from the itemID and returns it as a number. Returns -1 if
	 * the index is not a valid number.
	 * 
	 * @param itemID
	 * @return int index of the item
	 */
	private int getItemIdIndex(String itemID) {
		assertNotNull(itemID);
		try {
			return Integer.parseInt(itemID.substring(1)) - 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Gets the item type from the itemID. Does not check for validity of type.
	 * 
	 * @param itemID
	 * @return String item type
	 */
	private String getItemIdType(String itemID) {
		assertNotNull(itemID);
		if (itemID.isEmpty()) {
			return null;
		}
		return itemID.substring(0, 1).toLowerCase();
	}

	/**
	 * Creates a matcher using a pattern object with the given regex
	 * 
	 * @param date
	 * @param regex
	 * @return Matcher object with given regex
	 */
	private Matcher getMatcher(String date, String regex) {
		Pattern p = Pattern.compile(regex);
		assertNotNull(p);
		Matcher m = p.matcher(date);
		return m;
	}

	/**
	 * Checks if priority is valid and returns it as integer, or -1 if invalid
	 * priority
	 * 
	 * @param priority
	 * @return String priority
	 */
	protected String getPriority(String priority) {
		assertNotNull(priority);
		if (priority.equals("high") || priority.equals("medium")
				|| priority.equals("low") || priority.equals("")) {
			return priority;
		}
		return null;
	}

	/**
	 * Checks if the title is empty and returns null or the title
	 * 
	 * @param title
	 * @return String title
	 */
	protected String getTitle(String title) {
		assertNotNull(title);
		if (title.equals(STRING_EMPTY)) {
			return null;
		}
		return title;
	}

	/**
	 * Get the token for a date string
	 * 
	 * @param s
	 * @return
	 */
	private String getToken(String s) {
		assert (s.contains("/") | s.contains("-"));
		if (s.contains("/")) {
			return "/";
		} else {
			return "-";
		}
	}

	/**
	 * Indicates if the command can be undone or not
	 * 
	 * @return boolean true/false
	 */
	public abstract boolean isUndoable();

	/**
	 * Create an ArrayList of Strings that is split into arguments according to
	 * the regex and into maximum number of elements specified in the limit.
	 * 
	 * @param args
	 * @param regex
	 * @param limit
	 * @return ArrayList of args
	 */
	protected ArrayList<String> splitArgs(String regex, int limit) {
		args = args.replaceAll("\\s+", " ");
		return new ArrayList<String>(Arrays.asList(args.split(regex, limit)));
	}

	/**
	 * Swaps the day and the month for non-flexi in order to parse properly
	 * 
	 * @param date
	 * @return
	 */
	private String swapDayMonth(String date) {
		Matcher m = getMatcher(date,
				"(?<=\\s{0,1})\\d{1,2}(/|-)\\d{1,2}(?=\\s{0,1}|/)");
		if (m.find()) {
			String s = m.group(0);
			String token = getToken(s);
			String[] splitS = s.split(token, 2);
			String newS = splitS[1] + token + splitS[0];
			date = date.replace(s, newS);
		}
		return date;
	}

	/**
	 * Swaps the day and the month for flexicommands in order to parse properly
	 * 
	 * @param date
	 * @return
	 */
	private String swapDayMonthFlexi(String date) {
		Matcher m = getMatcher(date,
				"(?<=\\s{0,1})\\d{1,2}\\s[A-z]{3,}(?=\\s{0,1})");
		if (m.find()) {
			String s = m.group(0);
			String[] splitS = s.split(" ", 2);
			String newS = splitS[1] + " " + splitS[0];
			date = date.replace(s, newS);
		}
		return date;
	}

	/**
	 * Checks if the correct number of arguments are provided
	 * 
	 * @return boolean true/false
	 */
	protected abstract boolean validNumArgs();
}
