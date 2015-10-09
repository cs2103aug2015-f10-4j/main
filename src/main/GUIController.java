package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUIController extends Application {
	
	public static GUIModel model;
	public static GUIView view ;
	
	public static String oldUserInput;
	
	public String userInput;
	
	public static void setModel(GUIModel m) {
		model = m;
	}
	
	public static void setView(GUIView v) {
		view = v;
	}
	
	public void start(Stage view) {
		
	}
	
	private void setTaskList(GUIView view) {
		view.taskList = model.taskList;
	}
	
	public static void updateView(GUIModel m) {
		model = m;
		view.taskList = model.taskList;
		view.currentTask = model.currentTask;
		view.eventList = model.eventList;
	}
	
	public static void main(String[] args) {
		view = new GUIView();
		launch();
	}

}
