package main;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class GUIController {

	@FXML private TitledPane toDoPane;
	@FXML private TableView<Task> taskTable;
	@FXML private TableView<Task> doneTable;
	@FXML private TableColumn<Task, String> taskIndexCol;
	@FXML private TableColumn<Task, String> taskTitleCol;
	@FXML private TableColumn<Task, String> taskDueDateCol;
	@FXML private TableColumn<Task, String> taskPriorityCol;
	@FXML private TableColumn<Task, String> taskTagsCol;
	@FXML private TableColumn<Task, String> doneIndexCol;
	@FXML private TableColumn<Task, String> doneTitleCol;
	@FXML private TableColumn<Task, String> doneDueDateCol;
	@FXML private TableColumn<Task, String> donePriorityCol;
	@FXML private TableColumn<Task, String> doneTagsCol;
	@FXML private Label messageLabel;
	@FXML private TextField commandLineField;

	public void initialize() throws Exception {

		Magical.init();
		GUIModel.init();
		taskTable.setItems(FXCollections.observableArrayList(GUIModel.taskList));

		/*TableCell<Task, String> indexCell = new TableCell<Task, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);

	            setText(getIndex()+"");
	        }
		};
		Callback<TableColumn<Task, String>, TableCell<Task, String>> cb =
				new Callback<TableColumn<Task, String>, TableCell<Task, String>>(){
					@Override
					public TableCell<Task, String> call(TableColumn<Task, String> col) {
						TableCell<Task, String> cell = new TableCell<Task, String>() {
							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null) {
									setText("");
								} else {
									setText(getIndex()+"");
								}
							}
						};
						return cell;
					}
		};

		taskIndexCol.setCellFactory(cb);*/
		taskTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
		taskTagsCol.setCellValueFactory(new PropertyValueFactory<Task, String>("tags"));
		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		taskPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
		doneTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
		doneTagsCol.setCellValueFactory(new PropertyValueFactory<Task, String>("tags"));
		doneDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		donePriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
	}



	@FXML
	protected void onEnterPressed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			String userInput = commandLineField.getText();
			String message = Magical.parseCommand(userInput);
			messageLabel.setText(message);
			ArrayList<Task> newTaskList = Magical.getStorage().getTasks();
			GUIModel.setTaskList(newTaskList);
			taskTable.setItems(GUIModel.getTaskList());
			commandLineField.clear();
		}
	}

}
