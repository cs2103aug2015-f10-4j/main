package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {

	private static final String TITLE_FORMAT = "Magical v%s";
	private static final String HELP_TITLE = "Help";
	private static final String VERSION_NUMBER = "0.3";

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(String.format(TITLE_FORMAT, VERSION_NUMBER));
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/gui/FXML.fxml"))	;
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
