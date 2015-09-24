package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "*:.。.☆ Welcome to Magical! ☆.。.:*";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String FORMAT_DIVIDER = "----------------------\n";
	private static final String FORMAT_TASK = "[%s] %s\n"+ FORMAT_DIVIDER +"Description: %s\nDue Date: %s";
	
	private static Scanner scanner = new Scanner(System.in);
	
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
	
	public static void displayErrorMessage() {
		showToUser(MESSAGE_ERROR);
	}
	
	private static void displayTaskInFull(Task task) {
		showToUser(String.format(FORMAT_TASK, "t1", task.getTitle(),
				task.getDescription(), task.getDueDate()));
	}
	
}
