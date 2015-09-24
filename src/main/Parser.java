package main;

import java.util.Arrays;
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
	private static final String MESSAGE_INVALID_ARGS = "Invalid arguments";
	private boolean hasValidInput = false;
	
	public void execute(String userInput) throws Exception{
		this.userInput = userInput;
		splitInput(this.userInput);
		this.hasValidInput = isValidCmd() && isValidArgs();
	}
	
	private void splitInput (String input){
		String[] toSplit = input.split(" ", 2);
		this.command = toSplit[0].toLowerCase();
		this.args = toSplit[1];
	}
	
	private boolean isValidCmd()throws Exception{
		if(Arrays.asList(COMMANDS).contains(this.command)){
			return true;
		} else {
			throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	private boolean isValidArgs(){
		return true;
		//switch()
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
	

	public Hashtable readArgs(){
		
		return null;
	}
	
	public static void main(String[] args) {
		
		//Correct
		try {
			System.out.println("------- TEST 1-------");
			Parser p1 = new Parser();
			p1.execute("Add task/test/this is a test/24092015/1000/1700/daily");
			System.out.println(p1.userInput);
			System.out.println(p1.command);
			System.out.println(p1.args);
			System.out.println(p1.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Invalid input
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
	}
}
