package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class UndoneCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: undone <task_id>";

	private Item task;

	public UndoneCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(
				STRING_EMPTY, 1)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			task = getItemByID(argsArray.get(0).trim());

			if (task == null) {
				invalidArgs.add("taskID");
			} else if (argsArray.get(0).trim().contains("t")
					|| argsArray.get(0).trim().contains("e")) {
				invalidArgs.add("Undone tasks cannot be undone!");
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
		if (this.count != 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method creates a executes the undone command. Which simply moves
	 * either (1) a done task to the not-done pile or (2) a done event to the
	 * not-done pile
	 * 
	 * @param None
	 * @return message to show user
	 */
	public String execute() {
		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			int complementListIndex = Storage.getComplementListIndex(listIndex);
			Magical.getStorage().delete(listIndex, task);
			Magical.getStorage().create(complementListIndex, task);
		} catch (IOException e) {
			return "unable to un-archive task";
		} finally {
			GUIModel.setTaskList(Magical.getStorage().getList(
					Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.getStorage().getList(
					Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.getStorage().getList(
					Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.getStorage().getList(
					Storage.EVENTS_DONE_INDEX));
		}

		return "task un-archived";
	}

	@Override
	public boolean isUndoable() {
		return true;
	}
}
