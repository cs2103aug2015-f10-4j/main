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

	private static final String ENDL = System.getProperty("line.separator");

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
		taskList = makeObservable(newTaskList);
	}

	public static void setTaskDoneList(ArrayList<Task> newTaskDoneList) {
		taskDoneList = makeObservable(newTaskDoneList);
	}

	public static void setEventList(ArrayList<Task> newEventList) {
		eventList = makeObservable(newEventList);
	}

	public static void setEventDoneList(ArrayList<Task> newEventDoneList) {
		eventDoneList = makeObservable(newEventDoneList);
	}

	public static void init() {
		taskList = makeObservable(main.Magical.getStorage().getList(main.Storage.TASKS_INDEX));
		taskDoneList = makeObservable(main.Magical.getStorage().getList(main.Storage.TASKS_DONE_INDEX));
		eventList = makeObservable(main.Magical.getStorage().getList(main.Storage.EVENTS_INDEX));
		eventDoneList = makeObservable(main.Magical.getStorage().getList(main.Storage.EVENTS_DONE_INDEX));
	}

	private static ObservableList<Task> makeObservable(ArrayList<Task> arrayList) {
		return FXCollections.observableArrayList(arrayList);
	}

//	public static void main(String[] args) {
//		ArrayList<Task> testList = new ArrayList<Task>();
//		Task t = new Task();
//		t.setTitle("TEST EVENT");
//		t.setStartTime(1600);
//		t.setEndTime(1800);
//		testList.add(t);
//		setEventList(testList);
//		System.out.println(getEventList());
//		System.out.println(getEventList().get(0).getStartTime());
//		System.out.println(getEventList().get(0).getEndTime());
//	}

}
