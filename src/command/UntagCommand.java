package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class UntagCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: untag <item_id> <tag name>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_TAG_ABSENT = "%s does not have tag: ";
	private static final String MESSAGE_TAG_ERROR = "Unable to remove tag to %s";
	private static final String MESSAGE_TAG_REMOVED = "%s removed from %s";

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private Item prevItem;
	private String absentTags = STRING_EMPTY;

	/**
	 * Constructor for UntagCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public UntagCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(" ", -1);
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
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		}
	}

	/**
	 * Check if a tag is absent in a set of tags and add to return message
	 * absentTags if it is not. Returns true if there are absent tags, or false
	 * otherwise.
	 * 
	 * @param currentTags
	 * @param tag
	 * @return boolean
	 */
	private boolean checkTags(Set<String> currentTags, String tag) {
		if (!currentTags.contains(tag)) {
			if (absentTags.equals(STRING_EMPTY)) {
				absentTags = String.format(MESSAGE_TAG_ABSENT, itemID) + tag;
				return true;
			} else {
				absentTags += ", " + tag;
				return true;
			}
		}
		return false;
	}

	/**
	 * Make 2 copies of the item to be stored in prevItem and item
	 */
	void duplicateItem() {
		prevItem = item;
		item = prevItem.copy();
	}

	/**
	 * Throws exception if error messages if tags are absent
	 * 
	 * @throws IllegalArgumentException
	 */
	void errorAbsentTags() throws Exception {
		if (!absentTags.equals(STRING_EMPTY)) {
			throw new Exception(absentTags);
		}
	}

	/**
	 * Throws exception if error messages for invalid arguments are present
	 * 
	 * @throws IllegalArgumentException
	 */
	protected void errorInvalidArgs() throws IllegalArgumentException {
		if (invalidArgs.size() > 0) {
			throw new IllegalArgumentException(String.format(
					MESSAGE_HEADER_INVALID, invalidArgs));
		}
	}

	/**
	 * Throws exception if error messages for format are present
	 * 
	 * @throws IllegalArgumentException
	 */
	private void errorInvalidFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
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
			if (!checkTags(currentTags, tag)) {
				removeTagFromItem(currentTags, tag);
			}
		}
		
		errorAbsentTags();

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
	 * Set the relevant parameters of UntagCommand to that of the specified task
	 */
	void setProperParams() {
		this.itemID = argsArray.get(0).trim();
		this.item = getItemByID(itemID);
		this.tags = new ArrayList<String>(argsArray.subList(1, count));
	}

	/**
	 * Updates the original item with the new modified item
	 * 
	 * @throws IOException
	 */
	void updateItem() throws IOException {
		int listIndex = Storage.getListIndex(argsArray.get(0));
		Magical.getStorage().update(listIndex, prevItem, item);
	}

	public boolean validNumArgs() {
		if (this.count < 2) {
			return false;
		} else {
			return true;
		}
	}
}
