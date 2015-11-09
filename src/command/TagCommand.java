package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import main.Magical;
import main.Storage;
import main.Item;

public class TagCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: tag <item_id> <tag name>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_TAG_PRESENT = "%s already has tag: ";
	private static final String MESSAGE_TAG_RESTRICTED = "%s cannot have tag: ";
	private static final String MESSAGE_TAG_ERROR = "Unable to add tag to %s";
	private static final String MESSAGE_TAG_ADDED = "%s added to %s";

	/** For Checking **/
	private static final ArrayList<String> RESTRICTED = new ArrayList<String>(
			Arrays.asList("event", "events", "task", "tasks", "done"));

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private Item prevItem;
	private String presentTags = STRING_EMPTY;
	private String invalidTags = STRING_EMPTY;

	/**
	 * Constructor for TagCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public TagCommand(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkItemExists();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
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
	 * Adds error message if item does not exist or unable to get
	 */
	void checkItemExists() {
		if (item == null) {
			invalidArgs.add(MESSAGE_INVALID_ITEM_ID);
		}
	}

	/**
	 * Check if a tag is restricted and add to return message invalidTags if it
	 * is. Returns true if tag is restricted, or false otherwise.
	 * 
	 * @param currentTags
	 * @param tag
	 * @return boolean
	 */
	private boolean checkRestricted(Set<String> currentTags, String tag) {
		if (RESTRICTED.contains(tag.toLowerCase())) {
			if (invalidTags.equals(STRING_EMPTY)) {
				invalidTags = String.format(MESSAGE_TAG_RESTRICTED, itemID)
						+ tag;
			} else {
				invalidTags += ", " + tag;
			}
		}
		return false;
	}

	/**
	 * Check if a tag is present inside a set of tags and add to return message
	 * presentTags if it is. Returns true if there are present tags, or false
	 * otherwise.
	 * 
	 * @param currentTags
	 * @param tag
	 * @return boolean
	 */
	private boolean checkTags(Set<String> currentTags, String tag) {
		if (currentTags.contains(tag)) {
			if (presentTags.equals(STRING_EMPTY)) {
				presentTags = String.format(MESSAGE_TAG_PRESENT, itemID) + tag;
				return true;
			} else {
				presentTags += ", " + tag;
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
	 * Throws exception if error messages for invalid tags or tags that are
	 * already present
	 * 
	 * @throws IllegalArgumentException
	 */
	void errorPresentORInvalidTags() throws Exception {
		if (!presentTags.equals(STRING_EMPTY)
				&& !invalidTags.equals(STRING_EMPTY)) {
			throw new Exception(presentTags + " AND " + invalidTags);
		} else if (!presentTags.equals(STRING_EMPTY)) {
			throw new Exception(presentTags);
		} else if (!invalidTags.equals(STRING_EMPTY)) {
			throw new Exception(invalidTags);
		}
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
			if (!checkTags(currentTags, tag)
					&& !checkRestricted(currentTags, tag)) {
				addTagToItem(currentTags, tag);
			}
		}
		errorPresentORInvalidTags();

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
		Magical.updateDisplayList(listIndex, prevItem, item);
	}

	public boolean validNumArgs() {
		if (this.count < 2) {
			return false;
		} else {
			return true;
		}
	}
}
