package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt"; 

	private static final String MESSAGE_INVALID_CMD = "Invalid command given.";
	
	private static UI ui = new UI();
	private static Parser parser = new Parser();
	private static Storage storage = new Storage(CONFIG_STORAGE_FILENAME);
	
	public static void main(String args[]) {
		try {
			ui.start();
			String userInput = ui.readInput();
			parser.execute(userInput);
			String message = executeCommand(parser.readCmd(), parser.readArgs());
			ui.showToUser(message);
		} catch (Exception e) {
			ui.displayErrorMessage();
			e.printStackTrace();
		}
	}
	
	public static String executeCommand(String cmd, HashMap<String, String> args) throws Exception {
		switch(cmd) {
		case "add":
			return add(args);
//		case "block":
//			return block(args);
//		case "confirm":
//			return confirm(args);
//		case "delete":
//			return delete(args);
//		case "edit":
//			return edit(args);
//		case "done":
//			return done(args);
//		case "show":
//			return show(args);
//		case "date":
//			return date(args);
//		case "undo":
//			return undo(args);
		case "search":
			return search(args);
//		case "remind":
//			return remind(args);
//		case "tag":
//			return tag(args);
//		case "untag":
//			return untag(args);
//		case "help":
//			return help();
		case "exit":
			return exit();
		default:
			return MESSAGE_INVALID_CMD;
		}		
	}

	private static String search(HashMap<String, String> args) throws Exception {
		String query = args.get("query") != null ? args.get("query") : "";
		String type = args.get("type") != null ? args.get("type") : "";
		ArrayList<Task> results = storage.readTasks();
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for (Task t : results) {
			if ((t.getTitle().contains(query) || t.getDescription().contains(query)) && t.getType().contains(type)) {
				filteredResults.add(t);
			}
		}
		UI.displayTaskList("all", filteredResults);
		return null;
	}

	private static String add(HashMap<String, String> args) throws Exception {
		Task task = new Task();
		task.setType(args.get("type"));
		task.setTitle(args.get("title"));
		task.setDescription(args.get("description"));
		task.setRecurrence(args.get("recurrence"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		task.setDueDate(dateFormat.parse(args.get("dueDate")));
		task.setStartTime(Integer.parseInt(args.get("startTime")));
		task.setEndTime(Integer.parseInt(args.get("endTime")));

		storage.writeTask(task);
		return "task added";
	}

	private static String exit() {
		ui.displayGoodbyeMessage();
		System.exit(0);
		return null;
	}
	
}
