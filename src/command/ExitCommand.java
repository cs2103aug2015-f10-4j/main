package command;

/**
 * @@author A0131729E
 */
public class ExitCommand extends Command {

	/**
	 * Constructor for ExitCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @throws Exception
	 */
	public ExitCommand() {

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
}
