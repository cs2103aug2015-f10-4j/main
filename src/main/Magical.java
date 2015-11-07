package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import Commands.Command;

/**
 * Magical is the used to logic behind the application. It acts as a
 * intermediary between GUI and the rest of the classes. All core program
 * execution starts in this class. Magical should be used to execute the users
 * requests.
 *
 * @author Varun Patro
 */
public class Magical {
	public static List<Stack<ArrayList<Item>>> undoLists = new ArrayList<Stack<ArrayList<Item>>>(
			Storage.NUM_LISTS);
	private static Storage storage;

	/**
	 * This method initializes the Magical class by initializing the storage and
	 * undo history
	 */
	public static void init() {
		storage = new Storage();
		archivePastEvents();
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			undoLists.add(new Stack<ArrayList<Item>>());
		}
	}

	/**
	 * This method archives all events that ended before the current date.
	 */
	private static void archivePastEvents() {
		ArrayList<Item> eventList = storage.getList(Storage.EVENTS_INDEX);
		ArrayList<Item> newEventList = new ArrayList<Item>();
		ArrayList<Item> newEventDoneList = new ArrayList<Item>();
		CustomDate today = new CustomDate();
		for (int i = 0; i < eventList.size(); i++) {
			Item item = eventList.get(i);
			CustomDate d = item.getEndDate();
			if (d.compareTo(today) < 0) {
				newEventDoneList.add(item);
			} else {
				newEventList.add(item);
			}
		}
		try {
			storage.setList(Storage.EVENTS_INDEX, newEventList);
			storage.setList(Storage.EVENTS_DONE_INDEX, newEventDoneList);
		} catch (IOException e) {
		}
	}

	/**
	 * Getter for storage.
	 * 
	 * @return active instance of Storage
	 */
	public static Storage getStorage() {
		return storage;
	}

	/**
	 * This method reads makes use of the Parser to create the relevant command.
	 * The command is then executed and its result is returned.
	 * 
	 * @param userInput
	 *            input to execute
	 * @return message to display
	 * @exception Exception
	 */
	public static String execute(String userInput) throws Exception {
		Command command = Parser.parse(userInput);
		if (command.isUndoable()) {
			pushUndoLayer();
		}
		String message = command.execute();
		return message;
	}

	/**
	 * This method takes a snapshot of the current storage. This is known as
	 * pushing an undo layer onto the undo history stack.
	 */
	private static void pushUndoLayer() {
		int n = Storage.TASKS_INDEX;
		ArrayList<Item> t = storage.getList(n);
		ArrayList<Item> prevTasksList = listClone(t);
		ArrayList<Item> prevTasksDoneList = listClone(storage
				.getList(Storage.TASKS_DONE_INDEX));
		ArrayList<Item> prevEventsList = listClone(storage
				.getList(Storage.EVENTS_INDEX));
		ArrayList<Item> prevEventsDoneList = listClone(storage
				.getList(Storage.EVENTS_DONE_INDEX));
		undoLists.get(Storage.TASKS_INDEX).push(prevTasksList);
		undoLists.get(Storage.TASKS_DONE_INDEX).push(prevTasksDoneList);
		undoLists.get(Storage.EVENTS_INDEX).push(prevEventsList);
		undoLists.get(Storage.EVENTS_DONE_INDEX).push(prevEventsDoneList);
	}

	/**
	 * This method is used to deep clone an ArrayList of Items. Used for
	 * creating undo layers of storage.
	 * 
	 * @param list
	 *            List to clone
	 * @return clonedList
	 */
	private static ArrayList<Item> listClone(ArrayList<Item> list) {
		ArrayList<Item> newList = new ArrayList<Item>(list.size());
		try {
			for (Item i : list) {
				newList.add(i.copy());
			}
			return newList;
		} catch (Exception e) {
			return list;
		}
	}

}
