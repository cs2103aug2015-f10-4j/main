package main;

import java.util.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {
	
	private static final int VBOX_PADDING = 10;
	private static final int DEFAULT_WINDOW_WIDTH = 600;
	private static final int DEFAULT_WINDOW_HEIGHT = 800;
	
	private TableView<Task> taskTable = new TableView<Task>();
	private TextField commandLineTextField = new TextField();
	private VBox vbox = new VBox(VBOX_PADDING);
	private Scene scene = new Scene(vbox, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	private Label header = new Label("My Tasks");
	
	
	private void makeHeader() {
		header.setFont(new Font("Arial", 30));
	}
	
	private void makeVBox() {
    	vbox.setPadding(new Insets(VBOX_PADDING, VBOX_PADDING, VBOX_PADDING, VBOX_PADDING));
    	vbox.getChildren().addAll(header, taskTable, commandLineTextField);

	}

	private void makeTable() {
		
		taskTable.setMinHeight(650);
		
		TableColumn<Task, String> taskIdCol = new TableColumn<Task, String>("ID");
		
		TableColumn<Task, String> taskNameCol = new TableColumn<Task, String>("Task Name");
		taskNameCol.setMinWidth(200);
		taskNameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
		
		TableColumn<Task, String> taskDueDateCol = new TableColumn<Task, String>("Due Date");
		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		
		ArrayList<TableColumn<Task, String>> colList = new ArrayList<TableColumn<Task, String>>();
		colList.add(taskIdCol);
		colList.add(taskNameCol);
		colList.add(taskDueDateCol);
		
		ObservableList<Task> tasksObservableList;
		taskTable.getColumns().addAll(colList);
		
		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTitle("Get milk");
		task1.setDueDate(new Date(115, 8, 30));
		task2.setTitle("Do homework");
		task2.setDueDate(new Date(115, 9, 4));
		
		
		ObservableList<Task> tasks = FXCollections.observableArrayList(task1, task2);
		taskTable.setItems(tasks);
		taskTable.setEditable(false);
		
	}
	
	private void makeCommandLine() {
		commandLineTextField.setPromptText("What would you like to do?");
	}
	
    @Override
    public void start(Stage stage) {

    	makeHeader();
    	makeVBox();
    	makeTable();
    	makeCommandLine();


        stage.setTitle("Magical");
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
