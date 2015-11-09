package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class UndoneCommand extends Command {

	private static final String MESSAGE_UNDONE_SUCCESS = "Item un-archived";
	private static final String MESSAGE_UNDONE_ERROR = "Unable to un-archive %s";

	/** Command Parameters **/
	private Item item;
	private String itemID;

	/**
	 * Constructor for DoneCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to move item to undone list.
	 * 
	 * @param args
	 * @param item
	 * @throws Exception
	 */
	public UndoneCommand(String itemID, Item item) throws Exception {
		this.itemID = itemID;
		this.item = item;
	}

	/**
	 * This method creates a executes the undone command. Which simply moves
	 * either (1) a done task to the not-done pile or (2) a done event to the
	 * not-done pile
	 * 
	 * @return message to show user
	 */
	public String execute() {
		try {
			undoneItem();
		} catch (IOException e) {
			return String.format(MESSAGE_UNDONE_ERROR, itemID);
		} finally {
			updateView();
		}

		return MESSAGE_UNDONE_SUCCESS;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	/**
	 * This method executes the undone command. Which either moves a task or
	 * event to its corresponding undone task or undone event pile.
	 */
	void undoneItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		int complementListIndex = Storage.getComplementListIndex(listIndex);
		Magical.getStorage().delete(listIndex, item);
		Magical.getStorage().create(complementListIndex, item);
		Magical.deleteDisplayList(listIndex, item);
		Magical.addDisplayList(complementListIndex, item);
	}
}
