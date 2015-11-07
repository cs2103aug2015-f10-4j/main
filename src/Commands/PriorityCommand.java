package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class PriorityCommand extends Command {

	private static final String MESSAGE_ARGUMENT_PARAMS = "Use Format: set <task id> <priority>";

	private Item item;
	private String itemID;
	private int priority;
	private Item prevItem;

	public PriorityCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(
				Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			itemID = argsArray.get(0).trim();
			item = getItemByID(itemID);
			priority = getPriority(argsArray.get(1).trim());

			if (item == null) {
				invalidArgs.add("itemID");
			}
			if (priority == -1) {
				invalidArgs.add("priority");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_PARAMS);
		}
	}

	public boolean validNumArgs() {
		if (this.count != 2) {
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
