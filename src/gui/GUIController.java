package gui;

import java.util.Iterator;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Magical;
import main.Task;

public class GUIController {

	@FXML private TitledPane toDoPane;
	@FXML private TableView<Task> taskTable;
	@FXML private TableView<Task> doneTable;
	@FXML private TableColumn<Task, String> taskIDCol;
	@FXML private TableColumn<Task, String> taskTitleCol;
	@FXML private TableColumn<Task, String> taskDueDateCol;
	@FXML private TableColumn<Task, String> taskPriorityCol;
	@FXML private TableColumn<Task, String> taskTagsCol;
	@FXML private TableColumn<Task, String> doneIDCol;
	@FXML private TableColumn<Task, String> doneTitleCol;
	@FXML private TableColumn<Task, String> doneDueDateCol;
	@FXML private TableColumn<Task, String> donePriorityCol;
	@FXML private TableColumn<Task, String> doneTagsCol;
	@FXML private Label messageLabel;
	@FXML private TextField commandLineField;
	@FXML private TextArea helpTextArea;

	private void initialize() throws Exception {

		main.Magical.init();
		GUIModel.init();

		taskTable.setItems(FXCollections.observableArrayList(GUIModel.taskList));
		doneTable.setItems(FXCollections.observableArrayList(GUIModel.doneList));

		taskIDCol.setCellFactory(col -> {
		    TableCell<Task, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("t", cell.indexProperty().add(1).asString())));
		    return cell ;
		});

		taskTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

		taskTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			String result = "";
			Set<String> tagSet = col.getValue().getTags();
			if (!tagSet.isEmpty()) {
				Iterator<String> iterator = tagSet.iterator();
				while (iterator.hasNext()) {
					result += iterator.next() + ", ";
				}
				result = result.substring(0, result.lastIndexOf(","));
			}
			finalResult.setValue(result);
			return finalResult;
		});

		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		taskPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));

		doneIDCol.setCellFactory(col -> {
		    TableCell<Task, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("d", cell.indexProperty().add(1).asString())));
		    return cell;
		});

		doneTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

		doneTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			String result = "";
			Set<String> tagSet = col.getValue().getTags();
			if (!tagSet.isEmpty()) {
				Iterator<String> iterator = tagSet.iterator();
				while (iterator.hasNext()) {
					result += iterator.next() + ", ";
				}
				result = result.substring(0, result.lastIndexOf(","));
			}
			finalResult.setValue(result);
			return finalResult;
		});

		doneDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		donePriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));

		Platform.runLater(new Runnable() {
		    public void run() {
		        commandLineField.requestFocus();
		    }
		});

	}


	@FXML
	protected void onEnterPressed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			String userInput = commandLineField.getText();
			String message = Magical.parseCommand(userInput);
			messageLabel.setText(message);
			taskTable.setItems(GUIModel.getTaskList());
			doneTable.setItems(GUIModel.getDoneList());
			commandLineField.clear();
		}
		if (GUIModel.showHelpWindow) {
			Stage helpStage = new Stage();
			helpStage.setTitle("Help");
			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/gui/HelpFXML.fxml"))	;
			Scene myScene = new Scene(myPane);
			helpStage.setScene(myScene);
			helpStage.show();
			GUIModel.showHelpWindow = false;
		}
	}

}
