package command;

public class ExitCommand extends Command {

	/**
	 * Constructor for ExitCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ExitCommand(String args) throws Exception {
		super(args);
	}

	/**
	 * This method executes the exit command which terminates the program.
	 * 
	 * @return message to show user
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
	void setProperParams() {

	}

	@Override
	public boolean validNumArgs() {
		return true;
	}
}
