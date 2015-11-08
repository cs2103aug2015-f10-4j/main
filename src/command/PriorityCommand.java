package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

public class PriorityCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_ARGUMENT_FORMAT = "Use Format: set <item_id> <priority>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_PRIORITY = "Priority";
	private static final String MESSAGE_PRIORITY_UPDATED = "Priority updated for %s";
	private static final String MESSAGE_PRIORITY_ERROR = "Unable to change priority for %s";

	/** Command parameters **/
	private Item item;
	private String itemID;
	private String priority;
	private Item prevItem;

	/**
	 * Constructor for PriorityCommand objects. Checks if arguments are valid
	 * and stores the correct arguments properly. Throws the appropriate
	 * exception if arguments are invalid. Contains methods to change the
	 * priority of an item.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public PriorityCommand(String args) throws Exception {
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

	/**
	 * Make 2 copies of the item to be stored in prevItem and item
	 */
	void duplicateItem() {
		prevItem = item;
		item = prevItem.copy();
	}

	/**
	 * This method executes the priority command. Which simply changes the
	 * priority of the selected task or event to the new priority specified.
	 * 
	 * @return message to show user
	 * @throws Exception 
	 */
	@Override
	public String execute() throws Exception {
		duplicateItem();
		item.setPriority(priority);

		try {
			updateItem();
		} catch (IOException e) {
			throw new Exception(String.format(MESSAGE_PRIORITY_ERROR, itemID));
		} finally {
			updateView();
		}

		return String.format(MESSAGE_PRIORITY_UPDATED, itemID);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
		if (argsArray.size() == 1) {
			priority = STRING_EMPTY;
		} else {
			priority = getPriority(argsArray.get(1).trim());
		}
	}

	/**
	 * Updates the original item with the new modified item
	 * 
	 * @throws IOException
	 */
	void updateItem() throws IOException {
		int listIndex = Storage.getListIndex(argsArray.get(0));
		Magical.getStorage().update(listIndex, prevItem, item);
	}

	public boolean validNumArgs() {
		if (this.count > 2 && this.count < 0) {
			return false;
		} else {
			return true;
		}
	}
}
