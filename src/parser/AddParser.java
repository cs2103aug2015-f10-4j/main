package parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import command.Command;
import main.CustomDate;

public class AddParser extends ArgsParserSkeleton{
	
	/** Messaging */
	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time>";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_INVALID_DATETIME = "Date/Time";

	/** Command parameters **/
	private String title;
	private CustomDate dueDate;
	private int endTime;
	private boolean isFloat;

	/**
	 * Constructor for AddCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to add a task to storage.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public AddParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs("\\sby\\s", -1);
		removeEscapeCharacters();
		this.count = argsArray.size();
		splitArgsAfterDateTime();
		this.count = argsArray.size();
		this.isFloat = false;

		for (int i = 0; i < count; i++) {
			assertNotNull(argsArray.get(i));
		}

		if (validNumArgs()) {
			if (count == 1) {
				setFloatParams();
			} else {
				setProperParams();
			}

			checkTitle();
			checkDateTime();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Replaces characters that were used for escaping the keyword argument that
	 * was used for splitting
	 */
	private void removeEscapeCharacters() {
		for (int i = 0; i < argsArray.size(); i++) {
			argsArray.set(
					i,
					argsArray.get(i).trim()
							.replaceAll("(?<=by)\"|\"(?=by)", STRING_EMPTY));
		}
	}
	
	/**
	 * Date/time argument might be concatenated with other arguments, thus the
	 * method splits the arguments properly
	 */
	private void splitArgsAfterDateTime() {
		if (argsArray.size() > 1 && argsArray.get(count - 1).contains(" ")) {
			while (true) {
				String last = getLastWord(argsArray.get(count - 1));
				if (getDate(last) != null) {
					break;
				} else {
					splitOnce(last);
				}
			}
		}
	}
	
	/**
	 * Gives last word of a string
	 * 
	 * @param string
	 * @return String last word
	 */
	private String getLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[1];
	}
	
	/**
	 * Adds last word to the argsArray and removes it from the date/time
	 * argument
	 * 
	 * @param last
	 */
	private void splitOnce(String last) {
		argsArray.add(count, last);
		argsArray.set(count - 1, removeLastWord(argsArray.get(count - 1)));
	}
	
	/**
	 * Removes last word from a string
	 * 
	 * @param string
	 * @return String with last word removed
	 */
	private String removeLastWord(String string) {
		return string.split("\\s(?=\\S+$)")[0];
	}
	
	/**
	 * Set the relevant parameters of AddCommand to that of a floating task
	 */
	private void setFloatParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dueDate = null;
		this.endTime = -1;
		this.isFloat = true;
	}
	
	/**
	 * Set the relevant parameters of AddCommand to that of the specified task
	 */
	void setProperParams() {
		this.title = getTitle(argsArray.get(0).trim());
		this.dueDate = getDate(argsArray.get(1).trim());
		this.endTime = dueDate == null ? -1 : dueDate.getTime();
		assertFalse(isFloat);
	}
	
	/**
	 * Adds error message if title is invalid
	 */
	private void checkTitle() {
		if (title == null) {
			invalidArgs.add(MESSAGE_INVALID_TITLE);
		}
	}
	
	/**
	 * Adds error message if invalid date and time specified, and task to be
	 * added is not a floating task
	 */
	private void checkDateTime() {
		if (this.dueDate == null && !isFloat) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME);
		}
	}

	@Override
	boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns an AddCommand object with the proper parameters
	 * @return
	 */
	Command getCommand(){
	//	Command add = new AddCommand(this.title, this.dueDate, this.endTime);
	//	assertNotNull(add);
	//	return add;
		return null;
	}
}
