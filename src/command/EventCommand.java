package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class EventCommand extends Command {

	
	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use format: event <title> "
			+ "from <start date> <start time> "
			+ "to <end date> <end time>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End date/time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start date/time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_EVENT_ADDED = "event added";
	private static final String MESSAGE_EVENT_CLASH = ". Another event exists on the same date.";
	private static final String MESSAGE_EVENT_ERROR = "unable to add event";
	
	/** Command parameters **/
	protected String title;
	protected CustomDate dateStart;
	protected CustomDate dateEnd;
	protected int startTime;
	protected int endTime;
	private Item event;

	/**
	 * Constructor for EventCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public EventCommand(String args) throws Exception {
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

			setDefaultEndDay();
			
			checkTitle();

			checkDateTime(dateStart, 0);
			
			checkDateTime(dateEnd, 1);

			checkDateRange();
			
			errorInvalidArgs();

			
		} else {
			errorInvalidFormat();
		}
		
	}

	/** 
	 * Set the default day to start day if unspecified
	 */
	private void setDefaultEndDay() {
		if (dateEnd.getDateString().equals(today.getDateString())) {
			dateEnd.set("day", dateStart.getDay());
			dateEnd.set("month", dateStart.getMonth() - 1);
			dateEnd.set("year", dateStart.getYear());
		}
	}
	
	/**
	 * Throws exception if error messages for format are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
	}

	/**
	 * Throws exception if error messages for invalid arguments are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidArgs() throws IllegalArgumentException {
		if (invalidArgs.size() > 0) {
			throw new IllegalArgumentException(String.format(MESSAGE_HEADER_INVALID, invalidArgs));
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
	 * Adds error message if invalid date and time specified, according to if the date
	 * is the start or end date.
	 */
	private void checkDateTime(CustomDate date, int type) {
		assert(type == 0 || type == 1);
		if (date == null) {
			if(type == 0){
				invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
			} else {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
			}
		}
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
	 * Set the relevant parameters of EventCommand to that of the specified event
	 */
	private void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dateStart = getDate(argsArray.get(1).trim());
		this.dateEnd = getDate(argsArray.get(2).trim());
		assertNotNull(dateStart);
		assertNotNull(dateEnd);
		this.startTime = dateStart.getTime();
		this.endTime = dateEnd.getTime();
	}

	/**
	 * Replaces characters that were used for escaping the keyword argument
	 * that was used for splitting
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(i,
						  argsArray.get(i).trim().replaceAll("(?<=from)\"|\"(?=from)"
						  		+ "(?<=to)\"|\"(?=to)", STRING_EMPTY));
		}
	}

	/**
	 * Check if the end date given is after the start date
	 * @return
	 */
	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) != -1;
	}
	
	/**
	 * Date/time argument might be concatenated with other arguments, thus
	 * the method splits the arguments properly
	 */
	private void splitArgsAfterDateTime() {
		if (argsArray.size() > 1 && argsArray.get(count - 1).contains(" ")) {
			while (true) {
				String last = getLastWord(argsArray.get(count - 1));
				if (getDate(last) != null) {
					break;
				} else {
					splitOnce(last);
				}
			}
		}
	}

	/**
	 * Adds last word to the argsArray and removes it from the date/time argument
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
	 * Checks if the current event to be added clashes with another event
	 * @return
	 */
	private boolean isClashing() {
		ArrayList<Item> events = getEvents();
		for (Item t : events) {
			if (isTimeOverlap(t)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if events overlap
	 * @param t
	 * @return
	 */
	private boolean isTimeOverlap(Item t) {
		return t.getEndDate().equals(event.getEndDate());
	}
	
	/**
	 * get events list from storage
	 * @return
	 */
	private ArrayList<Item> getEvents() {
		ArrayList<Item> events = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		return events;
	}
	
	public String execute() {
		setEventParams();

		try {
			returnMsg = MESSAGE_EVENT_ADDED;
			checkEventClash();
			storeEvent();
			return returnMsg;
		} catch (IOException e) {
			return MESSAGE_EVENT_ERROR;
		} finally {
			updateView();
		}
	}

	/**
	 * Updates the new view in the GUI
	 */
	private void updateView() {
		GUIModel.setEventList(Magical.getStorage().getList(
				Storage.EVENTS_INDEX));
		GUIModel.setEventDoneList(Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX));
		GUIModel.setCurrentTab("events");
	}

	/**
     * Stores the created Item Object as event
	 * 
	 * @throws IOException
	 */
	private void storeEvent() throws IOException {
		Magical.getStorage().create(Storage.EVENTS_INDEX, event);
	}

	/**
	 * Checks if the event to be added clashes with another event and adds to the return
	 * message to inform the user
	 */
	private void checkEventClash() {
		if (isClashing()) {
			returnMsg += MESSAGE_EVENT_CLASH;
		}
	}

	/**
	 * Create an Item object with the correct argument parameters for an event
	 */
	private void setEventParams() {
		event = new Item();
		event.setType("event");
		event.setTitle(title);
		event.setStartDate(dateStart);
		event.setStartTime(startTime);
		event.setEndDate(dateEnd);
		event.setEndTime(endTime);
	}

	public boolean validNumArgs() {
		if (this.count != 3) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isUndoable() {
		return true;
	}
}
