package parser;

import static org.junit.Assert.*;

import command.Command;
import command.EventCommand;
import main.CustomDate;
import main.Item;

public class EventParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use format: event <title> "
			+ "from <start date> <start time> " + "to <end date> <end time>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End date/time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start date/time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	private static final String MESSAGE_INVALID_TITLE = "Title";

	/** For checking **/
	private static final int START = 0;
	private static final int END = 1;
	
	/** Command parameters **/
	protected String title;
	protected CustomDate dateStart;
	protected CustomDate dateEnd;
	protected int startTime;
	protected int endTime;
	
	/**
	 * Constructor for EventCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to add an event to storage.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public EventParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs("\\sto\\s|\\sfrom\\s", -1);
		removeEscapeCharacters();
		this.count = argsArray.size();
		splitArgsAfterDateTime();
		this.count = argsArray.size();

		for (int i = 0; i < count; i++) {
			assertNotNull(argsArray.get(i));
		}

		if (validNumArgs()) {
			setProperParams();
			System.out.println(dateStart);
			System.out.println(dateEnd);

			setDefaultEndDay();
			
			checkTitle();
			
			checkDateTime(dateStart, START);
			checkDateTime(dateEnd, END);
			
			checkDateRange();
			
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Replaces characters that were used for escaping the keyword argument that
	 * was used for splitting
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(i,
					argsArray
							.get(i)
							.trim()
							.replaceAll(
									"(?<=from)\"|\"(?=from)"
											+ "(?<=to)\"|\"(?=to)",
									STRING_EMPTY));
		}
	}
	
	/**
	 * Date/time argument might be concatenated with other arguments, thus the
	 * method splits the arguments properly
	 */
	private void splitArgsAfterDateTime() {
		if (argsArray.size() > 1 && argsArray.get(count - 1).contains(" ")) {
			while (true) {
				String last = getLastWord(argsArray.get(count - 1));
				if (getDate(last) != null) {
					break;
				} else if(!last.matches(".*\\d+.*")){
					splitOnce(last);
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * Gives last word of a string
	 * 
	 * @param string
	 * @return String last word
	 */
	private String getLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[1];
	}
	
	/**
	 * Adds last word to the argsArray and removes it from the date/time
	 * argument
	 * 
	 * @param last
	 */
	private void splitOnce(String last) {
		argsArray.add(count, last);
		argsArray.set(count - 1, removeLastWord(argsArray.get(count - 1)));
	}
	
	/**
	 * Removes last word from a string
	 * 
	 * @param string
	 * @return String with last word removed
	 */
	private String removeLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[0];
	}
	
	void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dateStart = getDate(argsArray.get(1).trim());
		this.dateEnd = getDate(argsArray.get(2).trim());
		this.startTime = dateStart == null ? -1 : dateStart.getTime();
		this.endTime = dateEnd == null ? -1 : dateEnd.getTime();
	}
	
	/**
	 * Set the default day to be start day if unspecified
	 */
	private void setDefaultEndDay() {
		if(dateEnd != null){
			if (dateEnd.getDateString().equals(today.getDateString())) {
				if(endIsTimeOnly()){
					setEndFollowingStart();
				}
			}
		}
	}
	
	/**
	 * Check if only time was given for end date. Parsing with another date other than
	 * default would give a customDate object, else null if not only time was given.
	 * @return
	 */
	private boolean endIsTimeOnly() {
		CustomDate temp = getDate("tomorrow " + argsArray.get(2).trim());
		return temp != null;
	}
	
	/**
	 * Set the date month and year according to that of the start day. Time
	 * may still be different
	 */
	private void setEndFollowingStart() {
		dateEnd.setDay(dateStart.getDay());
		dateEnd.setMonth(dateStart.getMonth());
		dateEnd.setYear(dateStart.getYear());
	}
	
	/**
	 * Adds error message if title is invalid
	 */
	private void checkTitle() {
		if (title == null) {
			invalidArgs.add(MESSAGE_INVALID_TITLE);
		}
	}
	
	/**
	 * Adds error message if invalid date and time specified, according to if
	 * the date is the start or end date.
	 */
	private void checkDateTime(CustomDate date, int type) {
		assert (type == START || type == END);
		if (date == null) {
			addInvalidDateTimeType(type);
		}
	}
	
	/**
	 * Adds the proper invalid message to error message
	 * @param type
	 */
	private void addInvalidDateTimeType(int type) {
		if (type == START) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
		} else {
			invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
		}
	}
	
	/**
	 * Adds error message if end date is before start date
	 */
	private void checkDateRange() {
		if (dateStart != null && dateEnd != null && !validDateRange()) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME_RANGE);
		}
	}
	
	/**
	 * Check if the end date given is after the start date
	 * 
	 * @return
	 */
	private boolean validDateRange() {
		return dateEnd.compareTo(dateStart) > 0;
	}
	
	boolean validNumArgs() {
		if (this.count != 3) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command event = new EventCommand(title, dateStart, dateEnd, startTime, endTime);
		return event;
	}
	
	public static void main(String[] args) throws Exception {
		EventParser e = new EventParser("fuck this shit from 5/11/15 2pm to 3pm");
	}
}
