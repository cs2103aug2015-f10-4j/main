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
	private static final String MESSAGE_INVALID_ARG = "Invalid %1s: %2s";
	private static final String MESSAGE_INVALID_NUM_INPUTS = "Invalid number of inputs";
	private boolean hasValidInput = false;
	
	public void execute(String userInput) throws Exception{
		this.userInput = userInput.trim();
		String command = getCommand(this.userInput);
		//System.out.println(command);
		String args = getArgs(this.userInput);
		//System.out.println(args);
		//this.hasValidInput = isValidCmd() && isValidArgs();
		this.hasValidInput = isValidInput(command, args);
		this.command = command;
		this.args = args;
	}
	
	private String getCommand(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		return toSplit[0].toLowerCase().trim();
	}

	private String getArgs(String userInput) {
		String[] toSplit = userInput.split(" ", 2);
		if(toSplit.length > 1){
			return toSplit[1].trim();
		} else {
			return null;
		}
	}
	
	//checks if command and args are valid
	private boolean isValidInput(String command, String args) throws Exception{
		switch(command){
			case "add":
				AddCommand add = new AddCommand(args);
				return true;
			case "search":
				return true;
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	//checks if add command arguments are okay
	private boolean checkAdd(String args) throws Exception {
		
		AddCommand add = new AddCommand(args);
		
		/*
		int count = args.length() - args.replace("/", "").length();
		
		if(count != 6){
			throw new Exception(MESSAGE_INVALID_NUM_INPUTS);
		}
		
		
		String[] argsArray = args.split("/");
		//System.out.println(Arrays.toString(argsArray));
		String type = argsArray[0].trim();
		String title = argsArray[1].trim();
		String dueDate = argsArray[3].trim();
		String startTime = argsArray[4].trim();
		String endTime = argsArray[5].trim();
		String recurrence;
		checkAddType(type);
		checkAddTitle(title);
		checkAddDueDate(dueDate, type);
		checkAddTime(startTime, 0);
		checkAddTime(endTime, 1);
		if(argsArray.length == 6){
			recurrence = null;
		} else {
			recurrence = argsArray[6].trim();
			checkAddRecurrence(recurrence);
		}
		//*/
		return true;
		
	}
	
	/*
	private void checkAddRecurrence(String recurrence) throws Exception {
		String r = recurrence.toLowerCase();
		if (!r.equals("daily") 
				&& !r.equals("weekly")
				&& !r.equals("monthly")
				&& !r.equals("yearly")) {
			throw new Exception(String.format(MESSAGE_INVALID_ARG, "recurrence", recurrence));
		}
	}

	private void checkAddTime(String time, int startEnd) throws Exception {
		String timeType;
		if(startEnd == 0){
			timeType = "startTime";
		} else {
			timeType = "endTime";
		}
		
		if(time.length() != 4){
			throw new Exception(String.format(MESSAGE_INVALID_ARG, timeType, time));
		} else {
			int hour = Integer.parseInt(time.substring(0, 2));
			int min = Integer.parseInt(time.substring(2, 4));
			
			if(hour > 23 || hour < 0
				|| min > 59 || min < 0){
				throw new Exception(String.format(MESSAGE_INVALID_ARG, timeType, time));
			}
		}
	}

	private void checkAddDueDate(String dueDate, String type) throws Exception {
		if(dueDate.equals("") && type.equals("task")){
			return;
		}
		if(dueDate.matches("^\\d+\\-\\d+\\-\\d+")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(dueDate);
			} catch (Exception e){
				throw new Exception(String.format(MESSAGE_INVALID_ARG, "dueDate", dueDate));
			}
		} else {
			throw new Exception(String.format(MESSAGE_INVALID_ARG, "dueDate", dueDate));
		}

	}

	private void checkAddTitle(String title) throws Exception {
		if(title.equals("")){
			throw new Exception(String.format(MESSAGE_INVALID_ARG, "title", title));
		}
	}

	private void checkAddType(String type) throws Exception {
		if(!type.toLowerCase().equals("event") 
			&& !type.toLowerCase().equals("task")){
			throw new Exception(String.format(MESSAGE_INVALID_ARG, "type", type));
		}
	}
	
	//*/
	
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
		
		/*
		HashMap<String, String> argsTable = new HashMap<String, String>();
		String[] argsArray = args.split("/");
		System.out.println(Arrays.toString(argsArray));
		String type = argsArray[0].trim();
		argsTable.put("type", type);
		String title = argsArray[1].trim();
		argsTable.put("title", title);
		String desc = argsArray[2].trim();
		argsTable.put("description", desc);
		String dueDate = argsArray[3].trim();
		argsTable.put("dueDate", dueDate);
		String startTime = argsArray[4].trim();
		argsTable.put("startTime", startTime);
		String endTime = argsArray[5].trim();
		argsTable.put("endTime", endTime);
		String recurrence = argsArray[6].trim();
		argsTable.put("recurrence", recurrence);
		return argsTable;
		*/
		switch(command){
		case "add":
			AddCommand add = new AddCommand(args);
			return add.getArgs();
		case "search":
			if(args != null){
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
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		//Correct
		try {
			System.out.println("------- TEST 1-------");
			Parser p1 = new Parser();
			//p1.execute("Add  task/test/this is a test/24-9-2015/1000/1700/daily");
			p1.execute("search /task");
			System.out.println(p1.userInput);
			System.out.println(p1.command);
			System.out.println(p1.args);
			System.out.println(p1.readArgs());
			//System.out.println(p1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
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
