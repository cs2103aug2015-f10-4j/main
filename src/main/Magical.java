package main;

import java.util.*;

public class Magical {
	private static final String MESSAGE_INVALID_CMD = "Invalid command given.";
	
	private static UI ui = new UI();
	private static Parser parser = new Parser();
	private static Storage storage = new Storage();
	
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
	
	public static String executeCommand(String cmd, HashMap<String, String> args) {
		switch(cmd) {
		case "add":
			return add(args);
		case "block":
			return block(args);
		case "confirm":
			return confirm(args);
		case "delete":
			return delete(args);
		case "edit":
			return edit(args);
		case "done":
			return done(args);
		case "show":
			return show(args);
		case "date":
			return date(args);
		case "undo":
			return undo(args);
		case "search":
			return search(args);
		case "remind":
			return remind(args);
		case "tag":
			return tag(args);
		case "untag":
			return untag(args);
		case "help":
			return help();
		case "exit":
			return exit();
		default:
			return MESSAGE_INVALID_CMD;
		}		
	}
	
}
