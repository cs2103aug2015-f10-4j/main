package command;

public class HelpCommand extends Command {

	/**
	 * Constructor for HelpCommand objects. Arguments are stored but have no impact on
	 * command's functionality.
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
	 * @param None
	 * @return None
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
	public boolean validNumArgs() {
		return true;
	}

	@Override
	void setProperParams() {

	}
}
