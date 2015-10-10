package main;

import java.util.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

/*
 * TYPES OF VIEWS
 * 1. DISPLAY_ALL: displays 3 tables of tasks -- to-do, events, and blocked dates
 * 2. DISPLAY_ONE: displays the full details of a particular task/event
 */
public class GUIView extends Application {

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Magical v0.1");
		FXMLLoader loader = new FXMLLoader();
		Scene scene = loader.load(getClass().getResource("FXML.fxml"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}


    public static void main(String[] args) {
    	launch(args);

    }
}
