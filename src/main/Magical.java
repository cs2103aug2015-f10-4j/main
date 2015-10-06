package main;

import java.io.IOException;
import java.util.*;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt"; 
	
	protected static Storage storage;
	protected static Stack<ArrayList<Task>> undoHistory;

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
		ArrayList<Task> upcomingTasks = upcomingTasks();
		UI.displayTaskList("Upcoming tasks", upcomingTasks);
		
	}

	private static ArrayList<Task> upcomingTasks() {
		ArrayList<Task> upcomingTasks = new ArrayList<Task>();
		ArrayList<Task> allTasks = storage.getTasks();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		Date today = cal.getTime();
		cal.add(Calendar.DATE, 3);
		Date threeDaysFromToday = cal.getTime();
		for (Task t : allTasks) {
			if (t.getDueDate().after(today) && t.getDueDate().before(threeDaysFromToday)) {
				upcomingTasks.add(t);
			}
		}
		return upcomingTasks;
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
					undoHistory.push(prevTaskList);
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
		undoHistory = new Stack<ArrayList<Task>>();
	}
}
