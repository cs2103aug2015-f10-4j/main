package Commands;

import java.io.IOException;
import java.util.ArrayList;

import main.GUIModel;
import main.Magical;
import main.Task;

public class UndoCommand extends Command {

	public UndoCommand(String args) {
		super(args);
	}

	@Override
	public String execute() {
		if (Magical.undoListHistory.size() > 0) {
			ArrayList<Task> lastTaskList = Magical.undoListHistory.pop();
			try {
				Magical.storage.setTaskList(lastTaskList);
				if (Magical.undoDoneListHistory.size() > 0) {
					ArrayList<Task> lastTaskDoneList = Magical.undoDoneListHistory.pop();
					try {
						Magical.storage.setTaskList(lastTaskDoneList);
						return "undo successful";
					} catch (IOException e) {
						Magical.undoDoneListHistory.push(lastTaskDoneList);
						return "unable to undo";
					}
				}
				return "undo successful";
			} catch (IOException e) {
				Magical.undoListHistory.push(lastTaskList);
				return "unable to undo";
			} finally {
				GUIModel.setTaskList(Magical.storage.getTasks());
				GUIModel.setDoneList(Magical.storage.getTasksDone());
			}
		}
		return "nothing to undo";
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
