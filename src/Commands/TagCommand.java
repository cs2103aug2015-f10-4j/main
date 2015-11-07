package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class TagCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: tag <task_id> <tag name>";

	private Item item;
	private String itemID;
	private String tag;
	private Item prevItem;

	public TagCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(
				Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			itemID = argsArray.get(0).trim();
			item = getItemByID(itemID);
			tag = argsArray.get(1).trim();

			if (item == null) {
				invalidArgs.add("itemID");
			}

			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
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
	 * This method executes the tag command. Which simply adds the specified tag
	 * to a task or event's tag set.
	 * 
	 * @param None
	 * @return message to show user
	 * @throws Exception 
	 */
	@Override
	public String execute() throws Exception {
		prevItem = item;
		item = prevItem.copy();

		Set<String> tags = item.getTags();
		if (tags.contains(tag)) {
			throw new Exception(itemID + " already has tag: " + tag);
		}
		tags.add(tag);
		item.setTags(tags);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.getStorage().update(listIndex, prevItem, item);
		} catch (IOException e) {
			throw new Exception("unable to add tag to " + itemID);
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

		return tag + " added to " + itemID;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
