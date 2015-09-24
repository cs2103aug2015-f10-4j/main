package main;

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
	
	public static String executeCommand(String cmd, HashMap<String, String> args) throws ParseException {
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
//		case "search":
//			return search(args);
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

	private static String add(HashMap<String, String> args) throws ParseException {
		Task task = new Task();
		if (args.get("type").equals("task")) {
			task.setType(Task.Type.TASK);
		} else {
			task.setType(Task.Type.EVENT);
		}

		task.setTitle(args.get("title"));
		task.setDescription(args.get("description"));
		task.setRecurrence(args.get("recurrence"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		task.setDueDate(dateFormat.parse(args.get("dueDate")));
		task.setStartTime(Integer.parseInt(args.get("startTime")));
		task.setEndTime(Integer.parseInt(args.get("endTime")));

		storage.writeData(task);
		return "task added";
	}

	private static String exit() {
		ui.displayGoodbyeMessage();
		System.exit(0);
		return null;
	}
	
}
