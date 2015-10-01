package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt"; 

	protected static UI ui = new UI();
	private static Parser parser;
	protected static Storage storage;
	protected static Command lastCommand = null;
	protected static Stack<ArrayList<Task>> undoHistory;

	public static void main(String args[]) {
		try {
			init();
			UI.start();
			startREPL();
		} catch (Exception e) {
			UI.displayErrorMessage();
			e.printStackTrace();
		}
	}

	private static void startREPL() throws Exception {
		while(true) {
			try {
				String userInput = ui.readInput();
				Command command = parser.parse(userInput);
				ArrayList<Task> prevTaskList = listClone(storage.getTasks());
				String message = command.execute();
				if (command.isUndoable()) {
					undoHistory.push(prevTaskList);
				}
				lastCommand = command.isUndoable()? command : lastCommand;
				UI.showToUser(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<Task> listClone(ArrayList<Task> tasks) {
		ArrayList<Task> newTaskList = new ArrayList<Task>(tasks.size());
		try {
			for(Task t : tasks) {
				newTaskList.add((Task) t.clone());
			}
			return newTaskList;
		} catch (Exception e) {
			return tasks;
		}
	}

	public static void init() throws IOException {
		parser = new Parser();
		storage = new Storage(CONFIG_STORAGE_FILENAME);
		undoHistory = new Stack<ArrayList<Task>>();
//		undoHistory.push(listClone(storage.getTasks()));
	}
}
