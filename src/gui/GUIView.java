package gui;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {

	private static final String TITLE_FORMAT = "Magical v%s";
	private static final String VERSION_NUMBER = "0.4";

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(String.format(TITLE_FORMAT, VERSION_NUMBER));
		Pane rootPane = (Pane) FXMLLoader.load(getClass().getResource("/gui/FXML.fxml"))	;
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Main method. Launches application.
	 * @param args
	 */
	public static void main(String[] args) {
		LauncherImpl.launchApplication(GUIView.class, args);
	}

}
