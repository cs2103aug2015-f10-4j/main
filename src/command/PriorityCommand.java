package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

public class PriorityCommand extends Command {

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
	 * @param priority 
	 * @param item 
	 * @throws Exception
	 */
	public PriorityCommand(String itemID, Item item, String priority) throws Exception {
		this.itemID = itemID;
		this.item = item;
		this.priority = priority;
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

	/**
	 * Updates the original item with the new modified item
	 * 
	 * @throws IOException
	 */
	void updateItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().update(listIndex, prevItem, item);
	}
}
