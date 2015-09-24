package main;

public class AddCommand extends Command{

	public AddCommand(String command, String args) {
		super(command, args);
	}
	
	public boolean checkCount(){
		if(this.count != 6){
			return false;
		} else {
			return true;
		}
	}
}
