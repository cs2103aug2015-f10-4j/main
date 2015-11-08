package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class DelCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: delete <item_id>";

	private Item item;
	private String itemID;

	public DelCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(
				STRING_EMPTY, 1)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			itemID = argsArray.get(0).trim();
			item = getItemByID(itemID);

			if (item == null) {
				invalidArgs.add("item_id");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(String.format(
						MESSAGE_HEADER_INVALID, invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
		}
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
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.getStorage().delete(listIndex, item);
			return itemID + " deleted";
		} catch (IOException e) {
			return "unable to delete " + itemID;
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
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
