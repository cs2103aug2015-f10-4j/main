package main;

public class Parser {

	private static final String[] COMMANDS = {"add", "block", "confirm", "delete", 
			"edit", "done", "show", "date", "undo", "search", "remind", "tag", 
			"untag", "help", "exit"};
	static final String MESSAGE_EXECUTE_ERROR = "Parser has not been executed";
	static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	
	public Command parse(String userInput) throws Exception{
		if(userInput != null){
			String trimInput = userInput.trim();
			String command = splitCommand(trimInput);
			String args = splitArgs(trimInput);
			return inputValidation(command, args);
		} else {
			throw new Exception("Must enter a command when calling execute");
		}
	}
	
	private String splitCommand(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		return toSplit[0].toLowerCase().trim();
	}

	private String splitArgs(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		if(toSplit.length > 1){
			return toSplit[1].trim();
		} else {
			return null;
		}
	}
	
	//checks if command and args are valid
	private Command inputValidation(String command, String args) throws Exception{
		args = args == null ? "" : args;
		switch (command) {
			case "add":
				return new AddCommand(args);
			case "block":
				BlockCommand block = new BlockCommand(args);
				return block;
			case "search":
				return new SearchCommand(args);
			case "delete":
				return new DelCommand(args);
			case "undo":
				return new Undo(args);
			case "done":
				return new DoneCommand(args);
			case "show":
				return new ShowCommand(args);
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
}
