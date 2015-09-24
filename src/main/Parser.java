package main;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {

	private String userInput;
	private String command;
	private String args;
	private static final String[] COMMANDS = {"add", "block", "confirm", "delete", 
			"edit", "done", "show", "date", "undo", "search", "remind", "tag", 
			"untag", "help", "exit"};
	private static final String MESSAGE_EXECUTE_ERROR = "Parser has not been executed";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	private boolean hasValidInput = false;
	private Command commandClass;
	
	public void execute(String userInput) throws Exception{
		this.userInput = userInput.trim();
		
		String command = splitCommand(this.userInput);
		String args = splitArgs(this.userInput);
		
		this.hasValidInput = isValidInput(command, args);
		this.command = command;
		this.args = args;
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
	private boolean isValidInput(String command, String args) throws Exception{
		switch (command) {
			case "add":
				AddCommand add = new AddCommand(args);
				commandClass = add;
				return true;
			case "block":
				
				return true;
			case "search":
				return true;
			case "delete":
				return true;
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	//checks if add command arguments are okay
	private boolean checkAdd(String args) throws Exception {
		
		AddCommand add = new AddCommand(args);
		return true;
		
	}
	
	private void checkExecute() throws Exception{
		if(!this.hasValidInput){
			throw new Exception(MESSAGE_EXECUTE_ERROR);
		}
	}
	
	public String readCmd() throws Exception {
		checkExecute();
		return this.command;
	}
	

	public HashMap<String, String> readArgs() throws Exception{
		
		switch(command){
			case "add":
				return commandClass.getArgs();
			case "search":
				if (args != null) {
					String[] argsArray = args.split("/");
					String query = argsArray[0];
					String type = argsArray[1];
					HashMap<String, String> h = new HashMap<String, String>();
					h.put("query", query);
					h.put("type", type);
					return h;
				} else {
					return new HashMap<String, String>();
				}
			case "delete":
				HashMap<String, String> h = new HashMap<String, String>();
				h.put("task id", args);
				return h;
			default:
				return null;
		}
	}
	
	public static void main(String[] args) {
		
		//Correct
		try {
			System.out.println("------- TEST 1-------");
			Parser p1 = new Parser();
			p1.execute("Add  task/test/this is a test/24-9-2015/1000/1700/daily");
			//p1.execute("search /task");
			System.out.println(p1.userInput);
			System.out.println(p1.command);
			System.out.println(p1.args);
			System.out.println(p1.readArgs());
			//System.out.println(p1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//*
		//Invalid command
		try {
			System.out.println("------- TEST 2-------");
			Parser p2 = new Parser();
			p2.execute("foobar task/test/this is a test/24092015/1000/1700/daily");
			p2.readCmd();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//No input
		try {
			System.out.println("------- TEST 3-------");
			Parser p3 = new Parser();
			p3.readCmd();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		// ----------- FOR ADD ------------
		
		//Invalid type
		try {
			System.out.println("------- ADDTEST 1-------");
			Parser pa1 = new Parser();
			pa1.execute("Add foobar/test/this is a test/24-9-2015/1000/1700/daily");
			System.out.println(pa1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Invalid title
		try {
			System.out.println("------- ADDTEST 2-------");
			Parser pa2 = new Parser();
			pa2.execute("Add task//this is a test/24-9-2015/1000/1700/daily");
			System.out.println(pa2.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invalid date
		try {
			System.out.println("------- ADDTEST 3-------");
			Parser pa3 = new Parser();
			pa3.execute("Add task/test/this is a test/24-9-2015a/1000/1700/daily");
			System.out.println(pa3.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Valid date
		try {
			System.out.println("------- ADDTEST 4-------");
			Parser pa4 = new Parser();
			pa4.execute("Add task/test/this is a test/ /1000/1700/daily");
			System.out.println(pa4.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invalid startTime
		try {
			System.out.println("------- ADDTEST 5-------");
			Parser pa5 = new Parser();
			pa5.execute("Add task/test/this is a test/24-9-2015/1260/1700/daily");
			System.out.println(pa5.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Invalid endTime
		try {
			System.out.println("------- ADDTEST 6-------");
			Parser pa6 = new Parser();
			pa6.execute("Add task/test/this is a test/24-9-2015/1000/2400/daily");
			System.out.println(pa6.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invalid recurrence
		try {
			System.out.println("------- ADDTEST 7-------");
			Parser pa7 = new Parser();
			pa7.execute("Add task/test/this is a test/24-9-2015/1000/1700/foobar");
			System.out.println(pa7.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//*/
	}
}
