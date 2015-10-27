package Commands;

import java.util.Arrays;

import main.Task;
import main.UI;

public class ShowCommand extends Command{

	private String error = STRING_EMPTY;
	private String[] tags;
	private String type;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format:\n"
			+ "1. show\n"
			+ "2. show <type>\n"
			+ "3. show <tag 1> <tag 2> ...";
	
	public ShowCommand(String args) throws Exception{
		super(args);
		
		this.argsArray = args.split(" ");
		this.count = argsArray.length;
		this.type = argsArray[0].trim();
		this.tags = null;
		
		if(type.equals("")){
			this.type = "all";
		} else if(!type.equalsIgnoreCase("event")||!type.equalsIgnoreCase("task")){
			this.type = "tag";
			this.tags = this.argsArray;
		}
	
		if (!error.equals(STRING_EMPTY)) {
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}			
	}
	
	public boolean validNumArgs() {
		return true;
	}
	
	@Override
	public String execute() {
		switch (type){
			case "all":
				//Do something
				break;
			case "event":
				//Do something
				break;
			case "task":
				//Do something
				break;
			case "tag":
				//Do something
				break;
			default:
				//Do something
				break;
		}
		return null;
	}
	
	@Override
	public boolean isUndoable(){
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		//ShowCommand s = new ShowCommand("");
		//ShowCommand s = new ShowCommand("event");
		ShowCommand s = new ShowCommand("food work play");
	}
}
