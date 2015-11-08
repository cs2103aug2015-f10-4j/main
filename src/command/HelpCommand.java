package command;

public class HelpCommand extends Command {

	/**
	 * Constructor for HelpCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public HelpCommand(String args) throws Exception {
		super(args);
	}

	/**
	 * This method executes the help command. Which triggers the GUI to launch
	 * the help window.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		gui.GUIModel.showHelpWindow = true;
		return null;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	void setProperParams() {

	}

	@Override
	public boolean validNumArgs() {
		return true;
	}
}
