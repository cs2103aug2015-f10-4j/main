package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "*:.。.☆ Welcome to Magical! ☆.。.:*";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String DIVIDER = "======================";

	private static final String FORMAT_HEADER = "%s\n" + DIVIDER;
	private static final String FORMAT_TASK = "[t%s] %s\n"+ DIVIDER +"Description: %s\nDue Date: %s";
	private static final String FORMAT_EVENT = "[e%s] %s\n" + DIVIDER + "";

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String args[]) {
		start();
		displaySection("Upcoming Events", "some random shit");
		displaySection("To-Do", "ur mom");
	}
	
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
	
	public static void displaySection(String header, String contents) {
		showToUser(DIVIDER);
		showToUser(String.format(FORMAT_HEADER, header));
		showToUser(contents+"\n");
	}
	
	public static void displayWelcomeMessage() {
		showToUser(MESSAGE_WELCOME);
	}
	
	public static void displayErrorMessage() {
		showToUser(MESSAGE_ERROR);
	}
	
	private static String makeTaskDisplay(Task task) {
		return String.format(FORMAT_TASK, 1, task.getTitle(), task.getDescription(), task.getDueDate());
	}
	
}
