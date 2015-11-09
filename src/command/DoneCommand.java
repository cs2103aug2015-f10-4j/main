package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class DoneCommand extends Command {

	private static final String MESSAGE_DONE_ERROR = "Unable to archive %s";
	private static final String MESSAGE_DONE_SUCCESS = "%s archived";

	/** Command Parameters **/
	private Item item;
	private String itemID;

	/**
	 * Constructor for DoneCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to move item to done list.
	 * 
	 * @param args
	 * @param item
	 * @throws Exception
	 */
	public DoneCommand(String itemID, Item item) throws Exception {
		this.itemID = itemID;
		this.item = item;
	}

	/**
	 * Removes item from list and adds to complement(done) list
	 * 
	 * @throws IOException
	 */
	void doneItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		int complementListIndex = Storage.getComplementListIndex(listIndex);
		Magical.getStorage().delete(listIndex, item);
		Magical.getStorage().create(complementListIndex, item);
		Magical.deleteDisplayList(listIndex, item);
		Magical.addDisplayList(complementListIndex, item);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	/**
	 * This method executes the done command. Which either moves a task or event
	 * to its corresponding done task or done event pile. .
	 * 
	 * @return message to show user
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		try {
			doneItem();
		} catch (IOException e) {
			throw new Exception(String.format(MESSAGE_DONE_ERROR, itemID));
		} finally {
			updateView();
		}

		return String.format(MESSAGE_DONE_SUCCESS, itemID);
	}
}
