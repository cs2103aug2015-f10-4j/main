package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIHelpView extends Application {

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Help");
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/gui/HelpFXML.fxml"))	;
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();

	}

}
