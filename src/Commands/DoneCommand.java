package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class DoneCommand extends Command {
	private static final String MESSAGE_INVALID_PARAMS = "Use Format: done <task_id>";

	private Item task;

	public DoneCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(
				STRING_EMPTY, 1)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			task = getItemByID(argsArray.get(0).trim());

			if (task == null) {
				invalidArgs.add("taskID");
			} else if (argsArray.get(0).trim().contains("d")
					|| argsArray.get(0).trim().contains("p")) {
				invalidArgs.add("Done tasks cannot be done!");
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
	 * This method executes the done command. Which either moves a task or event
	 * to its corresponding done task or done event pile.
	 * 
	 * @param None
	 *            .
	 * @return None
	 */
	@Override
	public String execute() {
		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			int complementListIndex = Storage.getComplementListIndex(listIndex);
			Magical.getStorage().delete(listIndex, task);
			Magical.getStorage().create(complementListIndex, task);
		} catch (IOException e) {
			return "unable to archive item";
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

		return "item archived";
	}
}
