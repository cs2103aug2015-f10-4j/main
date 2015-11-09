package parser;

import command.Command;
import command.PriorityCommand;
import main.Item;

/**
 * @@author A0129654X
 */
public class PriorityParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_ARGUMENT_FORMAT = "Use Format: set <item_id> <priority>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_PRIORITY = "Priority";
	
	/** Command parameters **/
	private Item item;
	private String itemID;
	private String priority;
	
	/**
	 * Constructor for PriorityCommand objects. Checks if arguments are valid
	 * and stores the correct arguments properly. Throws the appropriate
	 * exception if arguments are invalid. Contains methods to change the
	 * priority of an item.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public PriorityParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", 2);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemExists();
			checkPriorityValid();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_ARGUMENT_FORMAT);
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
	
	/**
	 * Adds error message if priority given is invalid
	 */
	void checkPriorityValid() {
		if (priority == null) {
			invalidArgs.add(MESSAGE_INVALID_PRIORITY);
		}
	}

	@Override
	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
		if (argsArray.size() == 1) {
			priority = STRING_EMPTY;
		} else {
			priority = getPriority(argsArray.get(1).trim());
		}
	}

	@Override
	boolean validNumArgs() {
		if (this.count > 2 && this.count < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command priority = new PriorityCommand(this.itemID, this.item, this.priority); 
		return priority;
	}
}
