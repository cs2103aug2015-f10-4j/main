package command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import main.Magical;
import main.Storage;
import main.Item;

/**
 * @@author A0131729E
 */
public class UndoCommand extends Command {

	/** Messaging **/
	private static final String MESSAGE_UNDO_ERROR = "Unable to undo";
	private static final String MESSAGE_UNDO_SUCCESS = "Undo successful";
	private static final String MESSAGE_UNDO_NONE = "nothing to undo";

	/** Command parameters **/
	private int undoLayersSize;

	/**
	 * Constructor for UndoCommand objects. Arguments are stored but have no
	 * impact on command's functionality.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public UndoCommand() {
		
	}

	/**
	 * This method executes the undo command. Whenever a command which alters
	 * the database is executed, a new layer of history is created so that the
	 * command's actions can be undo. If a previous version of the database
	 * exists, this command reverts the database to the previous version.
	 * 
	 * @return message to show user
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		undoLayersSize = Magical.undoLists.get(0).size();
		checkNumUndo();

		try {
			backUpToRedo();
			moveUndoToStorage();

			ArrayList<Item> lastTasksList = Magical.undoLists.get(
					Storage.TASKS_INDEX).pop();
			ArrayList<Item> lastTasksDoneList = Magical.undoLists.get(
					Storage.TASKS_DONE_INDEX).pop();
			ArrayList<Item> lastEventsList = Magical.undoLists.get(
					Storage.EVENTS_INDEX).pop();
			ArrayList<Item> lastEventsDoneList = Magical.undoLists.get(
					Storage.EVENTS_DONE_INDEX).pop();

			setStorage(lastTasksList, lastTasksDoneList, lastEventsList,
					lastEventsDoneList);
			
			return MESSAGE_UNDO_SUCCESS;
		} catch (Exception e) {
			throw new Exception(MESSAGE_UNDO_ERROR);
		} finally {
			if (Magical.lastViewCommand != null) {
				Magical.lastViewCommand.execute();
				updateView();
			} else {
				updateViewStorage();
			}
		}
	}

	/**
	 * Move undo stack to storage
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	void moveUndoToStorage() throws IOException, FileNotFoundException {
		String folderPath = Magical.undoFolderPaths.pop();
		Magical.getStorage().changeFolderPath(folderPath);
	}

	/**
	 * Back up storage to redo stack
	 */
	void backUpToRedo() {
		Magical.redoFolderPaths.push(Magical.getStorage().getFolderPath());
		Magical.redoLists.get(Storage.TASKS_INDEX).push(
				Magical.getStorage().getList(Storage.TASKS_INDEX));
		Magical.redoLists.get(Storage.TASKS_DONE_INDEX).push(
				Magical.getStorage().getList(Storage.TASKS_DONE_INDEX));
		Magical.redoLists.get(Storage.EVENTS_INDEX).push(
				Magical.getStorage().getList(Storage.EVENTS_INDEX));
		Magical.redoLists.get(Storage.EVENTS_DONE_INDEX).push(
				Magical.getStorage().getList(Storage.EVENTS_DONE_INDEX));
	}

	/**
	 * Set the storage with the specified lists
	 * 
	 * @param lastTasksList
	 * @param lastTasksDoneList
	 * @param lastEventsList
	 * @param lastEventsDoneList
	 * @throws IOException
	 */
	void setStorage(ArrayList<Item> lastTasksList,
			ArrayList<Item> lastTasksDoneList, ArrayList<Item> lastEventsList,
			ArrayList<Item> lastEventsDoneList) throws IOException {
		Magical.getStorage().setList(Storage.TASKS_INDEX, lastTasksList);
		Magical.getStorage().setList(Storage.TASKS_DONE_INDEX,
				lastTasksDoneList);
		Magical.getStorage().setList(Storage.EVENTS_INDEX, lastEventsList);
		Magical.getStorage().setList(Storage.EVENTS_DONE_INDEX,
				lastEventsDoneList);
	}

	/**
	 * Throw exception if there is nothing to undo
	 * 
	 * @param redoLayersSize
	 * @throws Exception
	 */
	void checkNumUndo() throws Exception {
		if (undoLayersSize <= 0) {
			throw new Exception(MESSAGE_UNDO_NONE);
		}
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
