package parser;

import command.Command;
import command.SearchCommand;

/**
 * @@author A0129654X
 */
public class SearchParser extends ArgsParserAbstract {

	/** Command parameters **/
	private String query;

	/**
	 * Constructor for SearchParser objects. Stores the correct arguments
	 * properly. Contains methods to display items containing query
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SearchParser(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs("", 1);
		this.count = argsArray.size();
		setProperParams();
	}

	@Override
	void setProperParams() {
		this.query = args.trim();
	}

	@Override
	boolean validNumArgs() {
		return true;
	}

	@Override
	Command getCommand() throws Exception {
		Command search = new SearchCommand(this.query);
		return search;
	}

}
