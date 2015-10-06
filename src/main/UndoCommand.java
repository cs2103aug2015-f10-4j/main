package main;

import java.io.IOException;
import java.util.ArrayList;

public class UndoCommand extends Command {

	public UndoCommand(String args) {
		super(args);
	}

	@Override
	public String execute() {
		if (Magical.undoHistory.size() > 0) {
			ArrayList<Task> lastTaskList = Magical.undoHistory.pop();
			try {
				Magical.storage.setTaskList(lastTaskList);
				return "undo successful";
			} catch (IOException e) {
				Magical.undoHistory.push(lastTaskList);
				return "unable to undo";
			}
		}
		return "nothing to undo";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}
}
