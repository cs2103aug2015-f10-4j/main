package main;

public class HelpCommand extends Command {
	
	public HelpCommand(String args) throws Exception {
		super(args);
	}
	
	public String execute() {
		UI.displayHelpMessage();
		return null;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
	}
}
