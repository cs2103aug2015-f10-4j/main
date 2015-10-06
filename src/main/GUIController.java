package main;

public class GUIController {
	
	public static GUIModel model = new GUIModel();
	public static GUIView view = new GUIView();
	
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
	
	public static void update() {
		
	}

}
