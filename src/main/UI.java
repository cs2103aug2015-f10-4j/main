package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "*:.。.☆ Welcome to Magical! ☆.。.:*\n";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String DIVIDER = "======================";

	private static final String FORMAT_HEADER = DIVIDER + "\n%s\n" + DIVIDER;
	private static final String FORMAT_SHORT_TASK = "%s | Due: %s";

	private static Scanner scanner = new Scanner(System.in);
	

	public static void main(String args[]) {
		start();
		ArrayList<Task> myTasks = new ArrayList<Task>();
		for (int i = 0; i < 3; i++) {
			myTasks.add(new Task());	
		}
		myTasks.get(0).setTitle("Do this");
		myTasks.get(0).setDueDate(new Date(115, 8, 24));
		myTasks.get(1).setDueDate(new Date(115, 9, 1));
		myTasks.get(1).setTitle("Do that");		
		myTasks.get(2).setDueDate(new Date(115, 9, 2));
		myTasks.get(2).setTitle("Do some other stuff");		
		displayHeader("To-Do");
		displayTaskList(myTasks);
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
	
	public static void displayWelcomeMessage() {
		showToUser(MESSAGE_WELCOME);
	}
	
	public static void displayErrorMessage() {
		showToUser(MESSAGE_ERROR);
	}
	
	private static void displayHeader(String header) {
		showToUser(String.format(FORMAT_HEADER, header));
	}
	
	private static void displayTaskList(ArrayList<Task> taskList) {
		Iterator<Task> iterator = taskList.iterator();
		int index = 0;
		while(iterator.hasNext()) {
			index++;
			showToUser("t" + index + ". "+ makeShortTask(iterator.next()));
		}
	}
	
	private static String makeShortTask(Task task) {
		return String.format(FORMAT_SHORT_TASK, task.getTitle(), makeShortDate(task.getDueDate()));
	}
	
	private static String makeShortDate(Date date) {
		return date.getDate() + "/" + (date.getMonth()+1);
	}
}
