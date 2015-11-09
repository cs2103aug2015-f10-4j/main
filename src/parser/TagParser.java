package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import command.Command;
import command.TagCommand;
import main.Item;

/**
 * @@author A0129654X
 */
public class TagParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: tag <item_id> <tag name>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_TAG_PRESENT = "%s already has tag: ";
	private static final String MESSAGE_TAG_RESTRICTED = "%s cannot have tag: ";
	
	/** For Checking **/
	private static final ArrayList<String> RESTRICTED = new ArrayList<String>(
			Arrays.asList("event", "events", "task", "tasks", "done"));

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
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
	public TagParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			
			checkItemExists();
			errorInvalidArgs();
			
			checkTags();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}

	/**
	 * Checks if tags already exist or are restricted
	 */
	void checkTags() {
		Set<String> currentTags = item.getTags();
		for (String tag : tags) {
			checkTagsExist(currentTags, tag);
			checkRestricted(currentTags, tag);
		}
		addTagExistError();
		
		addTagRestrictedError();
	}

	/**
	 * Adds error message if given tags are restricted
	 */
	void addTagRestrictedError() {
		if(!invalidTags.equals("")){
			invalidArgs.add(invalidTags);
		}
	}

	/**
	 * Adds error message if given tags already exist
	 */
	void addTagExistError() {
		if(!presentTags.equals("")){
			invalidArgs.add(presentTags);
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
	 * Check if a tag is present inside a set of tags and add to return message
	 * presentTags if it is. Returns true if there are present tags, or false
	 * otherwise.
	 * 
	 * @param currentTags
	 * @param tag
	 * @return boolean
	 */
	private boolean checkTagsExist(Set<String> currentTags, String tag) {
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
	
	@Override
	void setProperParams() {
		this.itemID = argsArray.get(0).trim();
		this.item = getItemByID(itemID);
		this.tags = new ArrayList<String>(argsArray.subList(1, count));
	}

	@Override
	boolean validNumArgs() {
		if (this.count < 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() throws Exception {
		Command tag = new TagCommand(this.itemID, this.item, this.tags);
		return tag;
	}
}
