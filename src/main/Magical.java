package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Magical {
	private static final String CONFIG_STORAGE_FILENAME = "storage.txt"; 

	private static final String MESSAGE_INVALID_CMD = "Invalid command given.";
	
	protected static UI ui = new UI();
	private static Parser parser;
	protected static Storage storage;
	protected static Command lastCommand;
	
	public static void main(String args[]) {
		try {
			init();
			ui.start();
			startREPL();
		} catch (Exception e) {
			ui.displayErrorMessage();
			e.printStackTrace();
		}
	}

	private static void startREPL() throws Exception {
		while(true) {
			try {
				String userInput = ui.readInput();
				Command command = parser.parse(userInput);
				String message = command.execute();
				lastCommand = command.isUndoable()? command : lastCommand;
				ui.showToUser(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void init() throws IOException {
		parser = new Parser();
		storage = new Storage(CONFIG_STORAGE_FILENAME);
	}
	
	public static String executeCommand(String cmd, HashMap<String, String> args) throws Exception {
		switch(cmd) {
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

	private static String exit() {
		ui.displayGoodbyeMessage();
		System.exit(0);
		return null;
	}
	
}
