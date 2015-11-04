package gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;

import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Magical;
import main.Storage;
import main.Task;

public class GUIModel {

	private static final String ENDL = System.getProperty("line.separator");

	public static final String HELP_MESSAGE = "this is the help message" + ENDL + "it spans many lines" + ENDL +
			"here is another line bc i have no idea what to write" + ENDL + "is this enough" + ENDL +
			"no it's not apparently" + ENDL + "what about now IDK is it ok" + ENDL + "no";

	public static boolean showHelpWindow = false;


	public static ObservableList<Task> taskList;
	public static ObservableList<Task> doneList;

	public static ObservableList<Task> getTaskList() {
		return taskList;
	}

	public static ObservableList<Task> getDoneList() {
		return doneList;
	}

	public static void setTaskList(ArrayList<Task> newTaskList) {
		taskList = FXCollections.observableArrayList(newTaskList);
	}

	public static void setDoneList(ArrayList<Task> newDoneList) {
		doneList = FXCollections.observableArrayList(newDoneList);
	}

	public static void init() {
		taskList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.TASKS_INDEX));
		doneList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.TASKS_DONE_INDEX));

	}

}
