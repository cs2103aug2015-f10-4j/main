package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class DelCommand extends Command {

	private static final String MESSAGE_ITEM_DELETED = "%s deleted";

	private static final String MESSAGE_ERROR_DELETE = "Unable to delete";

	private static final String MESSAGE_INVALID_FORMAT = "Use Format: delete <item_id>";

	private Item item;
	private String itemID;

	/**
	 * Constructor for DelCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DelCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(STRING_EMPTY, -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();

			checkItemExists();
			
			errorInvalidArgs();
			
		} else {
			errorInvalidFormat();
		}
	}

	/**
	 * Throws exception if error messages for format are present
	 * 
	 * @throws IllegalArgumentException
	 */
	void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
	}

	/**
	 * Throws exception if error messages for invalid arguments are present
	 * 
	 * @throws IllegalArgumentException
	 */
	void errorInvalidArgs() throws IllegalArgumentException {
		if (invalidArgs.size() > 0) {
			throw new IllegalArgumentException(String.format(
					MESSAGE_HEADER_INVALID, invalidArgs));
		}
	}

	/**
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add("item_id");
		}
	}
	
	/**
	 * Set the relevant parameters of DelCommand to that of the specified task
	 */
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

	/**
	 * This method executes the delete command. Which simply deletes the
	 * specified task or event from the database.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() {
		try {
			removeItem();
			return String.format(MESSAGE_ITEM_DELETED, itemID);
		} catch (IOException e) {
			return MESSAGE_ERROR_DELETE;
		} finally {
			updateView();
		}
	}

	/**
	 * Removes the item from storage
	 * @throws IOException
	 */
	void removeItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().delete(listIndex, item);
	}

	/**
	 * Updates the new view in the GUI
	 */
	void updateView() {
		GUIModel.setTaskList(Magical.getStorage().getList(
				Storage.TASKS_INDEX));
		GUIModel.setTaskDoneList(Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX));
		GUIModel.setEventList(Magical.getStorage().getList(
				Storage.EVENTS_INDEX));
		GUIModel.setEventDoneList(Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX));
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
