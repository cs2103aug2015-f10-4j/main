package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "Welcome to Magical!";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String DISPLAY_TASK_LONG = "";
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void start() {
		displayWelcomeMessage();
	}
	
	private static String readInput() {
		showToUser(MESSAGE_COMMAND_PROMPT);
		System.out.print("> ");
		String userInput = scanner.nextLine();
		return userInput;
	}
	
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
	private static void displayWelcomeMessage() {
		showToUser(MESSAGE_WELCOME);
	}
	
	private static void displayErrorMessage() {
		showToUser(MESSAGE_ERROR);
	}
	
	private static void displayTasks(ArrayList<Task> tasks) {
		
	}
	
}
