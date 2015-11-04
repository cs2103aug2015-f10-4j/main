package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIHelpController {

	private static final String ENDL = System.getProperty("line.separator");

	private final String HELP_TEXT = "Welcome to Magical!" + ENDL + ENDL
			+ "These are the available commands: " + ENDL + ENDL + "add [task title] [due date] ";

	@FXML private TextArea helpTextArea;

	public void initialize() {

		helpTextArea.setText(HELP_TEXT);

	}

}
