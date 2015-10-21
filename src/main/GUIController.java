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

import Commands.Command;
import main.GUIModel.TaskView;

public class GUIController {

	@FXML private TextField commandLineField;
	@FXML private Label messageLabel;
	@FXML private TableView<Task> taskTable;
	@FXML private TableColumn<Task, String> taskIdCol;
	@FXML private TableColumn<Task, String> taskTitleCol;

	
	public static void initialize() {

	}
	
	@FXML
	public void enterPressed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			String userCommand = commandLineField.getText();
			try {
				Command command = Magical.parseInput(userCommand);
				String message = command.execute();
				messageLabel.setText(message);
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
	}
	







}
