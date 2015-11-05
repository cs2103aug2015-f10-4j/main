package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class PriorityCommand extends Command {

	private static final String MESSAGE_ARGUMENT_PARAMS = "Use Format: set <task id> <priority>";

	private Task task;
	private int priority;
	private Task prevTask;

	public PriorityCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(
				Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			task = getTaskByID(argsArray.get(0).trim());
			priority = getPriority(argsArray.get(1).trim());

			if (task == null) {
				invalidArgs.add("taskID");
			}
			if (priority == -1) {
				invalidArgs.add("priority");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID
						+ String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_ARGUMENT_PARAMS);
		}
	}

	public boolean validNumArgs() {
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method executes the priority command. Which simply changes the
	 * priority of the selected task or event to the new priority specified.
	 * 
	 * @param None
	 *            .
	 * @return message to show user
	 */
	@Override
	public String execute() {
		prevTask = task;
		task = prevTask.copy();
		task.setPriority(priority);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.update(listIndex, prevTask, task);
		} catch (IOException e) {
			return "unable to change priority";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage
					.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage
					.getList(Storage.EVENTS_DONE_INDEX));
		}

		return "Priority updated.";
	}
}
