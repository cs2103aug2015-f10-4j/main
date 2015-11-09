package parser;

import static org.junit.Assert.*;

import command.Command;

public class Parser {

	/** List of Command types */
	private static final String CMD_ADD = "add";
	private static final String CMD_DATE = "date";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_DONE = "done";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_EVENT = "event";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_HELP = "help";
	private static final String CMD_PATH = "path";
	private static final String CMD_PRIORITY = "set";
	private static final String CMD_REDO = "redo";
	private static final String CMD_SEARCH = "search";
	private static final String CMD_SHOW = "show";
	private static final String CMD_SORT = "sort";
	private static final String CMD_TAG = "tag";
	private static final String CMD_UNDO = "undo";
	private static final String CMD_UNDONE = "undone";
	private static final String CMD_UNTAG = "untag";

	/** Error Messages */
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command: %s";
	private static final String MESSAGE_INVALID_INPUT = "Please enter a command";

	/**
	 * Splits a given string of user input and returns the words after the first
	 * word of the input, which are the arguments of the command
	 * 
	 * @param userInput
	 * @return String containing the arguments of the command
	 */
	private static String getArgs(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		if (toSplit.length > 1) {
			return toSplit[1].trim();
		} else {
			return null;
		}
	}

	/**
	 * Splits a given string of user input and returns the first argument, which
	 * should be the command
	 * 
	 * @param userInput
	 * @return String stating the command given by user
	 */
	private static String getCommand(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		return toSplit[0].toLowerCase().trim();
	}

	/**
	 * Checks if the command is valid and returns the correct Command subclass
	 * of the user's command. Passes the arguments to the Command subclass,
	 * which will be checked for validity within the constructor. Exception
	 * thrown if a command is not valid
	 * 
	 * @param command
	 * @param args
	 * @return Command subclass object according to the user command
	 * @throws Exception
	 */
	private static ArgsParserSkeleton inputValidation(String command, String args)
			throws Exception {
		args = args == null ? "" : args;
		switch (command) {
		case CMD_ADD:
			return new AddParser(args);
		case CMD_DATE:
			return new DateParser(args);
		case CMD_DELETE:
			return new DelParser(args);
		case CMD_DONE:
			return new DoneParser(args);
		case CMD_EDIT:
			return new EditParser(args);
		case CMD_EVENT:
			return new EventParser(args);
		case CMD_EXIT:
			return new DefaultParser(args);
		case CMD_HELP:
			return new DefaultParser(args);
		case CMD_PATH:
			return new PathParser(args);
		case CMD_PRIORITY:
			return new PriorityParser(args);
		case CMD_REDO:
			return new DefaultParser(args);
		case CMD_SEARCH:
			return new SearchParser(args);
		case CMD_SHOW:
			return new ShowParser(args);
		case CMD_SORT:
			return new SortParser(args);
		case CMD_TAG:
			return new TagParser(args);
		case CMD_UNDO:
			return new DefaultParser(args);
		case CMD_UNDONE:
			return new UndoneParser(args);
		case CMD_UNTAG:
			return new UntagParser(args);
		default:
			throw new Exception(String.format(MESSAGE_INVALID_COMMAND, command));
		}
	}

	/**
	 * Takes in an input from the user and returns the correct command object
	 * for executing what the user wants. Calls the inputValidation method.
	 * Exception is thrown if no input is given.
	 * 
	 * @param userInput
	 * @return Command object to use for executing
	 * @throws Exception
	 */
	public static Command parse(String userInput) throws Exception {
		assertNotNull(userInput);
		if (!userInput.isEmpty()) {
			String trimInput = userInput.trim();
			String command = getCommand(trimInput);
			String args = getArgs(trimInput);
			return inputValidation(command, args).getCommand();
		} else {
			throw new Exception(MESSAGE_INVALID_INPUT);
		}
	}
}