package Commands;

import java.util.ArrayList;
import java.util.Stack;

import gui.GUIModel;
import main.Magical;
import main.Storage;
import main.Item;

public class RedoCommand extends Command {

	public RedoCommand(String args) {
		super(args);
	}

	/**
	 * This method executes the redo command. This command changes the storage
	 * to the version before undo was called. It fails if undo was never called
	 * or undo history got edited.
	 * 
	 * @param None
	 * @return message to show user
	 * @throws Exception 
	 */
	@Override
	public String execute() throws Exception {
		if (Magical.lastCommand.isUndoable()) {
			Magical.redoLists.set(Storage.TASKS_INDEX, new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.TASKS_DONE_INDEX, new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.EVENTS_INDEX, new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.EVENTS_DONE_INDEX, new Stack<ArrayList<Item>>());
		}
		
		int redoLayersSize = Magical.redoLists.get(0).size();
		if (redoLayersSize <= 0) {
			throw new Exception("nothing to redo");
		}

		try {
			Magical.pushUndoLayer();
			ArrayList<Item> nextTasksList = Magical.redoLists.get(
					Storage.TASKS_INDEX).pop();
			ArrayList<Item> nextTasksDoneList = Magical.redoLists.get(
					Storage.TASKS_DONE_INDEX).pop();
			ArrayList<Item> nextEventsList = Magical.redoLists.get(
					Storage.EVENTS_INDEX).pop();
			ArrayList<Item> nextEventsDoneList = Magical.redoLists.get(
					Storage.EVENTS_DONE_INDEX).pop();
			Magical.getStorage().setList(Storage.TASKS_INDEX, nextTasksList);
			Magical.getStorage().setList(Storage.TASKS_DONE_INDEX,
					nextTasksDoneList);
			Magical.getStorage().setList(Storage.EVENTS_INDEX, nextEventsList);
			Magical.getStorage().setList(Storage.EVENTS_DONE_INDEX,
					nextEventsDoneList);
			return "redo successful";
		} catch (Exception e) {
			throw new Exception("unable to redo");
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
