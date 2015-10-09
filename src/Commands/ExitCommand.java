package Commands;

import main.UI;

public class ExitCommand extends Command {
	
	public ExitCommand(String args) throws Exception {
		super(args);
	}
	
	public String execute() {
		UI.displayGoodbyeMessage();
		System.exit(0);
		return null;
	}
}
