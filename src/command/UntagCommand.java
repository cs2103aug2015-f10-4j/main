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
public class UntagCommand extends Command {

	private static final String MESSAGE_TAG_ERROR = "Unable to remove tag to %s";
	private static final String MESSAGE_TAG_REMOVED = "%s removed from %s";

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private Item prevItem;

	/**
	 * Constructor for UntagCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @param tags 
	 * @param item 
	 * @throws Exception
	 */
	public UntagCommand(String itemID, Item item, ArrayList<String> tags) throws Exception {
		this.itemID = itemID;
		this.item = item;
		this.tags = tags;
	}

	/**
	 * Make 2 copies of the item to be stored in prevItem and item
	 */
	void duplicateItem() {
		prevItem = item;
		item = prevItem.copy();
	}

	/**
	 * This method executes the untag command. Which simply removes the
	 * specified tags from a task or event's tag set.
	 * 
	 * @param None
	 * @return message to show user
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		duplicateItem();
		Set<String> currentTags = item.getTags();
		
		for (String tag : tags) {
			removeTagFromItem(currentTags, tag);
		}

		try {
			updateItem();
		} catch (IOException e) {
			throw new Exception(String.format(MESSAGE_TAG_ERROR, itemID));
		} finally {
			updateView();
		}

		return String.format(MESSAGE_TAG_REMOVED, tags, itemID);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	/**
	 * Removes given tag from set of tags and set item tags as this set
	 * 
	 * @param currentTags
	 * @param tag
	 */
	void removeTagFromItem(Set<String> currentTags, String tag) {
		currentTags.remove(tag.toLowerCase());
		item.setTags(currentTags);
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
