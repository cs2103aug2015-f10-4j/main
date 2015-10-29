package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIView extends Application {

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Magical v0.2");
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/main/FXML.fxml"))	;
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
