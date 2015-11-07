package Commands;

import java.io.IOException;
import java.util.ArrayList;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class UndoCommand extends Command {

	public UndoCommand(String args) {
		super(args);
	}

	/**
	 * This method executes the undo command. Whenever a command which alters
	 * the database is executed, a new layer of history is created so that the
	 * command's actions can be undo. If a previous version of the database
	 * exists, this command reverts the database to the previous version.
	 * 
	 * @param None
	 * @return message to show user
	 * @throws Exception 
	 */
	@Override
	public String execute() throws Exception {
		int undoLayersSize = Magical.undoLists.get(0).size();
		if (undoLayersSize <= 0) {
			throw new Exception("nothing to undo");
		}

		try {
			Magical.redoLists.get(Storage.TASKS_INDEX).push(Magical.getStorage().getList(Storage.TASKS_INDEX));
			Magical.redoLists.get(Storage.TASKS_DONE_INDEX).push(Magical.getStorage().getList(Storage.TASKS_DONE_INDEX));
			Magical.redoLists.get(Storage.EVENTS_INDEX).push(Magical.getStorage().getList(Storage.EVENTS_INDEX));
			Magical.redoLists.get(Storage.EVENTS_DONE_INDEX).push(Magical.getStorage().getList(Storage.EVENTS_DONE_INDEX));
			ArrayList<Item> lastTasksList = Magical.undoLists.get(
					Storage.TASKS_INDEX).pop();
			ArrayList<Item> lastTasksDoneList = Magical.undoLists.get(
					Storage.TASKS_DONE_INDEX).pop();
			ArrayList<Item> lastEventsList = Magical.undoLists.get(
					Storage.EVENTS_INDEX).pop();
			ArrayList<Item> lastEventsDoneList = Magical.undoLists.get(
					Storage.EVENTS_DONE_INDEX).pop();
			Magical.getStorage().setList(Storage.TASKS_INDEX, lastTasksList);
			Magical.getStorage().setList(Storage.TASKS_DONE_INDEX,
					lastTasksDoneList);
			Magical.getStorage().setList(Storage.EVENTS_INDEX, lastEventsList);
			Magical.getStorage().setList(Storage.EVENTS_DONE_INDEX,
					lastEventsDoneList);
			return "undo successful";
		} catch (Exception e) {
			throw new Exception("unable to undo");
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
