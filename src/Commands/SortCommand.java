package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import gui.GUIModel;
import main.Item;

public class SortCommand extends Command {

	private String sortParam;

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: sort <priority OR date OR title>";

	public SortCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.sortParam = argsArray.get(0).trim().toLowerCase();

		if (validNumArgs()) {
			if (sortParam.equals(STRING_EMPTY)) {
				sortParam = "priority";
			} else if (!isValidSortParam()) {
				invalidArgs.add("sort parameter");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
		}
	}

	private boolean isValidSortParam() {
		switch (sortParam) {
		case "priority":
			return true;
		case "title":
			return true;
		case "date":
			return true;
		default:
			return false;
		}
	}

	/**
	 * This method executes the sort command. Which sorts all the tasks and
	 * events currently displayed by the GUI. Sorting is done using the
	 * parameter specified.
	 * 
	 * @param None
	 *            .
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
		switch (sortParam) {
		case "priority":
			Collections.sort(sortedTaskList, Item.Comparators.PRIORITY);
			Collections.sort(sortedTaskDoneList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventList, Item.Comparators.PRIORITY);
			Collections.sort(sortedEventDoneList, Item.Comparators.PRIORITY);
			break;
		case "date":
			Collections.sort(sortedTaskList, Item.Comparators.DATE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.DATE);
			Collections.sort(sortedEventList, Item.Comparators.DATE);
			Collections.sort(sortedEventDoneList, Item.Comparators.DATE);
			break;
		case "title":
			Collections.sort(sortedTaskList, Item.Comparators.TITLE);
			Collections.sort(sortedTaskDoneList, Item.Comparators.TITLE);
			Collections.sort(sortedEventList, Item.Comparators.TITLE);
			Collections.sort(sortedEventDoneList, Item.Comparators.TITLE);
		default:
			break;
		}
		GUIModel.setTaskList(sortedTaskList);
		GUIModel.setTaskDoneList(sortedTaskDoneList);
		GUIModel.setEventList(sortedEventList);
		GUIModel.setEventDoneList(sortedEventDoneList);
		return "sort successful";
	}

	@Override
	public boolean validNumArgs() {
		if (count > 1) {
			return false;
		} else {
			return true;
		}
	}

}
