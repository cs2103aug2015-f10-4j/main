package parser;

import java.util.ArrayList;
import command.Command;
import command.ShowCommand;

/**
 * @@author A0129654X
 */
public class ShowParser extends ArgsParserAbstract {

	/** Command parameters **/
	private ArrayList<String> tags;
	private String type;

	/**
	 * Constructor for ShowParser objects. Sets the command parameters with the
	 * proper inputs. Contains methods to display items that are tasks, events,
	 * containing specified tags, or all items
	 * 
	 * @param args
	 * @throws Exception
	 */
	public ShowParser(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(" ", -1);
		this.count = argsArray.size();

		setProperParams();
	}

	/**
	 * Sets the type of the show command to be all if no arguments are given, or
	 * to display tags if event/task is not given as the argument.
	 */
	void checkType() {
		if (type.equals("")) {
			this.type = "all";
		} else if (!type.equalsIgnoreCase("task")
				&& !type.equalsIgnoreCase("tasks")
				&& !type.equalsIgnoreCase("event")
				&& !type.equalsIgnoreCase("events")) {
			this.type = "tag";
		}
	}

	@Override
	void setProperParams() {
		this.type = argsArray.get(0).trim();
		this.tags = null;
		checkType();
		this.tags = this.argsArray;
	}

	@Override
	boolean validNumArgs() {
		return true;
	}

	@Override
	Command getCommand() throws Exception {
		Command show = new ShowCommand(this.type, this.tags);
		return show;
	}
}
