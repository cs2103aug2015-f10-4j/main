package main;

public class UndoCommand extends Command {

	public UndoCommand(String args) {
		super(args);
	}
	
	@Override
	public String execute() {
		if (Magical.lastCommand == null) {
			return "nothing to undo";
		}
		return Magical.lastCommand.undo();
	}
	
	@Override
	public String undo() {
		return "cannot undo a undo command";
	}
}
