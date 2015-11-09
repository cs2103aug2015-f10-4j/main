package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class EventCommand extends Command {

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
	 * @param endTime 
	 * @param startTime 
	 * @param dateEnd 
	 * @param dateStart 
	 * @throws Exception
	 */
	public EventCommand(String title, CustomDate dateStart, CustomDate dateEnd, int startTime, int endTime) throws Exception {
		event = new Item();
		event.setType("event");
		event.setTitle(title);
		event.setStartDate(dateStart);
		event.setStartTime(startTime);
		event.setEndDate(dateEnd);
		event.setEndTime(endTime);
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
	 * Stores the created Item Object as event
	 * 
	 * @throws IOException
	 */
	private void storeEvent() throws IOException {
		Magical.getStorage().create(Storage.EVENTS_INDEX, event);
		Magical.addDisplayList(Storage.EVENTS_INDEX, event);
	}

	/**
	 * Check if the end date given is after the start date
	 * 
	 * @return
	 */
	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) > 0;
	}

	/**
	 * Adds a new event to the storage using the parameters stored
	 * 
	 * @return message to show user
	 */
	public String execute() {
		try {
			returnMsg = MESSAGE_EVENT_ADDED;
			checkEventClash();
			storeEvent();
			return returnMsg;
		} catch (IOException e) {
			return MESSAGE_EVENT_ERROR;
		} finally {
			updateView();
			Magical.setCurrentTab("events");
		}
	}
}
