package command;

import java.io.IOException;

import main.Magical;
import main.Storage;
import main.Item;

public class DoneCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: done <item_id>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_INVALID_DONE = "%s is already done!";
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
	 * @throws Exception
	 */
	public DoneCommand(String args) throws Exception {
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

	/**
	 * Removes item from list and adds to complement(done) list
	 * 
	 * @throws IOException
	 */
	void doneItem() throws IOException {
		int listIndex = Storage.getListIndex(argsArray.get(0));
		int complementListIndex = Storage.getComplementListIndex(listIndex);
		Magical.getStorage().delete(listIndex, item);
		Magical.getStorage().create(complementListIndex, item);
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

	@Override
	public boolean isUndoable() {
		return true;
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
}
