package gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.Item;
import main.Magical;
import main.Storage;

/**
 * GUIModel holds information pulled from the main application, and must be
 * updated whenever a command is executed.
 *
 * @@author A0127127L
 */

public class GUIModel {

	public static boolean showHelpWindow = false;

	public static String currentTab = "tasks";

	public static ObservableList<Item> taskList;
	public static ObservableList<Item> taskDoneList;
	public static ObservableList<Item> eventList;
	public static ObservableList<Item> eventDoneList;

	@FXML
	private AnchorPane rootPane;

	/**
	 * This method sets the current tab in the model.
	 * 
	 * @param type
	 *            "tasks" or "events"
	 */

	public static void setCurrentTab(String type) {
		if (type == "tasks") {
			currentTab = type;
		} else if (type == "events") {
			currentTab = type;
		}
	}

	/**
	 * Returns the current tab in the model.
	 * 
	 * @return String "events" or "tasks"
	 */
	public static String getCurrentTab() {
		return currentTab;
	}

	/**
	 * The methods below return the various lists stored in the model.
	 * 
	 * @return ObservableList<Item>
	 */
	public static ObservableList<Item> getTaskList() {
		return taskList;
	}

	public static ObservableList<Item> getTaskDoneList() {
		return taskDoneList;
	}

	public static ObservableList<Item> getEventList() {
		return eventList;
	}

	public static ObservableList<Item> getEventDoneList() {
		return eventDoneList;
	}

	/**
	 * The methods below allow the lists in the model to be replaced.
	 * 
	 * @param newTaskList
	 */
	public static void setTaskList(ArrayList<Item> newTaskList) {
		taskList = makeObservable(newTaskList);
	}

	public static void setTaskDoneList(ArrayList<Item> newTaskDoneList) {
		taskDoneList = makeObservable(newTaskDoneList);
	}

	public static void setEventList(ArrayList<Item> newEventList) {
		eventList = makeObservable(newEventList);
	}

	public static void setEventDoneList(ArrayList<Item> newEventDoneList) {
		eventDoneList = makeObservable(newEventDoneList);
	}

	/**
	 * This method updates GUIModel with lists from the main application.
	 * 
	 * @return Nothing
	 */
	public static void update() {
		currentTab = Magical.getCurrentTab();
		showHelpWindow = Magical.isShowHelpWindow();
		setTaskList(Magical.getDisplayList(Storage.TASKS_INDEX));
		setTaskDoneList(Magical.getDisplayList(Storage.TASKS_DONE_INDEX));
		setEventList(Magical.getDisplayList(Storage.EVENTS_INDEX));
		setEventDoneList(Magical.getDisplayList(Storage.EVENTS_DONE_INDEX));
	}

	/**
	 * This method converts an ArrayList of tasks into an ObservableList.
	 * 
	 * @param arrayList
	 *            list to convert
	 * @return ObservableList
	 */
	private static ObservableList<Item> makeObservable(ArrayList<Item> arrayList) {
		return FXCollections.observableArrayList(arrayList);
	}

}
