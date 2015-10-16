package main;

import java.util.*;

public class GUIModel {

	private boolean displayNotificationBar;
	private String notificationMessage;
	private String errorMessage;
	
	enum TaskView {
		DISPLAY_ONE, DISPLAY_ALL;
	}
	
	public TaskView currentTaskView = TaskView.DISPLAY_ALL;

	public Task currentTask;
	public ArrayList<Task> eventList = new ArrayList<Task>();
	public ArrayList<Task> taskList = new ArrayList<Task>();

	public static Magical magical;

	public static void init() throws Exception {
		magical = new Magical();
		magical.init();
	}

	public void initialize() {
		taskList = magical.upcomingTasks();
	}

	public void setErrofrMessage(String message) {
		this.errorMessage = message;
	}

	public void makeTaskList(ArrayList<Task> tList) {
		taskList = tList;
	}

	public void setNotificationMessage(String message) {
		notificationMessage = message;
	}



}
