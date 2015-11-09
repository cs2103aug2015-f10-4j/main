package command;

import main.Magical;

public class HelpCommand extends Command {

	/**
	 * Constructor for HelpCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @throws Exception
	 */
	public HelpCommand() {
		
	}

	/**
	 * This method executes the help command. Which triggers the GUI to launch
	 * the help window.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		Magical.setShowHelpWindow(true);
		return null;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
