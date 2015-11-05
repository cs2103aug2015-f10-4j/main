package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GUIHelpController {

	private static final String ENDL = System.getProperty("line.separator");

	private static final String HEADER_TEXT = "Magical User Guide";

	private static final String BODY_TEXT = ENDL + "To add a task:" + ENDL +
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
			"To untag a task:" + ENDL +
			"> untag [taskID] [tag name]" + ENDL +
			ENDL +
			"To show all tasks:" + ENDL +
			"> show" + ENDL +
			ENDL +
			"To show a different tab:" + ENDL +
			"> show [tasks / events]" + ENDL +
			ENDL +
			"To show tasks/events of a certain tag:" + ENDL +
			"> show [tag name]" + ENDL +
			ENDL +
			"To sort tasks:" + ENDL +
			"> sort [priority / date / title]" + ENDL +
			ENDL +
			"To search for a task:" + ENDL +
			"> search [query]" + ENDL +
			ENDL +
			"To change a task's priority:" + ENDL +
			"> set [task ID] [number from 1 to 10]" + ENDL +
			ENDL +
			"To undo a previous action:" + ENDL +
			"> undo" + ENDL +
			ENDL +
			"To change where your list is stored:" + ENDL +
			"> path [filepath]" + ENDL +
			ENDL +
			"To exit the application:" + ENDL +
			"> exit";

	@FXML private Label headerLabel;
	@FXML private Label bodyLabel;

	public void initialize() {

		headerLabel.setText(HEADER_TEXT);
		bodyLabel.setText(BODY_TEXT);

	}

}
