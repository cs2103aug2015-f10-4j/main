package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import gui.GUIModel;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Item;

public class DateCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: date <start date> <end date>";

	private CustomDate startDate;
	private CustomDate endDate;

	public DateCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList(Arrays.asList(args.split("\\s")));

		System.out.println(argsArray);

		// checking if the 2 consecutive elements belong together as a date
		if (argsArray.size() > 1) {
			int i = 0;
			while (i + 1 < argsArray.size()) {

				if (getDate(argsArray.get(i)) == null
						&& getDate(argsArray.get(i + 1)) != null) {
					argsArray.set(i,
							argsArray.get(i) + " " + argsArray.get(i + 1));
					argsArray.remove(i + 1);
				}
				i++;
				System.out.println(argsArray);
			}
		}
		this.count = argsArray.size();

		if (validNumArgs()) {
			String start = argsArray.get(0).trim();
			String end = count == 2 ? argsArray.get(1).trim() : STRING_EMPTY;

			startDate = start.equals(STRING_EMPTY) ? new CustomDate(new Date(0))
					: getDate(start);
			endDate = end.equals(STRING_EMPTY) ? new CustomDate(new Date(
					Long.MAX_VALUE)) : getDate(end);

			if (startDate == null) {
				invalidArgs.add("start date");
			}

			if (endDate == null) {
				invalidArgs.add("end date");
			}

			if (startDate != null && endDate != null && !validDateRange()) {
				invalidArgs.add("End date is earlier than start date");
			}

			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
		}
	}

	public boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validDateRange() {
		return endDate.compareTo(startDate) != -1;
	}

	/**
	 * This method executes the date command. Which simply filters through all
	 * the tasks and events and lists only those that fall within the specified
	 * date range.
	 * 
	 * @param None
	 *            .
	 * @return None
	 */
	@Override
	public String execute() {
		ArrayList<Item> taskList = Magical.getStorage().getList(
				Storage.TASKS_INDEX);
		ArrayList<Item> taskDoneList = Magical.getStorage().getList(
				Storage.TASKS_DONE_INDEX);
		ArrayList<Item> eventList = Magical.getStorage().getList(
				Storage.EVENTS_INDEX);
		ArrayList<Item> eventDoneList = Magical.getStorage().getList(
				Storage.EVENTS_DONE_INDEX);
		ArrayList<Item> filteredTaskList = new ArrayList<Item>();
		ArrayList<Item> filteredTaskDoneList = new ArrayList<Item>();
		ArrayList<Item> filteredEventList = new ArrayList<Item>();
		ArrayList<Item> filteredEventDoneList = new ArrayList<Item>();
		for (Item t : taskList) {
			if (t.getEndDate() != null
					&& t.getEndDate().compareTo(startDate) >= 0
					&& t.getEndDate().compareTo(endDate) <= 0) {
				filteredTaskList.add(t);
			}
		}
		for (Item t : taskDoneList) {
			if (t.getEndDate() != null
					&& t.getEndDate().compareTo(startDate) >= 0
					&& t.getEndDate().compareTo(endDate) <= 0) {
				filteredTaskDoneList.add(t);
			}
		}
		for (Item t : eventList) {
			if (t.getEndDate() != null
					&& t.getEndDate().compareTo(startDate) >= 0
					&& t.getEndDate().compareTo(endDate) <= 0) {
				filteredEventList.add(t);
			}
		}
		for (Item t : eventDoneList) {
			if (t.getEndDate() != null
					&& t.getEndDate().compareTo(startDate) >= 0
					&& t.getEndDate().compareTo(endDate) <= 0) {
				filteredEventDoneList.add(t);
			}
		}
		GUIModel.setTaskList(filteredTaskList);
		GUIModel.setTaskDoneList(filteredTaskDoneList);
		GUIModel.setEventList(filteredEventList);
		GUIModel.setEventDoneList(filteredEventDoneList);
		return "date command successful";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
