package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

public class DelCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: delete <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_ITEM_DELETED = "%s deleted";
	private static final String MESSAGE_ITEM_ERROR_DELETE = "Unable to delete";

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
	public DelCommand(String args) throws Exception {
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

	/**
	 * This method executes the delete command, which deletes the specified task
	 * or event from the database.
	 * 
	 * @return message to show user
	 * @throws Exception 
	 */
	@Override
	public String execute() throws Exception {
		try {
			removeItem();
			return String.format(MESSAGE_ITEM_DELETED, itemID);
		} catch (IOException e) {
			throw new Exception(MESSAGE_ITEM_ERROR_DELETE);
		} finally {
			updateView();
		}
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	/**
	 * Removes the item from storage
	 * 
	 * @throws IOException
	 */
	void removeItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().delete(listIndex, item);
		Magical.deleteDisplayList(listIndex, item);
	}

	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
	}

	public boolean validNumArgs() {
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void main(String[] args) throws Exception {
		DelCommand d = new DelCommand("");
	}
}
