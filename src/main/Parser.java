package main;

public class Parser {

	private static final String[] COMMANDS = {"add", "block", "confirm", "delete", 
			"edit", "done", "show", "date", "undo", "search", "tag", 
			"untag", "help", "exit"};
	private static final String MESSAGE_EXECUTE_ERROR = "Parser has not been executed";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	
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
			case "add":
				return new AddCommand(args);
			case "block":
				return new BlockCommand(args);
			case "edit":
				return new EditCommand(args);
			case "search":
				return new SearchCommand(args);
			case "date":
				return new DateCommand(args);
			case "tag":
				return new TagCommand(args);
			case "untag":
				return new UntagCommand(args);
			case "delete":
				return new DelCommand(args);
			case "undo":
				return new UndoCommand(args);
			case "done":
				return new DoneCommand(args);
			case "show":
				return new ShowCommand(args);
			case "help":
				return new HelpCommand(args);
			case "exit":
				return new ExitCommand(args);
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
}
