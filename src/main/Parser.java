package main;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

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
		this.userInput = userInput;
		splitInput(this.userInput);
		//this.hasValidInput = isValidCmd() && isValidArgs();
		this.hasValidInput = isValidInput(this.command);
	}
	
	//Splits the input to command and args
	private void splitInput (String input){
		String[] toSplit = input.split(" ", 2);
		this.command = toSplit[0].toLowerCase();
		this.args = toSplit[1];
	}
	
	//DONT NEED THIS ANYMORE
	private boolean isValidCmd() throws Exception{
		if(Arrays.asList(COMMANDS).contains(this.command)){
			return true;
		} else {
			throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	//checks if command and args are valid
	private boolean isValidInput(String command) throws Exception{
		switch(command){
			case "add":
				return checkAdd(this.args);
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	//checks if add command arguments are okay
	private boolean checkAdd(String args) throws Exception {
		int count = args.length() - args.replace("/", "").length();
		
		if(count != 6){
			throw new Exception(MESSAGE_INVALID_NUM_INPUTS);
		}
		
		String[] argsArray = args.split("/");
		System.out.println(Arrays.toString(argsArray));
		String type = argsArray[0];
		String title = argsArray[1];
		String dueDate = argsArray[3];
		String startTime = argsArray[4];
		String endTime = argsArray[5];
		String recurrence;
		checkAddType(type);
		checkAddTitle(title);
		checkAddDueDate(dueDate);
		if(argsArray.length == 6){
			recurrence = null;
		} else {
			recurrence = argsArray[6];
		}
		return true;
	}

	private void checkAddDueDate(String dueDate) throws Exception {
		
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
	

	private void checkExecute() throws Exception{
		if(!this.hasValidInput){
			throw new Exception(MESSAGE_EXECUTE_ERROR);
		}
	}
	
	public String readCmd() throws Exception {
		checkExecute();
		return this.command;
	}
	

	public HashMap<String, String> readArgs(){
		
		HashMap<String, String> argsTable = new HashMap<String, String>();
		String[] argsArray = args.split("/");
		System.out.println(Arrays.toString(argsArray));
		String type = argsArray[0];
		argsTable.put("type", type);
		String title = argsArray[1];;
		argsTable.put("title", title);
		String desc = argsArray[2];
		argsTable.put("description", desc);
		String dueDate = argsArray[3];
		argsTable.put("dueDate", dueDate);
		String startTime = argsArray[4];
		argsTable.put("startTime", startTime);
		String endTime = argsArray[5];
		argsTable.put("endTime", endTime);
		String recurrence = argsArray[6];
		argsTable.put("recurrence", recurrence);
		return argsTable;
		
	}
	
	public static void main(String[] args) {
		
		//Correct
		try {
			System.out.println("------- TEST 1-------");
			Parser p1 = new Parser();
			p1.execute("Add task/test/this is a test/24-9-2015/1000/1700/daily");
			System.out.println(p1.userInput);
			System.out.println(p1.command);
			System.out.println(p1.checkAdd(p1.args));
			//System.out.println(p1.args);
			//System.out.println(p1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Invalid input
		/*
		try {
			System.out.println("------- TEST 2-------");
			Parser p2 = new Parser();
			p2.execute("foobar task/test/this is a test/24092015/1000/1700/daily");
			p2.readCmd();
		} catch (Exception e){
			e.printStackTrace();
		}
		//*/
		
		//No input
		/*
		try {
			System.out.println("------- TEST 3-------");
			Parser p3 = new Parser();
			p3.readCmd();
		} catch (Exception e){
			e.printStackTrace();
		}
		//*/
	}
}
