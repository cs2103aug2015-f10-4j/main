package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class UntagCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: untag <task_id> <tag name>";

	private Item task;
	private String tag;
	private Item prevTask;

	public UntagCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(
				Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			task = getItemByID(argsArray.get(0).trim());
			tag = argsArray.get(1).trim();

			if (task == null) {
				invalidArgs.add("taskID");
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
		if (this.count != 2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method creates a executes the untag command. Which simply removes
	 * the specified tag from the tag Set of the task/event
	 * 
	 * @param None
	 *            .
	 * @return message to show user
	 */
	public String execute() {
		prevTask = task;
		task = prevTask.copy();

		Set<String> tags = task.getTags();
		tags.remove(tag);
		task.setTags(tags);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.update(listIndex, prevTask, task);
		} catch (IOException e) {
			return "unable to remove tag from item";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage
					.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage
					.getList(Storage.EVENTS_DONE_INDEX));
		}
		return tag + " removed from item";
	}
}
