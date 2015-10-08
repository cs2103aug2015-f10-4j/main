package main;

public class GUIController {
	
	public static GUIModel model = new GUIModel();
	public static GUIView view = new GUIView();
	
	public static String oldUserInput;
	
	public String userInput;
	
	public static void setModel(GUIModel m) {
		model = m;
	}
	
	public static void setView(GUIView v) {
		view = v;
	}
	
	public static void start() {
		view.taskList = model.taskList;
		view.launch();
	}
	
	public static void updateView(GUIModel m) {
		model = m;
		view.taskList = model.taskList;
		view.currentTask = model.currentTask;
		view.eventList = model.eventList;
	}
	
	public static void main(String[] args) {
		while(view.userInput.equalsIgnoreCase(oldUserInput)) {
			
		}
	}

}
