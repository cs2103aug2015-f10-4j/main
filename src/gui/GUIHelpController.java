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

	private final String HELP_TEXT = "Welcome to Magical!" + ENDL +
			ENDL +
			"==================" + ENDL +
			"Available Commands" + ENDL +
			"==================" + ENDL +
			ENDL +
			"To add a task:" + ENDL +
			"> add [task title] by [due date] [recurrence]" + ENDL +
			ENDL +
			"To add an event:" + ENDL +
			"> event [title] from [start date] to [start time] to [end date] [end time] [recurrence]" + ENDL +
			ENDL +
			"To mark a task as 'done':" + ENDL +
			"> done [task ID]" + ENDL +
			ENDL +
			"To delete a task:" + ENDL +
			"> delete [task ID]" + ENDL +
			ENDL +
			"To edit a task:" + ENDL +
			"> edit [task ID] [field] [updated value]" + ENDL +
			ENDL +
			"To tag a task:" + ENDL +
			"> tag [task ID] [tag name]" + ENDL +
			ENDL +
			"To search for a task:" + ENDL +
			"> search [query]" + ENDL +
			ENDL +
			"To change a task's priority:" + ENDL +
			"> set [task ID] [number from 1 to]" + ENDL +
			ENDL +
			"To undo a previous action:" + ENDL +
			"> undo";

	@FXML private TextArea helpTextArea;

	public void initialize() {

		helpTextArea.setText(HELP_TEXT);

	}

}
