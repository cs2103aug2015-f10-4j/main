package main;

import Commands.AddCommand;
import Commands.BlockCommand;
import Commands.Command;
import Commands.DateCommand;
import Commands.DelCommand;
import Commands.DoneCommand;
import Commands.EditCommand;
import Commands.EventCommand;
import Commands.ExitCommand;
import Commands.HelpCommand;
import Commands.PriorityCommand;
import Commands.SearchCommand;
import Commands.ShowCommand;
import Commands.TagCommand;
import Commands.UndoCommand;
import Commands.UntagCommand;

public class Parser {
	private static final String CMD_ADD = "add";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_UNDO = "undo";
	private static final String CMD_BLOCK = "block";
	private static final String CMD_CONFIRM = "confirm";
	private static final String CMD_DATE = "date";
	private static final String CMD_DONE = "done";
	private static final String CMD_HELP = "help";
	private static final String CMD_SEARCH = "search";
	private static final String CMD_SHOW = "show";
	private static final String CMD_TAG = "tag";
	private static final String CMD_UNTAG = "untag";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_PRIORITY = "set";
	private static final String CMD_EVENT = "event";
	
	static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	
	public static Command parse(String userInput) throws Exception{
		if(userInput != null){
			String trimInput = userInput.trim();
			String command = splitCommand(trimInput);
			String args = splitArgs(trimInput);
			return inputValidation(command, args);
		} else {
			throw new Exception("Must enter a command when calling execute");
		}
	}
	
	private static String splitCommand(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		return toSplit[0].toLowerCase().trim();
	}

	private static String splitArgs(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		if(toSplit.length > 1){
			return toSplit[1].trim();
		} else {
			return null;
		}
	}
	
	//checks if command and args are valid
	private static Command inputValidation(String command, String args) throws Exception{
		args = args == null ? "" : args;
		switch (command) {
			case CMD_ADD:
				return new AddCommand(args);
			case CMD_BLOCK:
				return new BlockCommand(args);
			case CMD_EDIT:
				return new EditCommand(args);
			case CMD_SEARCH:
				return new SearchCommand(args);
			case CMD_DATE:
				return new DateCommand(args);
			case CMD_TAG:
				return new TagCommand(args);
			case CMD_UNTAG:
				return new UntagCommand(args);
			case CMD_DELETE:
				return new DelCommand(args);
			case CMD_UNDO:
				return new UndoCommand(args);
			case CMD_DONE:
				return new DoneCommand(args);
			case CMD_SHOW:
				return new ShowCommand(args);
			case CMD_HELP:
				return new HelpCommand(args);
			case CMD_EXIT:
				return new ExitCommand(args);
			case CMD_PRIORITY:
				return new PriorityCommand(args);
			case CMD_EVENT:
				return new EventCommand(args);
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND + ": " + command);
		}
	}
}
