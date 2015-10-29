package main;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUIModel {

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
		taskList = FXCollections.observableArrayList(Magical.getStorage().getTasks());
<<<<<<< HEAD
=======
		doneList = FXCollections.observableArrayList(Magical.getStorage().getTasksDone());
>>>>>>> 4b1b14cfc7ce5e177207912cbaad6d60d0f20a71
	}

}
