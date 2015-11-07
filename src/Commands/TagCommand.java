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

	/** Messaging **/
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: tag <task_id> <tag name>";

	/** For Checking **/
	private static final String[] RESTRICTED = {"event", "events", "task", "tasks", "done"};
	
	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private Item prevItem;

	/**
	 * Constructor for TagCommand objects.
	 * Checks if arguments are valid and stores the correct arguments properly.
	 * Throws the appropriate exception if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public TagCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(args," ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();

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

	/**
	 * Set the relevant parameters of TagCommand to that of the specified task
	 */
	void setProperParams() {
		this.itemID = argsArray.get(0).trim();
		this.item = getItemByID(itemID);
		this.tags = new ArrayList<String>(argsArray.subList(1, count));
	}

	public boolean validNumArgs() {
		if (this.count < 2) {
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

		Set<String> currentTags = item.getTags();
		String duplicateTags = STRING_EMPTY;
		for(String tag : tags){
			if (currentTags.contains(tag)) {
				duplicateTags += duplicateTags.equals(STRING_EMPTY) ? tags : ", " + tags;
			} else {
				currentTags.add(tag);
				item.setTags(currentTags);
			}
		}
		if(duplicateTags.equals(STRING_EMPTY)){
			throw new Exception(itemID + " already has tag: " + duplicateTags);
		}

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

		return tags + " added to " + itemID;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
	public static void main(String[] args) throws Exception {
		TagCommand t = new TagCommand("uasgg asdgna");
	}
}
