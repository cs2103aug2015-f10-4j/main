package Commands;

import gui.GUIView;

public class HelpCommand extends Command {

	public HelpCommand(String args) throws Exception {
		super(args);
	}

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
