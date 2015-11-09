package parser;

import command.Command;
import command.DoneCommand;
import main.Item;

public class DoneParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: done <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_DONE = "%s is already done!";

	/** Command Parameters **/
	private Item item;
	private String itemID;
	
	/**
	 * Constructor for DoneCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to move item to done list.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DoneParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemDone();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Adds error message if item does not exist or unable to get or is already
	 * done
	 */
	void checkItemDone() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		} else if (argsArray.get(0).trim().contains("d")
				|| argsArray.get(0).trim().contains("p")) {
			invalidArgs.add(String.format(MESSAGE_INVALID_DONE, itemID));
		}
	}

	@Override
	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
	}

	@Override
	boolean validNumArgs() {
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command done = new DoneCommand(this.itemID, this.item);
		return done;
	}
	
}
