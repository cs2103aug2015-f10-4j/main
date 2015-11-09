package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

public class DelCommand extends Command {

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
	 * @param item 
	 * @throws Exception
	 */
	public DelCommand(String itemID, Item item) throws Exception {
		this.itemID = itemID;
		this.item = item;
	}

	/**
	 * Removes the item from storage
	 * 
	 * @throws IOException
	 */
	void removeItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().delete(listIndex, item);
	}
	
	@Override
	public boolean isUndoable() {
		return true;
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
}
