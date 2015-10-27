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
		doneList = FXCollections.observableArrayList(Magical.getStorage().getTasksDone());
	}

}
