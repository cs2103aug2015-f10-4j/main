package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import parser.Parser;
import command.Command;

/**
 * Magical is the logic behind the application. It acts as an intermediary this
 * class. Magical should be used to execute the users requests. between GUI and
 * the other classes. All core program execution starts in
 *
 * @author Varun Patro
 */
public class Magical {
	private static Storage storage;
	private static List<ArrayList<Item>> displayLists;
	private static String currentTab;
	private static boolean showHelpWindow;

	public static List<Stack<ArrayList<Item>>> undoLists = new ArrayList<Stack<ArrayList<Item>>>(
			Storage.NUM_LISTS);
	public static List<Stack<ArrayList<Item>>> redoLists = new ArrayList<Stack<ArrayList<Item>>>(
			Storage.NUM_LISTS);

	public static Stack<String> undoFolderPaths = new Stack<String>();

	public static Stack<String> redoFolderPaths = new Stack<String>();

	public static Command lastCommand;
	/**
	 * This method archives all events that ended before the current date.
	 */
	private static void archivePastEvents() {
		ArrayList<Item> eventList = storage.getList(Storage.EVENTS_INDEX);
		ArrayList<Item> eventDoneList = storage
				.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Item> newEventList = new ArrayList<Item>();
		CustomDate today = new CustomDate();
		for (int i = 0; i < eventList.size(); i++) {
			Item item = eventList.get(i);
			CustomDate d = item.getEndDate();
			if (d.compareTo(today) < 0) {
				eventDoneList.add(item);
			} else {
				newEventList.add(item);
			}
		}
		try {
			storage.setList(Storage.EVENTS_INDEX, newEventList);
			storage.setList(Storage.EVENTS_DONE_INDEX, eventDoneList);
		} catch (IOException e) {
		}
	}
	/**
	 * This method reads makes use of the Parser to create the relevant command.
	 * The command is then executed and its result is returned.
	 * 
	 * @param userInput
	 * @return message to display
	 * @exception Exception
	 */
	public static String execute(String userInput) throws Exception {
		Parser p = Parser.getInstance();
		Command command = p.parse(userInput);
		if (command.isUndoable()) {
			pushUndoLayer();
		}
		String message = command.execute();
		lastCommand = command;
		return message;
	}

	/**
	 * Getter for currentTab.
	 * 
	 * @return current tab
	 */
	public static String getCurrentTab() {
		return currentTab;
	}

	/**
	 * Getter for display list.
	 * 
	 * @param index
	 * @return corresponding list of items to return
	 */
	public static ArrayList<Item> getDisplayList(int index) {
		return displayLists.get(index);
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
	 * This method initializes the Magical class by initializing the storage,
	 * undo and redo history. It also initializes the displayList with the items
	 * in storage.
	 */
	public static void init() {
		storage = new Storage();
		currentTab = "tasks";
		archivePastEvents();
		
		displayLists = new ArrayList<ArrayList<Item>>(Storage.NUM_LISTS);
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			displayLists.add(storage.getList(i));
		}
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			undoLists.add(new Stack<ArrayList<Item>>());
		}
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			redoLists.add(new Stack<ArrayList<Item>>());
		}
	}

	/**
	 * Getter for show help window
	 * 
	 * @return
	 */
	public static boolean isShowHelpWindow() {
		return showHelpWindow;
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

	/**
	 * This method takes a snapshot of the current storage. This is known as
	 * pushing an undo layer onto the undo history stack.
	 */
	public static void pushUndoLayer() {
		undoFolderPaths.push(storage.getFolderPath());
		ArrayList<Item> prevTasksList = listClone(storage
				.getList(Storage.TASKS_INDEX));
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
	 * Setter for currenTab.
	 * 
	 * @param currentTab
	 */
	public static void setCurrentTab(String currentTab) {
		Magical.currentTab = currentTab;
	}

	/**
	 * Setter for display list.
	 * 
	 * @param index
	 * @param newList
	 */
	public static void setDisplayList(int index, ArrayList<Item> newList) {
		displayLists.set(index, newList);
	}

	/**
	 * Setter for show help window
	 * 
	 * @param showHelpWindow
	 */
	public static void setShowHelpWindow(boolean showHelpWindow) {
		Magical.showHelpWindow = showHelpWindow;
	}

}
