package main;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUIModel {

	public static ObservableList<Task> taskList;
	public static ObservableList<Task> eventList;

	public static ObservableList<Task> getTaskList() {
		return taskList;
	}

	public static void setTaskList(ArrayList<Task> newTaskList) {
		taskList = FXCollections.observableArrayList(newTaskList);
	}

	public static void init() {
		taskList = FXCollections.observableArrayList(Magical.getStorage().readTaskList());
	}

}