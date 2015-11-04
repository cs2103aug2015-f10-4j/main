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

	public static String currentTab = "tasks";

	public static ObservableList<Task> taskList;
	public static ObservableList<Task> taskDoneList;
	public static ObservableList<Task> eventList;
	public static ObservableList<Task> eventDoneList;

	public static void setCurrentTab(String type) {
		if (type == "tasks") {
			currentTab = type;
		}
		else if (type == "events") {
			currentTab = type;
		}
	}

	public static String getCurrentTab() {
		return currentTab;
	}

	public static ObservableList<Task> getTaskList() {
		return taskList;
	}

	public static ObservableList<Task> getTaskDoneList() {
		return taskDoneList;
	}
	public static ObservableList<Task> getEventList() {
		return eventList;
	}

	public static ObservableList<Task> getEventDoneList() {
		return eventDoneList;
	}

	public static void setTaskList(ArrayList<Task> newTaskList) {
		taskList = FXCollections.observableArrayList(newTaskList);
	}

	public static void setTaskDoneList(ArrayList<Task> newTaskDoneList) {
		taskDoneList = FXCollections.observableArrayList(newTaskDoneList);
	}

	public static void setEventList(ArrayList<Task> newEventList) {
		eventList = FXCollections.observableArrayList(newEventList);
	}

	public static void setEventDoneList(ArrayList<Task> newEventDoneList) {
		eventDoneList = FXCollections.observableArrayList(newEventDoneList);
	}

	public static void init() {
		taskList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.TASKS_INDEX));
		taskDoneList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.TASKS_DONE_INDEX));
		eventList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.EVENTS_INDEX));
		eventDoneList = FXCollections.observableArrayList(main.Magical.getStorage().getList(main.Storage.EVENTS_DONE_INDEX));
	}

}
