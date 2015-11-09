package gui;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * GUIView initializes the application by loading the GUI layout from the FXML
 * file, and launching it.
 *
 * @@author A0127127L
 */

public class GUIView extends Application {

	private static final String TITLE = "Magical";
	private static final String TITLE_FORMAT = "Magical v%s";
	private static final String VERSION_NUMBER = "0.5";

	private static boolean showVersionNumber = false;

	/**
	 * Initializes the main scene of the application, by loading the FXML file
	 * and its controller.
	 * 
	 * @return nothing
	 */
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(showVersionNumber ? String.format(TITLE_FORMAT,
				VERSION_NUMBER) : TITLE);
		Pane rootPane = (Pane) FXMLLoader.load(getClass().getResource(
				"/gui/FXML.fxml"));
		Scene scene = new Scene(rootPane);
		primaryStage.getIcons().add(new Image("/gui/magicalLogo.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Main method. Launches application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LauncherImpl.launchApplication(GUIView.class, args);
	}

}
