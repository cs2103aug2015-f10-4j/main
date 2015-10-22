package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GUIController {

	@FXML private TableView<Task> taskTable;
	@FXML private TableColumn<Task, String> taskTitleCol;

	@FXML private Label messageLabel;
	@FXML private TextField commandLineField;

	public void initialize() throws Exception {
		Magical.init();
		GUIModel.init();
		taskTable.setItems(FXCollections.observableArrayList(GUIModel.taskList));
		taskTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("taskTitle"));
	}

	@FXML
	protected void onEnterPressed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			String userInput = commandLineField.getText();
			String message = Magical.parseCommand(userInput);
			messageLabel.setText(message);
		}
	}

}
