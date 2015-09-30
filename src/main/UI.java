package main;

import java.util.*;

public class UI {

	private static final String MESSAGE_WELCOME = "*:.。.☆ Welcome to Magical! ☆.。.:*\r\n";
	private static final String MESSAGE_GOODBYE = "*:.。.☆ Farewell! ☆.。.:*\r\n";
	private static final String MESSAGE_COMMAND_PROMPT = "What would you like to do?";
	private static final String MESSAGE_ERROR = "An error has occurred.";
	
	private static final String MESSAGE_TASK_ADDED = "Added task: %s";
	private static final String MESSAGE_TASK_DELETED = "Deleted task: %s";
	
	private static final String DIVIDER = "======================";

	private static final String FORMAT_HEADER = DIVIDER + "\r\n%s\r\n" + DIVIDER;
	private static final String FORMAT_SHORT_TASK = "%s | Due: %s";
	private static final String FORMAT_SHORT_EVENT = "%s on %s at %s";
	
	private static Scanner scanner = new Scanner(System.in);

	/*
	 * PUBLIC METHODS
	 * */
	
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
		if (text != null) {
			System.out.println(text);
		}
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
				"\r\nDue Date: " + makeShortDate(task.getDueDate()) + "\r\n");
		
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1);
	}
	
	private static String makeShortTime(int time) {
		String meridiem;
		int hours = time / 100;
		int minutes = time - (hours*100);
		if (hours < 12) {
			meridiem = " AM";
		} else {
			meridiem = " PM";
		}
		if (minutes < 10) {
			return hours + ":0" + minutes + meridiem;
		}
		return hours + ":" + minutes + meridiem;
	}
}
