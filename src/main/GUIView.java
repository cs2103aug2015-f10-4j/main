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

/*
 * TYPES OF VIEWS
 * 1. DISPLAY_ALL: displays 3 tables of tasks -- to-do, events, and blocked dates
 * 2. DISPLAY_ONE: displays the full details of a particular task/event
 */
public class GUIView extends Stage {
	
	enum ViewType {
		DISPLAY_ALL, DISPLAY_ONE
	}
	
	private static final int VBOX_PADDING = 10;
	private static final int DEFAULT_WINDOW_WIDTH = 400;
	private static final int DEFAULT_WINDOW_HEIGHT = 600;
	private static final String DEFAULT_FONT = "Arial";
	
	private static final Font FONT_HEADER = new Font(DEFAULT_FONT, 24);
	private static final Font FONT_SUBHEADER = new Font(DEFAULT_FONT, 18);
	private static final Font FONT_BODY = new Font(DEFAULT_FONT, 12);
	
	private static final Label LABEL_TODO = makeLabel("To-Do", FONT_HEADER);
	private static final Label LABEL_EVENTS = makeLabel("Upcoming Events", FONT_HEADER);
	private static final Label LABEL_BLOCKED = makeLabel("Blocked Dates", FONT_HEADER);
	
	private static final String DATE_FORMAT = "%s %s %s, %s";
	private static final String WELCOME_MESSAGE_FORMAT = "Welcome, %s! You have %s tasks due today.";
	
	private TableView<Task> eventTable = new TableView<Task>();
	
	private VBox vbox = new VBox(VBOX_PADDING);
	private Scene scene = new Scene(vbox, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	
	
	public static ArrayList<Task> taskList = new ArrayList<Task>();
	public static ArrayList<Task> eventList = new ArrayList<Task>();
	public static ArrayList<Task> blockedDatesList = new ArrayList<Task>();
	public static Task currentTask = new Task();
	public static ViewType currentViewType;
	
	public String userInput;
	
	private static Label makeLabel(String text, Font font) {
		Label label = new Label(text);
		label.setFont(font);
		return label;
	}
	
	private Label errorMessage = makeLabel("", FONT_BODY);
	
	private void makeVBox(ViewType viewType) {
    	vbox.setPadding(new Insets(VBOX_PADDING, VBOX_PADDING, VBOX_PADDING, VBOX_PADDING));
    	switch (viewType) {
    	case DISPLAY_ALL:
    		vbox.getChildren().addAll(LABEL_TODO, makeTaskTable(taskList),
        			LABEL_EVENTS, makeEventTable(eventList));
    	case DISPLAY_ONE:
    		vbox.getChildren().addAll(makeLabel(currentTask.getTitle(), FONT_HEADER),
    									makeLabel("Description", FONT_SUBHEADER),
    									makeLabel(currentTask.getDescription(), FONT_BODY),
    									makeLabel("Due Date", FONT_SUBHEADER),
    									makeLabel(makeDateString(currentTask.getDueDate()), FONT_BODY),
    									makeLabel("Priority", FONT_SUBHEADER),
    									makeLabel("" + currentTask.getPriority(), FONT_BODY),
    									makeLabel("Tags", FONT_SUBHEADER),
    									makeLabel(makeTagString(currentTask.getTags()), FONT_BODY));
    	}
    	vbox.getChildren().addAll(errorMessage, makeCommandLine());

	}

	private TableView<Task> makeTaskTable(ArrayList<Task> taskList) {
		
		TableView<Task> taskTable = new TableView<Task>();
		
		TableColumn<Task, String> taskIdCol = new TableColumn<Task, String>("ID");
		
		TableColumn<Task, String> taskNameCol = new TableColumn<Task, String>("Task Name");
		taskNameCol.setMinWidth(200);
		taskNameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
		
		TableColumn<Task, Date> taskDueDateCol = new TableColumn<Task, Date>("Due Date");
		taskDueDateCol.setCellValueFactory(new PropertyValueFactory<Task, Date>("dueDate"));
		
		TableColumn<Task, String> taskPriorityCol = new TableColumn<Task, String>("Priority");
		taskPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
		
		ArrayList<TableColumn<Task, ?>> colList = new ArrayList<TableColumn<Task, ?>>();
		colList.add(taskIdCol);
		colList.add(taskNameCol);
		colList.add(taskDueDateCol);
		colList.add(taskPriorityCol);
	
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
		
		TableColumn<Task, String> taskPriorityCol = new TableColumn<Task, String>("Priority");
		taskPriorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
		
		ArrayList<TableColumn<Task, String>> colList = new ArrayList<TableColumn<Task, String>>();
		colList.add(taskIdCol);
		colList.add(taskNameCol);
		colList.add(taskDueDateCol);
		colList.add(taskPriorityCol);
	
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
		        	userInput = commandLineTextField.getText();
		        	errorMessage.setText("You just said: " + commandLineTextField.getText());
		        }
		    }
		});
		
		return commandLineTextField;
	}
	
    public void initialize() {
    	setTitle("Magical");
        setScene(scene);
        
    	currentViewType = ViewType.DISPLAY_ONE;
    	makeVBox(currentViewType);
    	makeCommandLine();
    }
    
    private static final String[] DAYS_ARRAY = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String[] MONTHS_ARRAY = {"January", "February", "March", "April", "May", "June", "July",
    										"August", "Septembers", "October", "November", "December"};
    
    public static String makeDateString(Date date) {
    	return String.format(DATE_FORMAT, date.getDate()+1, MONTHS_ARRAY[date.getMonth()], date.getYear()+1900, DAYS_ARRAY[date.getDay()]);
    }
    
    public static String makeTagString(Set<String> tagSet) {
    	Iterator<String> iterator = tagSet.iterator();
    	String result = "";
    	for (int i = 0; i < tagSet.size()-1; i ++) {
    		result += iterator.next() + ", ";
    	}
    	result += iterator.next();
    	return result;
    }
    
    
    public static void main(String[] args) {
    	
    	HashSet<String> tagSet = new HashSet<String>();
    	tagSet.add("tag1");
    	tagSet.add("tag2");
    	
    	Task task1 = new Task();
    	task1.setTitle("Test task");
    	task1.setDescription("Task description goes here!");
    	task1.setDueDate(new Date(114, 12, 11));
    	task1.setPriority(2);
    	task1.setTags(tagSet);
    	
    	taskList.add(task1);
    	currentTask = task1;

    }
}
