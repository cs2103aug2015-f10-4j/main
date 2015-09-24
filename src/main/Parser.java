package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

	private String userInput;
	private String command;
	private String args;
	private static final String[] COMMANDS = {"add", "block", "confirm", "delete", 
			"edit", "done", "show", "date", "undo", "search", "remind", "tag", 
			"untag", "help", "exit"};
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	private static final String MESSAGE_INVALID_ARGS = "Invalid arguments";
	
	public void execute(String userInput){
		this.userInput = userInput;
		splitInput(this.userInput);
	}
	
	private void splitInput (String input){
		String[] toSplit = input.split(" ", 2);
		this.command = toSplit[0].toLowerCase();
		this.args = toSplit[1];
	}
	
	public String readCmd() throws Exception{
		if(Arrays.asList(COMMANDS).contains(this.command)){
			return this.command;
		} else {
			throw new Exception(MESSAGE_INVALID_COMMAND);
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Parser p = new Parser();
			p.execute("Add task/test/this is a test/24092015/1000/1700/daily");
			System.out.println(p.userInput);
			System.out.println(p.command);
			System.out.println(p.args);
			System.out.println(p.readCmd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Parser p = new Parser();
			p.execute("foobar task/test/this is a test/24092015/1000/1700/daily");
			p.readCmd();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
