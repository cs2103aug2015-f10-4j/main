package main;

import java.io.IOException;
import java.util.*;

import Commands.Command;

public class Magical {
	public static Storage storage;
	public static List<Stack<ArrayList<Item>>> undoLists = new ArrayList<Stack<ArrayList<Item>>>(Storage.NUM_LISTS);

	public static void init() throws IOException {
		storage = new Storage();
		for (int i = 0; i < Storage.NUM_LISTS; i++) {
			undoLists.add(new Stack<ArrayList<Item>>());
		}
	}

	public static Storage getStorage(){
		return storage;
	}

	public static String parseCommand(String userInput) throws Exception{
		Command command = Parser.parse(userInput);
		if (command.isUndoable()) {
			pushUndoLayer();
		}
		String message = command.execute();
		return message;
	}

	private static void pushUndoLayer() {
		int n = Storage.TASKS_INDEX;
		ArrayList<Item> t = storage.getList(n);
		ArrayList<Item> prevTasksList = listClone(t);
		ArrayList<Item> prevTasksDoneList = listClone(storage.getList(Storage.TASKS_DONE_INDEX));
		ArrayList<Item> prevEventsList = listClone(storage.getList(Storage.EVENTS_INDEX));
		ArrayList<Item> prevEventsDoneList = listClone(storage.getList(Storage.EVENTS_DONE_INDEX));
		undoLists.get(Storage.TASKS_INDEX).push(prevTasksList);
		undoLists.get(Storage.TASKS_DONE_INDEX).push(prevTasksDoneList);
		undoLists.get(Storage.EVENTS_INDEX).push(prevEventsList);
		undoLists.get(Storage.EVENTS_DONE_INDEX).push(prevEventsDoneList);
	}

	private static ArrayList<Item> listClone(ArrayList<Item> tasks) {
		ArrayList<Item> newList = new ArrayList<Item>(tasks.size());
		try {
			for(Item t : tasks) {
				newList.add(t.copy());
			}
			return newList;
		} catch (Exception e) {
			return tasks;
		}
	}

}
