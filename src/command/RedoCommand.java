package command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import main.Magical;
import main.Storage;
import main.Item;

public class RedoCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_REDO_ERROR = "Unable to redo";
	private static final String MESSAGE_REDO_SUCCESS = "Redo successful";
	private static final String MESSAGE_REDO_NONE = "Nothing to redo";

	/** Command parameters **/
	private int redoLayersSize;

	/**
	 * Constructor for RedoCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @param args
	 * @throws Exception
	 */
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

		redoLayersSize = Magical.redoLists.get(0).size();
		checkNumRedo();
		checkCanRedo();

		try {
			Magical.pushUndoLayer();
			String folderPath = Magical.redoFolderPaths.pop();
			Magical.getStorage().changeFolderPath(folderPath);
			
			ArrayList<Item> nextTasksList = Magical.redoLists.get(Storage.TASKS_INDEX).pop();
			ArrayList<Item> nextTasksDoneList = Magical.redoLists.get(Storage.TASKS_DONE_INDEX).pop();
			ArrayList<Item> nextEventsList = Magical.redoLists.get(Storage.EVENTS_INDEX).pop();
			ArrayList<Item> nextEventsDoneList = Magical.redoLists.get(Storage.EVENTS_DONE_INDEX).pop();
			
			setStorageLists(nextTasksList, nextTasksDoneList, nextEventsList, nextEventsDoneList);

			return MESSAGE_REDO_SUCCESS;
		} catch (Exception e) {
			throw new Exception(MESSAGE_REDO_ERROR);
		} finally {
			updateView();
		}
	}

	/**
	 * Set the storage with the specified lists 
	 * @param nextTasksList
	 * @param nextTasksDoneList
	 * @param nextEventsList
	 * @param nextEventsDoneList
	 * @throws IOException
	 */
	void setStorageLists(ArrayList<Item> nextTasksList, ArrayList<Item> nextTasksDoneList,
			ArrayList<Item> nextEventsList, ArrayList<Item> nextEventsDoneList) throws IOException {
		Magical.getStorage().setList(Storage.TASKS_INDEX, nextTasksList);
		Magical.getStorage().setList(Storage.TASKS_DONE_INDEX, nextTasksDoneList);
		Magical.getStorage().setList(Storage.EVENTS_INDEX, nextEventsList);
		Magical.getStorage().setList(Storage.EVENTS_DONE_INDEX, nextEventsDoneList);
	}

	/**
	 * Check if the last command is undoable. If it is, then storage was
	 * updated. Therefore, redo history should be reset and empty redo history
	 * message should be thrown.
	 * 
	 * @throws Exception
	 */
	void checkCanRedo() throws Exception {
		if (Magical.lastCommand.isUndoable()) {
			Magical.redoFolderPaths = new Stack<String>();
			Magical.redoLists.set(Storage.TASKS_INDEX,
					new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.TASKS_DONE_INDEX,
					new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.EVENTS_INDEX,
					new Stack<ArrayList<Item>>());
			Magical.redoLists.set(Storage.EVENTS_DONE_INDEX,
					new Stack<ArrayList<Item>>());
			throw new Exception(MESSAGE_REDO_NONE);
		}
	}

	/**
	 * Throw exception if there is nothing to redo
	 * 
	 * @param redoLayersSize
	 * @throws Exception
	 */
	void checkNumRedo() throws Exception {
		if (redoLayersSize <= 0) {
			throw new Exception(MESSAGE_REDO_NONE);
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

	@Override
	void setProperParams() {

	}
}
