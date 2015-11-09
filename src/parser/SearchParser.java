package parser;

import command.Command;

public class SearchParser extends ArgsParserSkeleton {

	/** Command parameters **/
	private String query;

	/**
	 * Constructor for SearchCommand objects. Stores the correct arguments
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
	Command getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
