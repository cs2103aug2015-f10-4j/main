package main;

public class Parser {

	private String userInput;
	private String command;
	private String args;
	
	public void execute(String userInput){
		this.userInput = userInput;
		splitInput(this.userInput);
	}
	
	private void splitInput (String input){
		String[] toSplit = input.split(" ", 2);
		this.command = toSplit[0];
		this.args = toSplit[1];
	}
	
	public String readCmd(){
		return this.command;
	}
	
	public static void main(String[] args) {
		Parser p = new Parser();
		p.execute("add task/test/this is a test/24092015/1000/1700/daily");
		System.out.println(p.userInput);
		System.out.println(p.command);
		System.out.println(p.args);
		System.out.println(p.readCmd());
	}
}
