package Commands;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class EventCommand extends Command {

	
	/** Messaging */
	private static final String MESSAGE_INVALID_FLEXI = "Use format: event <title> "
			+ "from <start date> <start time> "
			+ "to <end date> <end time>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_EVENT_ADDED = "event added";
	private static final String MESSAGE_EVENT_CLASH = ". Another event exists on the same date.";
	private static final String MESSAGE_EVENT_ERROR = "unable to add event";

	/** For checking */
	private final CustomDate today = getDate("today");
	
	/** Command parameters **/
	protected String title;
	protected CustomDate dateStart;
	protected CustomDate dateEnd;
	protected int startTime;
	protected int endTime;
	private Item event;

	public EventCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(args, "(?<=\\s)to(?=\\s)|(?<=\\s)from(?=\\s)", -1);
		
		removeEscapeCharacters();
		this.count = argsArray.size();

		splitArgsAfterDateTime();
		
		this.count = argsArray.size();

		for (int i = 0; i < count; i++) {
			assertNotNull(argsArray.get(i));
		}

		if (validNumArgs()) {

			setProperParams();

			if (dateEnd.getDateString().equals(today.getDateString())) {
				dateEnd.set("day", dateStart.getDay());
				dateEnd.set("month", dateStart.getMonth() - 1);
				dateEnd.set("year", dateStart.getYear());
				System.out.println(dateEnd);
			}
			
			if (title == null) {
				invalidArgs.add(MESSAGE_INVALID_TITLE);
			}
			if (dateStart == null) {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
			}

			if (dateEnd == null) {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
			}

			if (dateStart != null && dateEnd != null && !validDateRange()) {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_RANGE);
			}
			
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(String.format(MESSAGE_HEADER_INVALID, invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_FLEXI);
		}
		
	}

	/**
	 * Set the relevant parameters of EventCommand to that of the specified event
	 */
	private void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dateStart = getDate(argsArray.get(1).trim());
		this.dateEnd = getDate(argsArray.get(2).trim());
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

	private boolean isClashing() {
		ArrayList<Item> events = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		for (Item t : events) {
			if (t.getEndDate().equals(event.getEndDate())) {
				return true;
			}
		}
		return false;
	}
	
	public String execute() {
		event = new Item();
		event.setType("event");
		event.setTitle(title);
		event.setStartDate(dateStart);
		event.setStartTime(startTime);
		event.setEndDate(dateEnd);
		event.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_EVENT_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_EVENT_CLASH;
			}
			Magical.getStorage().create(Storage.EVENTS_INDEX, event);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_EVENT_ERROR;
		} finally {
			GUIModel.setEventList(Magical.getStorage().getList(
					Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.getStorage().getList(
					Storage.EVENTS_DONE_INDEX));
			GUIModel.setCurrentTab("events");
		}
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
