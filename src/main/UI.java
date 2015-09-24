package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "Welcome to Magical!";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void start() {
		displayWelcomeMessage();
		while(true) {
			showToUser(MESSAGE_COMMAND_PROMPT);
			System.out.print("> ");
			String userInput = scanner.nextLine();
			showToUser("You said: " + userInput );
		}
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
	
}
