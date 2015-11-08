package command;

import java.io.FileNotFoundException;

import main.Magical;

public class PathCommand extends Command {

	private static final String MESSAGE_ARGUMENT_PARAMS = "Use Format: path <location>";
	
	private String location;

	public PathCommand(String args) throws Exception {
		super(args);

		this.location = args;
		if (args.isEmpty()) {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_PARAMS);
		}
	}

	/**
	 * This method executes the path command. Which changes the file path of the
	 * database file.
	 * 
	 * @param None
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		try {
			Magical.getStorage().changeFolderPath(location);
		} catch (FileNotFoundException fnfe) {
			throw new Exception(location + " (no such directory)");
		}
		return "Path changed to: " + location;
	}

	@Override
	public boolean validNumArgs() {
		return true;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

}
