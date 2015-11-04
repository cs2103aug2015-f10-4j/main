package Commands;

import java.io.IOException;
import java.util.ArrayList;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Task;

public class UndoCommand extends Command {

	public UndoCommand(String args) {
		super(args);
	}

	@Override
	public String execute() {
		int undoLayersSize = Magical.undoLists.get(0).size();
		if (undoLayersSize <= 0) {
			return "nothing to undo";
		}

		try {
			ArrayList<Task> lastTasksList = Magical.undoLists.get(Storage.TASKS_INDEX).pop();
			ArrayList<Task> lastTasksDoneList = Magical.undoLists.get(Storage.TASKS_DONE_INDEX).pop();
			ArrayList<Task> lastEventsList = Magical.undoLists.get(Storage.EVENTS_INDEX).pop();
			ArrayList<Task> lastEventsDoneList = Magical.undoLists.get(Storage.EVENTS_DONE_INDEX).pop();
			Magical.storage.setList(Storage.TASKS_INDEX, lastTasksList);
			Magical.storage.setList(Storage.TASKS_DONE_INDEX, lastTasksDoneList);
			Magical.storage.setList(Storage.EVENTS_INDEX, lastEventsList);
			Magical.storage.setList(Storage.EVENTS_DONE_INDEX, lastEventsDoneList);
			return "undo successful";
		} catch (IOException e) {
			return "unable to undo";
		} finally {
				GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
				GUIModel.setDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public boolean validNumArgs() {
		return true;
	}
}
