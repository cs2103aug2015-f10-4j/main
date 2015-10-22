package main;

import java.util.ArrayList;

public class GUIModel {

	public static ArrayList<Task> taskList;

	public static void init() {
		taskList = Magical.upcomingTasks();
	}

}
