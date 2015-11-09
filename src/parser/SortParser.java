package parser;

import java.util.ArrayList;
import command.Command;
import command.SortCommand;

/**
 * @@author A0129654X
 */
public class SortParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: sort <parameter> (upto 3 parameters)";
	private static final String MESSAGE_INVALID_PARAMS = "Parameters";

	/** Command parameters **/
	private ArrayList<String> sortParams;

	/**
	 * Constructor for SortParser objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to sort the displayed tasks.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SortParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", 3);
		this.count = argsArray.size();
		setProperParams();

		if (validNumArgs()) {
			checkParams();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}

	/**
	 * Set the sorting parameters to all types if none are specified, else add
	 * error message
	 */
	void checkParams() {
		if (sortParams.size() == 1 && sortParams.get(0).isEmpty()) {
			sortParams.add("priority");
			sortParams.add("date");
			sortParams.add("title");
		} else if (!isValidSortParams()) {
			invalidArgs.add(MESSAGE_INVALID_PARAMS);
		}
	}

	/**
	 * Returns true if sort parameters are valid (priority, title, date), or
	 * false otherwise
	 * 
	 * @return whether sort paramters are valid
	 */
	private boolean isValidSortParams() {
		for (String param : sortParams) {
			if (!(param.equals("priority") || param.equals("title") || param
					.equals("date"))) {
				return false;
			}
		}
		return true;
	}

	@Override
	void setProperParams() {
		this.sortParams = argsArray;
	}

	@Override
	boolean validNumArgs() {
		if (count > 3) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command sort = new SortCommand(this.sortParams);
		return sort;
	}

}
