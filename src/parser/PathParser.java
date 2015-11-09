package parser;

import command.Command;
import command.PathCommand;

/**
 * @@author A0129654X
 */
public class PathParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_ARGUMENT_FORMAT = "Use Format: path <location>";
	/** Command parameters **/
	private String location;

	/**
	 * Constructor for PathParser objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to change the file path
	 * location of storage textfile.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public PathParser(String args) throws Exception {
		super(args);
		setProperParams();
		checkLocation();
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

	@Override
	void setProperParams() {
		this.location = args;
	}

	@Override
	boolean validNumArgs() {
		return true;
	}

	@Override
	Command getCommand() throws Exception {
		Command path = new PathCommand(this.location);
		return path;
	}
}
