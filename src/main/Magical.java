package main;

import java.util.*;

import Commands.Command;

public class Magical {
	private static Storage storage;
	public static List<Stack<ArrayList<Item>>> undoLists = new ArrayList<Stack<ArrayList<Item>>>(
			Storage.NUM_LISTS);

	/**
	 * This method initializes the Magical class by initializing the storage and
	 * undo history
	 * 
	 * @param None
	 * @return None
	 */
	public static void init() {
		storage = new Storage();
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			undoLists.add(new Stack<ArrayList<Item>>());
		}
	}

	/**
	 * Getter for storage.
	 * 
	 * @param None
	 * @return Storage
	 */
	public static Storage getStorage() {
		return storage;
	}

	/**
	 * This method reads makes use of the Parser to create the relevant command.
	 * The command is then executed and its result is returned.
	 * 
	 * @param String
	 *            userInput
	 * @return String message to display
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
	 * 
	 * @param None
	 * @return None
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
	 * @param ArrayList<Item> list
	 * @return ArrayList<Item>
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
