package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class TagCommand extends Command {

	private static final String MESSAGE_TAG_ERROR = "Unable to add tag to %s";
	private static final String MESSAGE_TAG_ADDED = "%s added to %s";

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private Item prevItem;

	/**
	 * Constructor for TagCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @param tags2
	 * @param item2
	 * @throws Exception
	 */
	public TagCommand(String itemID, Item item, ArrayList<String> tags)
			throws Exception {
		this.itemID = itemID;
		this.item = item;
		this.tags = tags;
	}

	/**
	 * Adds given tag to set of tags and set item tags as this set
	 * 
	 * @param currentTags
	 * @param tag
	 */
	void addTagToItem(Set<String> currentTags, String tag) {
		currentTags.add(tag.toLowerCase());
		item.setTags(currentTags);
	}

	/**
	 * Make 2 copies of the item to be stored in prevItem and item
	 */
	void duplicateItem() {
		prevItem = item;
		item = prevItem.copy();
	}

	/**
	 * This method executes the tag command. Which simply adds the specified tag
	 * to a task or event's tag set.
	 * 
	 * @return message to show user
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {

		duplicateItem();
		Set<String> currentTags = item.getTags();
		for (String tag : tags) {
			addTagToItem(currentTags, tag);
		}

		try {
			updateItem();
		} catch (IOException e) {
			return String.format(MESSAGE_TAG_ERROR, itemID);
		} finally {
			updateView();
		}

		return String.format(MESSAGE_TAG_ADDED, tags, itemID);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	/**
	 * Updates the original item with the new modified item
	 * 
	 * @throws IOException
	 */
	void updateItem() throws IOException {
		int listIndex = Storage.getListIndex(itemID);
		Magical.getStorage().update(listIndex, prevItem, item);
		Magical.updateDisplayList(listIndex, prevItem, item);
	}
}
