package main;

public class Parser {

	private static final String[] COMMANDS = {"add", "block", "confirm", "delete", 
			"edit", "done", "show", "date", "undo", "search", "remind", "tag", 
			"untag", "help", "exit"};
	private static final String MESSAGE_EXECUTE_ERROR = "Parser has not been executed";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	
	public Command execute(String userInput) throws Exception{
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
		switch (command) {
			case "add":
				AddCommand add = new AddCommand(args);
				return add;
			case "block":
				
			case "search":
				args = args == null ? "" : args;
				SearchCommand commandClass = new SearchCommand(args);
				return commandClass;
			case "delete":
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	public static void main(String[] args) {
		
		//Correct
		try {
			System.out.println("------- TEST 1-------");
			Parser p1 = new Parser();
			Command c = p1.execute("Add  task/test/this is a test/24-9-2015/1000/1700/daily");
			//p1.execute("search /task");
			//System.out.println(p1.userInput);
			//System.out.println(p1.command);
			//System.out.println(p1.args);
			//System.out.println(p1.readArgs());
			//System.out.println(p1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Invalid command
		try {
			System.out.println("------- TEST 2-------");
			Parser p2 = new Parser();
			p2.execute("foobar task/test/this is a test/24092015/1000/1700/daily");
		} catch (Exception e){
			e.printStackTrace();
		}

		//No input
		try {
			System.out.println("------- TEST 3-------");
			Parser p3 = new Parser();
			p3.execute(null);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		// ----------- FOR ADD ------------
		
		//Invalid type
		try {
			System.out.println("------- ADDTEST 1-------");
			Parser pa1 = new Parser();
			pa1.execute("Add foobar/test/this is a test/24-9-2015/1000/1700/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//Invalid title
		try {
			System.out.println("------- ADDTEST 2-------");
			Parser pa2 = new Parser();
			pa2.execute("Add task//this is a test/24-9-2015/1000/1700/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Invalid date
		try {
			System.out.println("------- ADDTEST 3-------");
			Parser pa3 = new Parser();
			pa3.execute("Add task/test/this is a test/24-9-2015a/1000/1700/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		// Valid date
		try {
			System.out.println("------- ADDTEST 4-------");
			Parser pa4 = new Parser();
			pa4.execute("Add task/test/this is a test/ /1000/1700/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invalid startTime
		try {
			System.out.println("------- ADDTEST 5-------");
			Parser pa5 = new Parser();
			pa5.execute("Add task/test/this is a test/24-9-2015/1260/1700/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Invalid endTime
		try {
			System.out.println("------- ADDTEST 6-------");
			Parser pa6 = new Parser();
			pa6.execute("Add task/test/this is a test/24-9-2015/1000/2400/daily");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invalid recurrence
		try {
			System.out.println("------- ADDTEST 7-------");
			Parser pa7 = new Parser();
			pa7.execute("Add task/test/this is a test/24-9-2015/1000/1700/foobar");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		// ----------- FOR SEARCH ------------
		
		//Invalid type
		try {
			System.out.println("------- SEARCHTEST 1-------");
			Parser pa1 = new Parser();
			pa1.execute("search /task");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//*/
	}
}
