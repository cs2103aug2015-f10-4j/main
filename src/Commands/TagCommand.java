package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class TagCommand extends Command {

	private static final String MESSAGE_INVALID_PARAMS = "Use Format: tag <task_id> <tag name>";

	private Task task;
	private String tag;
	private Task prevTask;

	public TagCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(
				Arrays.asList(args.split(" ", 2)));
		this.count = argsArray.size();

		if (validNumArgs()) {
			task = getTaskByID(argsArray.get(0).trim());
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
	 * This method executes the tag command. Which simply adds the specified tag
	 * to a task or event's tag set.
	 * 
	 * @param None
	 *            .
	 * @return message to show user
	 */
	@Override
	public String execute() {
		prevTask = task;
		task = prevTask.copy();

		Set<String> tags = task.getTags();
		tags.add(tag);
		task.setTags(tags);

		try {
			int listIndex = Storage.getListIndex(argsArray.get(0));
			Magical.storage.update(listIndex, prevTask, task);
		} catch (IOException e) {
			return "unable to add tag to task";
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage
					.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage
					.getList(Storage.EVENTS_DONE_INDEX));
		}

		return tag + " added to task";
	}
}
