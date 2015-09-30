package main;

public class Undo extends Command {

	public Undo(String args) {
		super(args);
	}
	
	@Override
	public String execute() {
		Magical.lastCommand.undo();
		return null;
	}
	
	@Override
	public String undo() {
		return "cannot undo a undo command";
	}
}
