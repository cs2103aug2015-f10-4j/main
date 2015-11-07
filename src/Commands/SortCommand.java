package Commands;

import java.util.ArrayList;
import java.util.Collections;

import gui.GUIModel;
import main.Item;

public class SortCommand extends Command {

	private ArrayList<String> sortParams;

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: sort <parameter> (upto 3 parameters)";

	public SortCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(args, " ", 3);
		this.count = argsArray.size();
		this.sortParams = argsArray;

		if (validNumArgs()) {
			if (sortParams.size() == 1 && sortParams.get(0).isEmpty()) {
				sortParams.add("priority");
				sortParams.add("date");
				sortParams.add("title");
			} else if (!isValidSortParams()) {
				invalidArgs.add("parameters");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
		}
	}

	private boolean isValidSortParams() {
		for (String param : sortParams) {
			if (!(param.equals("priority") || param.equals("title") || param.equals("date"))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method executes the sort command. Which sorts all the tasks and
	 * events currently displayed by the GUI. Sorting is done using the
	 * parameter specified.
	 * 
	 * @param None
	 * @return message to show user
	 */
	@Override
	public String execute() throws Exception {
		ArrayList<Item> sortedTaskList = new ArrayList<Item>(
				GUIModel.getTaskList());
		ArrayList<Item> sortedTaskDoneList = new ArrayList<Item>(
				GUIModel.getTaskDoneList());
		ArrayList<Item> sortedEventList = new ArrayList<Item>(
				GUIModel.getEventList());
		ArrayList<Item> sortedEventDoneList = new ArrayList<Item>(
				GUIModel.getEventDoneList());
		
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
		
		GUIModel.setTaskList(sortedTaskList);
		GUIModel.setTaskDoneList(sortedTaskDoneList);
		GUIModel.setEventList(sortedEventList);
		GUIModel.setEventDoneList(sortedEventDoneList);
		return "sort successful";
	}

	@Override
	public boolean validNumArgs() {
		if (count > 3) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
