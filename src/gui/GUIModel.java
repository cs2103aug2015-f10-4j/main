package gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;

import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import main.CustomDate;
import main.Magical;
import main.Storage;
import main.Task;

public class GUIModel {

	public static boolean showHelpWindow = false;

	public static String currentTab = "tasks";

	public static ObservableList<Task> taskList;
	public static ObservableList<Task> taskDoneList;
	public static ObservableList<Task> eventList;
	public static ObservableList<Task> eventDoneList;

	/**
	 * This method sets the current tab in the model.
	 * @param type - "tasks" or "events"
	 */

	public static void setCurrentTab(String type) {
		if (type == "tasks") {
			currentTab = type;
		}
		else if (type == "events") {
			currentTab = type;
		}
	}

	/**
	 * Returns the current tab in the model.
	 * @return String - "events" or "tasks"
	 */
	public static String getCurrentTab() {
		return currentTab;
	}

	/**
	 * Return the model's task list.
	 * @return ObservableList<Task>
	 */
	public static ObservableList<Task> getTaskList() {
		return taskList;
	}

	/**
	 * Return the model's done task list.
	 * @return ObservableList<Task>
	 */
	public static ObservableList<Task> getTaskDoneList() {
		return taskDoneList;
	}

	/**
	 * Return the model's event list.
	 * @return ObservableList<Task>
	 */
	public static ObservableList<Task> getEventList() {
		return eventList;
	}

	/**
	 * Return the model's done event list.
	 * @return ObservableList<Task>
	 */
	public static ObservableList<Task> getEventDoneList() {
		return eventDoneList;
	}

	/**
	 * Sets the model's task list.
	 * @param newTaskList - replaces current task list
	 */
	public static void setTaskList(ArrayList<Task> newTaskList) {
		taskList = makeObservable(newTaskList);
	}

	/**
	 * Sets the model's done task list.
	 * @param newTaskList - replaces current done task list
	 */
	public static void setTaskDoneList(ArrayList<Task> newTaskDoneList) {
		taskDoneList = makeObservable(newTaskDoneList);
	}

	/**
	 * Sets the model's event list.
	 * @param newTaskList - replaces current event list
	 */
	public static void setEventList(ArrayList<Task> newEventList) {
		eventList = makeObservable(newEventList);
	}

	/**
	 * Sets the model's done event list.
	 * @param newTaskList - replaces current done event list
	 */
	public static void setEventDoneList(ArrayList<Task> newEventDoneList) {
		eventDoneList = makeObservable(newEventDoneList);
	}

	/**
	 * This method initializes GUIModel by converting tasks lists from Storage
	 * into ObservableLists usable by the controller.
	 * @return Nothing
	 */

	public static void init() {
		taskList = makeObservable(main.Magical.getStorage().getList(main.Storage.TASKS_INDEX));
		taskDoneList = makeObservable(main.Magical.getStorage().getList(main.Storage.TASKS_DONE_INDEX));
		eventList = makeObservable(main.Magical.getStorage().getList(main.Storage.EVENTS_INDEX));
		eventDoneList = makeObservable(main.Magical.getStorage().getList(main.Storage.EVENTS_DONE_INDEX));
	}

	/**
	 * This method converts an arraylist of tasks into an ObservableList.
	 * @param arrayList - list to convert
	 * @return ObservableList
	 */
	private static ObservableList<Task> makeObservable(ArrayList<Task> arrayList) {
		return FXCollections.observableArrayList(arrayList);
	}


}
