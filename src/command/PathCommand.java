package command;

import java.io.FileNotFoundException;

import main.Magical;

public class PathCommand extends Command {

	private static final String MESSAGE_DIRECTORY_CHANGED = "Path changed to: %s";
	private static final String MESSAGE_DIRECTORY_MISSING = "%s (no such directory)";

	/** Messaging **/
	private static final String MESSAGE_ARGUMENT_FORMAT = "Use Format: path <location>";

	/** Command parameters **/
	private String location;

	/**
	 * Constructor for PathCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to change the file path
	 * location of storage textfile.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public PathCommand(String location) throws Exception {
		this.location = location;
	}

	/**
	 * Throw exception if no location was specified
	 * 
	 * @throws IllegalArgumentException
	 */
	void checkLocation() throws IllegalArgumentException {
		if (location.isEmpty()) {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_FORMAT);
		}
	}

	/**
	 * This method executes the path command. Which changes the file path of the
	 * database file.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		try {
			Magical.getStorage().changeFolderPath(location);
		} catch (FileNotFoundException fnfe) {
			throw new Exception(String.format(MESSAGE_DIRECTORY_MISSING,
					location));
		}
		return String.format(MESSAGE_DIRECTORY_CHANGED, location);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
