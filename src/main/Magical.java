package main;

import java.io.IOException;
import java.util.*;

import Commands.Command;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt";

	public static Storage storage;
	public static List<Stack<ArrayList<Task>>> undoLists = new ArrayList<Stack<ArrayList<Task>>>(Storage.NUM_LISTS);

	public static Storage getStorage(){
		return storage;
	}

	public static void main(String args[]) {
		try {
			init();
			startApp();
			startREPL();
		} catch (Exception e) {
			UI.displayErrorMessage();
			e.printStackTrace();
		}
	}

	private static void startApp() {
		UI.displayWelcomeMessage();
	}

	public static String parseCommand(String userInput) throws Exception{
		Command command = Parser.parse(userInput);
		if (command.isUndoable()) {
			pushUndoLayer();
		}
		String message = command.execute();
		return message;
	}

	private static void startREPL() throws Exception {
		while(true) {
			try {
				String userInput = UI.readInput();
				Command command = Parser.parse(userInput);
				ArrayList<Task> prevTaskList = listClone(storage.getList(Storage.TASKS_INDEX));
				String message = command.execute();
				UI.showToUser(message);
				if (command.isUndoable()) {
					undoLists.get(Storage.TASKS_INDEX).push(prevTaskList);
					UI.displayTaskList("Tasks", storage.getList(Storage.TASKS_INDEX));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void pushUndoLayer() {
		int n = Storage.TASKS_INDEX;
		ArrayList<Task> t = storage.getList(n);
		ArrayList<Task> prevTasksList = listClone(t);
		ArrayList<Task> prevTasksDoneList = listClone(storage.getList(Storage.TASKS_DONE_INDEX));
		ArrayList<Task> prevEventsList = listClone(storage.getList(Storage.EVENTS_INDEX));
		ArrayList<Task> prevEventsDoneList = listClone(storage.getList(Storage.EVENTS_DONE_INDEX));
		undoLists.get(Storage.TASKS_INDEX).push(prevTasksList);
		undoLists.get(Storage.TASKS_DONE_INDEX).push(prevTasksDoneList);
		undoLists.get(Storage.EVENTS_INDEX).push(prevEventsList);
		undoLists.get(Storage.EVENTS_DONE_INDEX).push(prevEventsDoneList);
	}

	private static ArrayList<Task> listClone(ArrayList<Task> tasks) {
		ArrayList<Task> newList = new ArrayList<Task>(tasks.size());
		try {
			for(Task t : tasks) {
				newList.add(t.copy());
			}
			return newList;
		} catch (Exception e) {
			return tasks;
		}
	}

	public static void init() throws IOException {
		storage = new Storage();
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			undoLists.add(new Stack<ArrayList<Task>>());
		}
	}
}
