package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class PriorityCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_ARGUMENT_PARAMS = "Use Format: set <item_id> <priority>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";

	/** Command parameters **/
	private Item item;
	private String itemID;
	private String priority;
	private Item prevItem;

	/**
	 * Constructor for PriorityCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid. Contains methods to 
	 * change the priority of an item.
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
			
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(String.format(
						MESSAGE_HEADER_INVALID, invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_PARAMS);
		}
	}

	/**
	 *  Adds error message if priority given is invalid
	 */
	void checkPriorityValid() {
		if (priority == null) {
			invalidArgs.add("Priority");
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

	void setProperParams() {
		itemID = argsArray.get(0).trim();
		item = getItemByID(itemID);
		if (argsArray.size() == 1) {
			priority = "";
		} else {
			priority = getPriority(argsArray.get(1).trim());
		}
	}

	public boolean validNumArgs() {
		if (this.count > 2 && this.count < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method executes the priority command. Which simply changes the
	 * priority of the selected task or event to the new priority specified.
	 * 
	 * @param None
	 * @return message to show user
	 */
	@Override
	public String execute() {
		prevItem = item;
		item = prevItem.copy();
		item.setPriority(priority);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.getStorage().update(listIndex, prevItem, item);
		} catch (IOException e) {
			return "unable to change priority for " + itemID;
		} finally {
			GUIModel.setTaskList(Magical.getStorage().getList(
					Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.getStorage().getList(
					Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.getStorage().getList(
					Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.getStorage().getList(
					Storage.EVENTS_DONE_INDEX));
		}

		return "priority updated for " + itemID;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
