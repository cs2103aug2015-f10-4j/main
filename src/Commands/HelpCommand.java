package Commands;

public class HelpCommand extends Command {

	public HelpCommand(String args) throws Exception {
		super(args);
	}

	/**
	 * This method executes the help command. Which triggers the GUI to launch
	 * the help window.
	 * 
	 * @param None
	 *            .
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
}
