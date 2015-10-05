package main;

public class GUIController {
	
	GUIModel model;
	GUIView view;

	public GUIController(GUIModel model, GUIView view) {
		this.model = model;
		this.view = view;
	}
	
	public void setModel(GUIModel model) {
		this.model = model;
	}
	
	public void setView(GUIView view) {
		this.view = view;
	}

}
