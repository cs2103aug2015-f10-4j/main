package command;

import java.io.FileNotFoundException;

import main.Magical;

public class PathCommand extends Command {

	private static final String MESSAGE_ARGUMENT_PARAMS = "Use Format: path <location>";
	
	private String location;

	/**
	 * Constructor for PathCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid. Contains methods to 
	 * change the file path location of storage textfile.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public PathCommand(String args) throws Exception {
		super(args);

		setProperParams();
		
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

	@Override
	void setProperParams() {
		this.location = args;
	}

}
