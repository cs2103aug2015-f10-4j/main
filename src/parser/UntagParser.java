package parser;

import java.util.ArrayList;
import java.util.Set;

import command.Command;
import command.UntagCommand;
import main.Item;

/**
 * @@author A0129654X
 */
public class UntagParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: untag <item_id> <tag name>";
	private static final String MESSAGE_INVALID_ITEM_ID = "item_id";
	private static final String MESSAGE_TAG_ABSENT = "%s does not have tag: ";

	/** Command Parameters **/
	private Item item;
	private String itemID;
	private ArrayList<String> tags;
	private String absentTags = STRING_EMPTY;
	
	/**
	 * Constructor for UntagCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid
	 * 
	 * @param args
	 * @throws Exception
	 */
	public UntagParser(String args) throws Exception {
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
			checkTagsNotExist(currentTags, tag);
		}
		addTagsAbsentError();
	}

	/**
	 * Adds error message if given tags are absent from item
	 */
	void addTagsAbsentError() {
		if(!absentTags.equals("")){
			invalidArgs.add(absentTags);
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
	private boolean checkTagsNotExist(Set<String> currentTags, String tag) {
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
		Command untag = new UntagCommand(this.itemID, this.item, this.tags);
		return untag;
	}
}
