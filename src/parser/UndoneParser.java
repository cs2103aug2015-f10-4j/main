package parser;

import command.Command;
import main.Item;

public class UndoneParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: undone <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_UNDONE = "Undone tasks cannot be undone";
	
	/** Command Parameters **/
	private Item item;
	private String itemID;

	/**
	 * Constructor for DoneCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to move item to undone list.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public UndoneParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemUndone();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Adds error message if item does not exist or unable to get or is already
	 * done
	 */
	void checkItemUndone() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		} else if (argsArray.get(0).trim().contains("t")
				|| argsArray.get(0).trim().contains("e")) {
			invalidArgs.add(MESSAGE_INVALID_UNDONE);
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
	Command getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
}
