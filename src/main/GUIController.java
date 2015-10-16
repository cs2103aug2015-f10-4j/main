package main;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import jdk.nashorn.internal.objects.annotations.Constructor;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.util.*;

import main.GUIModel.TaskView;

public class GUIController {

	@FXML private TextField commandLineField;
	@FXML private Label errorMessage;
	@FXML private TableView<Task> taskTable;
	@FXML private TableColumn<Task, String> taskIdCol;
	@FXML private TableColumn<Task, String> taskTitleCol;

	public static GUIModel model;
	
	
	public String userInput;

	public void initialize() {
		model = new GUIModel();
		assert(model.currentTaskView == TaskView.DISPLAY_ALL);
		Task task1 = new Task();
		task1.setTitle("Do CS2103 homework");
		task1.setDueDate(new Date(115, 11, 11));
		task1.setPriority(1);
		ArrayList<Task> tList = new ArrayList<Task>();
		tList.add(task1);


		taskTable.setItems(FXCollections.observableArrayList(task1));
	}

	@FXML
	public void handleEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String userCommand = commandLineField.getText();
			errorMessage.setText(userCommand);
		}
	}







}
