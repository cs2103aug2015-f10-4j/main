package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import gui.GUIModel;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class EventCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use format: event <title> "
			+ "from <start date> <start time> " + "to <end date> <end time>";
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
	 * Constructor for EventCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to add an event to storage.
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
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
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
	 * Adds error message if invalid date and time specified, according to if
	 * the date is the start or end date.
	 */
	private void checkDateTime(CustomDate date, int type) {
		assert (type == 0 || type == 1);
		if (date == null) {
			if (type == 0) {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
			} else {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
			}
		}
	}

	/**
	 * Checks if the event to be added clashes with another event and adds to
	 * the return message to inform the user
	 */
	private void checkEventClash() {
		if (isClashing()) {
			returnMsg += MESSAGE_EVENT_CLASH;
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
	 * Adds a new event to the storage using the parameters stored
	 * 
	 * @return message to show user
	 */
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
			GUIModel.setCurrentTab("events");
		}
	}

	/**
	 * get events list from storage
	 * 
	 * @return
	 */
	private ArrayList<Item> getEvents() {
		ArrayList<Item> events = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		return events;
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
	 * 
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
	 * 
	 * @param t
	 * @return
	 */
	private boolean isTimeOverlap(Item t) {
		return t.getEndDate().equals(event.getEndDate());
	}

	public boolean isUndoable() {
		return true;
	}

	/**
	 * Replaces characters that were used for escaping the keyword argument that
	 * was used for splitting
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(
					i,
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
	 * Removes last word from a string
	 * 
	 * @param string
	 * @return String with last word removed
	 */
	private String removeLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[0];
	}

	/**
	 * Set the default day to be start day if unspecified
	 */
	private void setDefaultEndDay() {
		if(dateEnd != null){
			if (dateEnd.getDateString().equals(today.getDateString())
					&& !(dateStart.compareTo(today) < 0)) {
				dateEnd.setDay(dateStart.getDay());
				dateEnd.setMonth(dateStart.getMonth());
				dateEnd.setYear(dateStart.getYear());
			}
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

	void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dateStart = getDate(argsArray.get(1).trim());
		this.dateEnd = getDate(argsArray.get(2).trim());
		this.startTime = dateStart == null ? -1 : dateStart.getTime();
		this.endTime = dateEnd == null ? -1 : dateEnd.getTime();
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
	 * Stores the created Item Object as event
	 * 
	 * @throws IOException
	 */
	private void storeEvent() throws IOException {
		Magical.getStorage().create(Storage.EVENTS_INDEX, event);
	}

	/**
	 * Check if the end date given is after the start date
	 * 
	 * @return
	 */
	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) > 0;
	}

	public boolean validNumArgs() {
		if (this.count != 3) {
			return false;
		} else {
			return true;
		}
	}
}
