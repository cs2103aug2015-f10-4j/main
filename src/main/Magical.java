package main;

import java.io.IOException;
import java.util.*;

import Commands.Command;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt";

	public static Storage storage;
	public static Stack<ArrayList<Task>> undoListHistory;
	public static Stack<ArrayList<Task>> undoDoneListHistory;

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
		ArrayList<Task> prevTaskList = listClone(storage.getTasks());
		String message = command.execute();
		if (command.isUndoable()) {
			undoListHistory.push(prevTaskList);
		}
		return message;
	}

	private static void startREPL() throws Exception {
		while(true) {
			try {
				String userInput = UI.readInput();
				Command command = Parser.parse(userInput);
				ArrayList<Task> prevTaskList = listClone(storage.getTasks());
				String message = command.execute();
				UI.showToUser(message);
				if (command.isUndoable()) {
					undoListHistory.push(prevTaskList);
					UI.displayTaskList("Tasks", storage.getTasks());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<Task> listClone(ArrayList<Task> tasks) {
		ArrayList<Task> newTaskList = new ArrayList<Task>(tasks.size());
		try {
			for(Task t : tasks) {
				newTaskList.add(t.copy());
			}
			return newTaskList;
		} catch (Exception e) {
			return tasks;
		}
	}

	public static void init() throws IOException {
		storage = new Storage(CONFIG_STORAGE_FILENAME);
		undoListHistory = new Stack<ArrayList<Task>>();
		undoDoneListHistory = new Stack<ArrayList<Task>>();
	}
}
