package command;

public class ExitCommand extends Command {

	public ExitCommand(String args) throws Exception {
		super(args);
	}

	/**
	 * This method executes the exit command. Which terminates the program.
	 * 
	 * @param None
	 * @return None
	 */
	@Override
	public String execute() {
		System.exit(0);
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
