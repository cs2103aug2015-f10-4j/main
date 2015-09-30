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
import javafx.scene.input.*;
import javafx.stage.Stage;

public class GUI extends Application {
	
	private static final int VBOX_PADDING = 10;
	private static final int DEFAULT_WINDOW_WIDTH = 600;
	private static final int DEFAULT_WINDOW_HEIGHT = 800;
	
	private Label whatYouJustSaid = new Label("");
	
	
	private TableView<Task> eventTable = new TableView<Task>();
	
	private VBox vbox = new VBox(VBOX_PADDING);
	private Scene scene = new Scene(vbox, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	
	
	public ArrayList<Task> tasksToDisplay = new ArrayList<Task>();
	public ArrayList<Task> eventsToDisplay = new ArrayList<Task>();

	
	private void makeTestTasks() {
		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTitle("Get milk");
		task1.setDueDate(new Date(115, 8, 30));
		task2.setTitle("Do homework");
		task2.setDueDate(new Date(115, 9, 4));
		tasksToDisplay.add(task1);
		tasksToDisplay.add(task2);
		
		Task event1 = new Task();
		event1.setTitle("Dinner with Prof");
		event1.setDueDate(new Date(115, 9, 3, 19, 30));
		eventsToDisplay.add(event1);
	}
	
	
	private Label makeHeader(String text) {
		Label header = new Label(text);
		header.setFont(new Font("Arial", 30));
		return header;
	}
	
	private void makeVBox() {
    	vbox.setPadding(new Insets(VBOX_PADDING, VBOX_PADDING, VBOX_PADDING, VBOX_PADDING));
    	vbox.getChildren().addAll(makeHeader("To-Do"), makeTaskTable(tasksToDisplay),
    			makeHeader("Upcoming Events"), makeEventTable(eventsToDisplay), makeCommandLine(),
    			whatYouJustSaid);

	}

	private TableView<Task> makeTaskTable(ArrayList<Task> taskList) {
		
		TableView<Task> taskTable = new TableView<Task>();
		
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
	
		taskTable.getColumns().addAll(colList);
		
		ObservableList<Task> tasks = FXCollections.observableArrayList(taskList);
		taskTable.setItems(tasks);
		taskTable.setEditable(false);
		
		return taskTable;
		
	}
	
	private TableView<Task> makeEventTable(ArrayList<Task> eventList) {
		TableView<Task> eventTable = new TableView<Task>();
		
		TableColumn<Task, String> taskIdCol = new TableColumn<Task, String>("ID");
		
		TableColumn<Task, String> taskNameCol = new TableColumn<Task, String>("Event Name");
		taskNameCol.setMinWidth(200);
		taskNameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
		
		TableColumn<Task, String> taskDueDateCol = new TableColumn<Task, String>("Due Date");
		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		
		ArrayList<TableColumn<Task, String>> colList = new ArrayList<TableColumn<Task, String>>();
		colList.add(taskIdCol);
		colList.add(taskNameCol);
		colList.add(taskDueDateCol);
	
		eventTable.getColumns().addAll(colList);
		
		ObservableList<Task> tasks = FXCollections.observableArrayList(eventList);
		eventTable.setItems(tasks);
		eventTable.setEditable(false);
		
		return eventTable;
	}
	
	private TextField makeCommandLine() {
		TextField commandLineTextField = new TextField();
		commandLineTextField.setPromptText("What would you like to do?");
		
		commandLineTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		             whatYouJustSaid.setText("You just said: "+commandLineTextField.getText());
		        }
		    }
		});
		
		return commandLineTextField;
	}
	
    @Override
    public void start(Stage stage) {
    	
    	makeTestTasks();

    	makeVBox();
    	makeCommandLine();

        stage.setTitle("Magical");
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
