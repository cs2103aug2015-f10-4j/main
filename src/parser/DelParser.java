package parser;

import command.Command;
import main.Item;

public class DelParser extends ArgsParserSkeleton {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: delete <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";

	/** Command Parameters **/
	private Item item;
	private String itemID;

	/**
	 * Constructor for DelCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to remove to item.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DelParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemExists();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		}
	}
	
	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
	}

	boolean validNumArgs() {
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	Command getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
