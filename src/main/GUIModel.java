package main;

import java.util.*;

public class GUIModel {
	
	private boolean displayNotificationBar;
	private String notificationMessage;
	private String errorMessage;
	
	public Task currentTask = new Task();
	public ArrayList<Task> eventList = new ArrayList<Task>();
	public ArrayList<Task> taskList = new ArrayList<Task>();
	
	public GUIModel() {
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public void setTaskList(ArrayList<Task> tList) {
		taskList = tList;
	}
	
	

}
