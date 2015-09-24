package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "*:.。.☆ Welcome to Magical! ☆.。.:*\n";
	private static final String MESSAGE_GOODBYE = "*:.。.☆ Farewell! ☆.。.:*\n";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String DIVIDER = "======================";

	private static final String FORMAT_HEADER = DIVIDER + "\n%s\n" + DIVIDER;
	private static final String FORMAT_SHORT_TASK = "%s | Due: %s";
	private static final String FORMAT_SHORT_EVENT = "%s on %s at %s";
	
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String args[]) {
		start();
		ArrayList<Task> myTasks = new ArrayList<Task>();
		ArrayList<Task> myEvents = new ArrayList<Task>();
		for (int i = 0; i < 3; i++) {
			myTasks.add(new Task());	
		}
		myTasks.get(0).setTitle("Do this");
		myTasks.get(0).setDueDate(new Date(115, 8, 24));
		myTasks.get(0).setDescription("Or you will cry & die");
		myTasks.get(1).setTitle("Do that");		
		myTasks.get(2).setDueDate(new Date(115, 9, 2));
		myTasks.get(2).setTitle("Do some other stuff");		
		displayTaskList("To-Do", myTasks);
		myEvents.add(new Task());
		myEvents.get(0).setTitle("Commit suicide");
		myEvents.get(0).setDueDate(new Date(115, 9, 4));
		myEvents.get(0).setStartTime(1108);
		displayEventList("Upcoming Events", myEvents);
		displayTaskDetails(myTasks.get(0));
		displayGoodbyeMessage();
	}

	/*
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Magical V0.0");
        Text scenetitle = new Text(MESSAGE_WELCOME);
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 500, 720));
        primaryStage.show();
    }
	
	*/
	
	/*
	 * PUBLIC METHODS
	 */
	
	public static void start() {
		displayWelcomeMessage();
	}
	
	public static String readInput() {
		showToUser(MESSAGE_COMMAND_PROMPT);
		System.out.print("> ");
		String userInput = scanner.nextLine();
		return userInput;
	}
	
	public static void showToUser(String text) {
		System.out.println(text);
	}
	
	public static void displayWelcomeMessage() {
		showToUser(MESSAGE_WELCOME);
	}
	
	public static void displayGoodbyeMessage() {
		showToUser(MESSAGE_GOODBYE);
	}
	
	public static void displayErrorMessage() {
		showToUser(MESSAGE_ERROR);
	}

	public static void displayTaskDetails(Task task) {
		displayHeader(task.getTitle());
		showToUser("Description: " + task.getDescription() +
				"\nDue Date: " + makeShortDate(task.getDueDate()) + "\n");
		
	}
	
	public static void displayTaskList(String header, ArrayList<Task> taskList) {
		displayHeader(header);
		Iterator<Task> iterator = taskList.iterator();
		int index = 0;
		while(iterator.hasNext()) {
			index++;
			showToUser("t" + index + ". "+ makeShortTask(iterator.next()));
		}
		showToUser("");
	}
	
	public static void displayEventList(String header, ArrayList<Task> eventList) {
		displayHeader(header);
		Iterator<Task> iterator = eventList.iterator();
		int index = 0;
		while(iterator.hasNext()) {
			index++;
			showToUser("e" + index + ". "+ makeShortEvent(iterator.next()));
		}
		showToUser("");
	}
	
	/*
	 * PRIVATE METHODS
	 */
	
	private static void displayHeader(String header) {
		showToUser(String.format(FORMAT_HEADER, header));
	}
	
	private static String makeShortTask(Task task) {
		if (task.getDueDate() == null) { // if floating task
			return task.getTitle();
		}
		return String.format(FORMAT_SHORT_TASK, task.getTitle(), makeShortDate(task.getDueDate()));
	}
	
	private static String makeShortEvent(Task event) {
		return String.format(FORMAT_SHORT_EVENT, event.getTitle(),
				makeShortDate(event.getDueDate()), makeShortTime(event.getStartTime()));
	}
	
	private static String makeShortDate(Date date) {
		return date.getDate() + "/" + (date.getMonth()+1);
	}
	
	private static String makeShortTime(int time) {
		return "" + time;
	}
}
