package main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {

	// private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created successfully.";
	public static final int NUM_LISTS = 4;
	public static final int TASKS_INDEX = 0;
	public static final int TASKS_DONE_INDEX = 1;
	public static final int EVENTS_INDEX = 2;
	public static final int EVENTS_DONE_INDEX = 3;

	private static File file;
	private List<ArrayList<Task>> lists;
	ObjectMapper mapper = new ObjectMapper();
	private static SimpleDateFormat dateFormat = 
			new SimpleDateFormat("dd MMM yyyy");

	public Storage (String fileName) {
		assert fileName != null;

		file = new File(fileName);
		mapper.setDateFormat(dateFormat);

		if ( !(file.exists()) ) {
			lists = new ArrayList<ArrayList<Task>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Task>());
			}
			try {
				writeLists();
			} catch (IOException e) {
				System.out.println("Storage IOException: File not created successfully");
				return;
			}
		} else {
			readLists();
		}
	}

	protected boolean fileExist() {
		if (file.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static int getListIndex(String id) {
		switch(id.charAt(0)) {
		case 't':
			return TASKS_INDEX;
		case 'd':
			return TASKS_DONE_INDEX;
		case 'e':
			return EVENTS_INDEX;
		case 'p':
			return EVENTS_DONE_INDEX;
		default:
			return -1;
		}
	}
	
	public static int getComplementListIndex(int index) {
		switch(index) {
		case TASKS_INDEX:
			return TASKS_DONE_INDEX;
		case TASKS_DONE_INDEX:
			return TASKS_INDEX;
		case EVENTS_INDEX:
			return EVENTS_DONE_INDEX;
		case EVENTS_DONE_INDEX:
			return EVENTS_INDEX;
		default:
			return -1;
		}
	}
	
	public void create(int listIndex, Task t) throws IOException {
		lists.get(listIndex).add(t);
		writeLists();
	}
	
	public ArrayList<Task> getList(int listIndex) {
		return lists.get(listIndex);
	}
	
	public void update(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).set(pos, t);
			writeLists();
		}
	}
	
	public void delete(int listIndex, Task t) throws IOException {
		int pos = getPos(listIndex, t);
		if (pos > -1) {
			lists.get(listIndex).remove(pos);
			writeLists();
		}
	}

	protected void clear(int listIndex) throws IOException {
		lists.set(listIndex, new ArrayList<Task>());
		writeLists();
	}
	
	protected int getPos(int listIndex, Task t) {
		return lists.get(listIndex).indexOf(t);
	}
	
	public void setList(int listIndex, ArrayList<Task> list) throws IOException {
		lists.set(listIndex, list);
		writeLists();
	}

	protected void writeLists() throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, lists);
	}

	protected void readLists() {
		try {
			lists = mapper.readValue(file, new TypeReference<List<ArrayList<Task>>>() { });
		} catch (Exception e) {
			lists = new ArrayList<ArrayList<Task>>(NUM_LISTS);
			for (int i = 0; i < NUM_LISTS; i++) {
				lists.add(new ArrayList<Task>());
			}
		}
		return;
	}
}
