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
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Magical;
import main.Item;

public class GUIController {

	@FXML private TitledPane toDoPane;

	// TASKS
	@FXML private TableView<Item> taskTable;
	@FXML private TableColumn<Item, String> taskIDCol;
	@FXML private TableColumn<Item, String> taskTitleCol;
	@FXML private TableColumn<Item, String> taskDueDateCol;
	@FXML private TableColumn<Item, String> taskPriorityCol;
	@FXML private TableColumn<Item, String> taskTagsCol;
	// DONE TASKS
	@FXML private TableView<Item> taskDoneTable;
	@FXML private TableColumn<Item, String> taskDoneIDCol;
	@FXML private TableColumn<Item, String> taskDoneTitleCol;
	@FXML private TableColumn<Item, String> taskDoneDueDateCol;
	@FXML private TableColumn<Item, String> taskDonePriorityCol;
	@FXML private TableColumn<Item, String> taskDoneTagsCol;
	// EVENTS
	@FXML private TableView<Item> eventTable;
	@FXML private TableColumn<Item, String> eventIDCol;
	@FXML private TableColumn<Item, String> eventTitleCol;
	@FXML private TableColumn<Item, String> eventStartDateCol;
	@FXML private TableColumn<Item, String> eventEndDateCol;
	@FXML private TableColumn<Item, String> eventPriorityCol;
	@FXML private TableColumn<Item, String> eventTagsCol;
	// DONE EVENTS
	@FXML private TableView<Item> eventDoneTable;
	@FXML private TableColumn<Item, String> eventDoneIDCol;
	@FXML private TableColumn<Item, String> eventDoneTitleCol;
	@FXML private TableColumn<Item, String> eventDoneStartDateCol;
	@FXML private TableColumn<Item, String> eventDoneEndDateCol;
	@FXML private TableColumn<Item, String> eventDonePriorityCol;
	@FXML private TableColumn<Item, String> eventDoneTagsCol;

	// Controls
	@FXML private TabPane tabPane;
	@FXML private Tab taskTab;
	@FXML private Tab eventTab;
	@FXML private Label messageLabel;
	@FXML private TextField commandLineField;
	@FXML private TextArea helpTextArea;

	/**
	 * This method initializes GUIController when it is called by GUIView.
	 * @throws Exception
	 */

	public void initialize() throws Exception {

		main.Magical.init();
		gui.GUIModel.init();

		taskTable.setItems(GUIModel.getTaskList());
		taskDoneTable.setItems(GUIModel.getTaskDoneList());
		eventTable.setItems(GUIModel.getEventList());
		eventDoneTable.setItems(GUIModel.getEventDoneList());

		// Populate task table columns

		taskIDCol.setCellFactory(col -> {
		    TableCell<Item, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("t", cell.indexProperty().add(1).asString())));
		    return cell ;
		});


		taskTitleCol.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));

		taskTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			Set<String> tagSet = col.getValue().getTags();
			String result = makeTagString(tagSet);
			finalResult.setValue(result);
			return finalResult;
		});

		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("endDate"));
		taskPriorityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("priority"));


		// Populate event table columns

		eventIDCol.setCellFactory(col -> {
		    TableCell<Item, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("e", cell.indexProperty().add(1).asString())));
		    return cell ;
		});


		eventTitleCol.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));

		eventTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			Set<String> tagSet = col.getValue().getTags();
			String result = makeTagString(tagSet);
			finalResult.setValue(result);
			return finalResult;
		});

		eventStartDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("startDate"));
		eventEndDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("endDate"));
		eventPriorityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("priority"));


		// Populate done task table columns

		taskDoneIDCol.setCellFactory(col -> {
		    TableCell<Item, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("d", cell.indexProperty().add(1).asString())));
		    return cell;
		});

		taskDoneTitleCol.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));

		taskDoneTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			Set<String> tagSet = col.getValue().getTags();
			String result = makeTagString(tagSet);
			finalResult.setValue(result);
			return finalResult;
		});

		taskDoneDueDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("endDate"));
		taskDonePriorityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("priority"));


		// Populate done event table columns

		eventDoneIDCol.setCellFactory(col -> {
		    TableCell<Item, String> cell = new TableCell<>();
		    cell.textProperty().bind(Bindings.when(cell.emptyProperty())
		        .then("")
		        .otherwise(Bindings.concat("p", cell.indexProperty().add(1).asString())));
		    return cell;
		});

		eventDoneTitleCol.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));

		eventDoneTagsCol.setCellValueFactory(col -> {
			SimpleStringProperty finalResult = new SimpleStringProperty();
			Set<String> tagSet = col.getValue().getTags();
			String result = makeTagString(tagSet);
			finalResult.setValue(result);
			return finalResult;
		});

		eventDoneStartDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("startDate"));
		eventDoneEndDateCol.setCellValueFactory(new PropertyValueFactory<Item, String>("endDate"));
		eventDonePriorityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("priority"));

		Platform.runLater(new Runnable() {
			@Override
		    public void run() {
		        commandLineField.requestFocus();
		    }
		});

	}

	/**
	 * This method switches the currently selected tab.
	 * @param type - either "tasks" or "events"
	 */

	public void switchToTab(String type) {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		if (type == "tasks") {
			selectionModel.select(taskTab);
		} else if (type == "events") {
			selectionModel.select(eventTab);
		}
		return;
	}

	/**
	 * This method converts a tagSet into a printable String to populate
	 * Tags columns with.
	 * @param tagSet - set of tags from a Task object
	 * @return String
	 */

	private String makeTagString(Set<String> tagSet) {
		String result = "";
		if (!tagSet.isEmpty()) {
			Iterator<String> iterator = tagSet.iterator();
			while (iterator.hasNext()) {
				result += iterator.next() + ", ";
			}
			result = result.substring(0, result.lastIndexOf(","));
		}
		return result;
	}


	/**
	 * This method handles input from commandLineField by checking if the
	 * Enter key has been pressed, reading user input and passing it to
	 * the main application logic via main.Magical.parseCommand. If the
	 * user input throws an exception, the error message is printed into
	 * the label above the command line.
	 * This method also checks if GUIModel.showHelpWindow is true, and
	 * opens the help window accordingly.
	 * @param event - Enter pressed
	 * @throws Exception
	 */

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
			AnchorPane myPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/gui/HelpFXML.fxml"))	;
			Scene myScene = new Scene(myPane);
			helpStage.setScene(myScene);
			helpStage.show();
			GUIModel.showHelpWindow = false;
		}
	}

	/**
	 * These methods set the current tab of the GUIModel to the
	 * tab last clicked by the user. They are assigned to their
	 * respective tabs in the FXML file.
	 */
	@FXML
	protected void handleTaskTabClicked() {
		GUIModel.setCurrentTab("tasks");
	}

	@FXML
	protected void handleEventTabClicked() {
		GUIModel.setCurrentTab("events");
	}



}
