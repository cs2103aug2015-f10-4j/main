package command;

import java.io.IOException;
import main.Magical;
import main.Storage;
import main.Item;

public class UndoneCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: undone <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_UNDONE = "Undone tasks cannot be undone";
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
	 * @throws Exception
	 */
	public UndoneCommand(String args) throws Exception {
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

	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
	}

	/**
	 * This method executes the undone command. Which either moves a task or
	 * event to its corresponding undone task or undone event pile.
	 */
	void undoneItem() throws IOException {
		int listIndex = Storage.getListIndex(argsArray.get(0));
		int complementListIndex = Storage.getComplementListIndex(listIndex);
		Magical.getStorage().delete(listIndex, item);
		Magical.getStorage().create(complementListIndex, item);
	}

	public boolean validNumArgs() {
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}
}
