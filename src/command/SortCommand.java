package command;

import java.util.ArrayList;
import java.util.Collections;

import gui.GUIModel;
import main.Item;

public class SortCommand extends Command {
	
	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: sort <parameter> (upto 3 parameters)";
	private static final String MESSAGE_INVALID_PARAMS = "Parameters";
	private static final String MESSAGE_SORT_SUCCESS = "sort successful";
	
	/** Command parameters **/
	private ArrayList<String> sortParams;

	/**
	 * Constructor for SortCommand objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to sort the displayed tasks.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SortCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(" ", 3);
		this.count = argsArray.size();
		
		setProperParams();

		if (validNumArgs()) {
			checkParams();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}

	/**
	 * Set the sorting parameters to all types if none are specified, else 
	 * add error message
	 */
	void checkParams() {
		if (sortParams.size() == 1 && sortParams.get(0).isEmpty()) {
			sortParams.add("priority");
			sortParams.add("date");
			sortParams.add("title");
		} else if (!isValidSortParams()) {
			invalidArgs.add(MESSAGE_INVALID_PARAMS);
		}
	}

	/**
	 * This method executes the sort command. Which sorts all the tasks and
	 * events currently displayed by the GUI. Sorting is done using the
	 * parameter specified.
	 * 
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		
		ArrayList<Item> sortedTaskList = new ArrayList<Item>(GUIModel.getTaskList());
		ArrayList<Item> sortedTaskDoneList = new ArrayList<Item>(GUIModel.getTaskDoneList());
		ArrayList<Item> sortedEventList = new ArrayList<Item>(GUIModel.getEventList());
		ArrayList<Item> sortedEventDoneList = new ArrayList<Item>(GUIModel.getEventDoneList());
		
		if (sortParams.contains("title")) {
			Collections.sort(sortedTaskList, Item.Comparators.TITLE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.TITLE);
			Collections.sort(sortedEventList, Item.Comparators.TITLE);
			Collections.sort(sortedEventDoneList, Item.Comparators.TITLE);
		}
		if (sortParams.contains("date")) {
			Collections.sort(sortedTaskList, Item.Comparators.DATE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.DATE);
			Collections.sort(sortedEventList, Item.Comparators.DATE);
			Collections.sort(sortedEventDoneList, Item.Comparators.DATE);
		}
		if (sortParams.contains("priority")) {
			Collections.sort(sortedTaskList, Item.Comparators.PRIORITY);
			Collections.sort(sortedTaskDoneList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventDoneList, Item.Comparators.PRIORITY);
		}
		
		updateView(sortedTaskList, sortedTaskDoneList, sortedEventList, sortedEventDoneList);
		
		return MESSAGE_SORT_SUCCESS;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	/**
	 * Returns true if sort parameters are valid (priority, title, date), or false otherwise
	 * @return whether sort paramters are valid
	 */
	private boolean isValidSortParams() {
		for (String param : sortParams) {
			if (!(param.equals("priority") || param.equals("title") || param.equals("date"))) {
				return false;
			}
		}
		return true;
	}

	void setProperParams() {
		this.sortParams = argsArray;
	}

	@Override
	public boolean validNumArgs() {
		if (count > 3) {
			return false;
		} else {
			return true;
		}
	}

}
