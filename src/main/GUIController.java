package main;

public class GUIController {
	
	GUIModel model;
	GUIView view;

	public GUIController(GUIModel m, GUIView v) {
		model = m;
		view = v;
	}
	
	public void setModel(GUIModel m) {
		model = m;
	}
	
	public void setView(GUIView v) {
		view = v;
	}

}
