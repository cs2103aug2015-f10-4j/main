package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import gui.GUIModel;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Task;

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
		ArrayList<Task> taskList = Magical.storage.getList(Storage.TASKS_INDEX);
		ArrayList<Task> taskDoneList = Magical.storage
				.getList(Storage.TASKS_DONE_INDEX);
		ArrayList<Task> eventList = Magical.storage
				.getList(Storage.EVENTS_INDEX);
		ArrayList<Task> eventDoneList = Magical.storage
				.getList(Storage.EVENTS_DONE_INDEX);
		ArrayList<Task> filteredTaskList = new ArrayList<Task>();
		ArrayList<Task> filteredTaskDoneList = new ArrayList<Task>();
		ArrayList<Task> filteredEventList = new ArrayList<Task>();
		ArrayList<Task> filteredEventDoneList = new ArrayList<Task>();
		for (Task t : taskList) {
			if (t.getDueDate() != null
					&& t.getDueDate().compareTo(startDate) >= 0
					&& t.getDueDate().compareTo(endDate) <= 0) {
				filteredTaskList.add(t);
			}
		}
		for (Task t : taskDoneList) {
			if (t.getDueDate() != null
					&& t.getDueDate().compareTo(startDate) >= 0
					&& t.getDueDate().compareTo(endDate) <= 0) {
				filteredTaskDoneList.add(t);
			}
		}
		for (Task t : eventList) {
			if (t.getDueDate() != null
					&& t.getDueDate().compareTo(startDate) >= 0
					&& t.getDueDate().compareTo(endDate) <= 0) {
				filteredEventList.add(t);
			}
		}
		for (Task t : eventDoneList) {
			if (t.getDueDate() != null
					&& t.getDueDate().compareTo(startDate) >= 0
					&& t.getDueDate().compareTo(endDate) <= 0) {
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
