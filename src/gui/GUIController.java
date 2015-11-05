package gui;

import java.util.Iterator;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Magical;
import main.Task;

public class GUIController {

	@FXML private TitledPane toDoPane;

	// TASKS
	@FXML private TableView<Task> taskTable;
	@FXML private TableColumn<Task, String> taskIDCol;
	@FXML private TableColumn<Task, String> taskTitleCol;
	@FXML private TableColumn<Task, String> taskDueDateCol;
	@FXML private TableColumn<Task, String> taskPriorityCol;
	@FXML private TableColumn<Task, String> taskTagsCol;
	// DONE TASKS
	@FXML private TableView<Task> taskDoneTable;
	@FXML private TableColumn<Task, String> taskDoneIDCol;
	@FXML private TableColumn<Task, String> taskDoneTitleCol;
	@FXML private TableColumn<Task, String> taskDoneDueDateCol;
	@FXML private TableColumn<Task, String> taskDonePriorityCol;
	@FXML private TableColumn<Task, String> taskDoneTagsCol;
	// EVENTS
	@FXML private TableView<Task> eventTable;
	@FXML private TableColumn<Task, String> eventIDCol;
	@FXML private TableColumn<Task, String> eventTitleCol;
	@FXML private TableColumn<Task, String> eventDateCol;
	@FXML private TableColumn<Task, String> eventPriorityCol;
	@FXML private TableColumn<Task, String> eventTagsCol;
	// DONE EVENTS
	@FXML private TableView<Task> eventDoneTable;
	@FXML private TableColumn<Task, String> eventDoneIDCol;
	@FXML private TableColumn<Task, String> eventDoneTitleCol;
	@FXML private TableColumn<Task, String> eventDoneDateCol;
	@FXML private TableColumn<Task, String> eventDonePriorityCol;
	@FXML private TableColumn<Task, String> eventDoneTagsCol;

	// Controls
	@FXML private TabPane tabPane;
	@FXML private Tab taskTab;
	@FXML private Tab eventTab;
	@FXML private Label messageLabel;
	@FXML private TextField commandLineField;
	@FXML private TextArea helpTextArea;

	public void initialize() throws Exception {

		main.Magical.init();
		gui.GUIModel.init();

		taskTable.setItems(GUIModel.getTaskList());
		taskDoneTable.setItems(GUIModel.getTaskDoneList());
		eventTable.setItems(GUIModel.getEventList());
		eventDoneTable.setItems(GUIModel.getEventDoneList());

		/*
		 * TASK TABLE
		 */

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

		/*
		 * EVENT TABLE
		 */
		eventIDCol.setCellFactory(col -> {
		    TableCell<Task, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("e", cell.indexProperty().add(1).asString())));
		    return cell ;
		});


		eventTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

		eventTagsCol.setCellValueFactory(col -> {
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

		eventDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		eventPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));


		/*
		 * DONE TASKS TABLE
		 */

		taskDoneIDCol.setCellFactory(col -> {
		    TableCell<Task, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("d", cell.indexProperty().add(1).asString())));
		    return cell;
		});

		taskDoneTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

		taskDoneTagsCol.setCellValueFactory(col -> {
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

		taskDoneDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		taskDonePriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));

		/*
		 * DONE EVENTS TABLE
		 */

		eventDoneIDCol.setCellFactory(col -> {
		    TableCell<Task, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("p", cell.indexProperty().add(1).asString())));
		    return cell;
		});

		eventDoneTitleCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

		eventDoneTagsCol.setCellValueFactory(col -> {
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

		eventDoneDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		eventDonePriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));

		Platform.runLater(new Runnable() {
			@Override
		    public void run() {
		        commandLineField.requestFocus();
		    }
		});

	}

	public void switchToTab(String type) {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		if (type == "tasks") {
			selectionModel.select(taskTab);
		} else if (type == "events") {
			selectionModel.select(eventTab);
		}
		return;
	}



	@FXML
	protected void onEnterPressed(KeyEvent event) throws Exception {

		if (event.getCode() == KeyCode.ENTER) {
			String userInput = commandLineField.getText();
			try {
				messageLabel.setTextFill(Color.web("#0000ff"));
				String message = main.Magical.parseCommand(userInput);
				messageLabel.setText(message);
				taskTable.setItems(GUIModel.getTaskList());
				taskDoneTable.setItems(GUIModel.getTaskDoneList());
				eventTable.setItems(GUIModel.getEventList());
				eventDoneTable.setItems(GUIModel.getEventDoneList());
				commandLineField.clear();
				switchToTab(GUIModel.getCurrentTab());
			} catch (Exception e) {
				messageLabel.setTextFill(Color.web("#ff0000"));
				messageLabel.setText(e.getMessage());
			}
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

	@FXML
	protected void handleTaskTabClicked() {
		GUIModel.setCurrentTab("tasks");
	}

	@FXML
	protected void handleEventTabClicked() {
		GUIModel.setCurrentTab("events");
	}



}
