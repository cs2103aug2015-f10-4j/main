package Commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import main.CustomDate;
import main.Magical;
import main.RecurrencePeriod;
import main.Storage;
import main.Item;
import gui.GUIModel;

public class AddCommand extends Command{

	/** Command parameters **/
	protected String title;
	protected CustomDate dueDate;
	protected int endTime;
	protected RecurrencePeriod recurrence;
	protected boolean isFloat;
	private Item item;

	/** Messaging */
	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time> <recurrence>";
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";

	public AddCommand(String args) throws Exception {
		super(args);

		this.argsArray = splitArgs(args, "\\s+by\\s+", 2);

		removeEscapeCharacters();

		this.count = argsArray.size();

		if(argsArray.size() > 1 && argsArray.get(count-1).contains(" ")){
			while(true){
				String last = argsArray.get(count-1).split("\\s(?=\\S+$)")[1];
				if(getRecurrence(last) == null){
					if(getDate(last) != null){
						break;
					} else {
						argsArray.add(count, last);
						argsArray.set(count-1, argsArray.get(count-1).split("\\s(?=\\S+$)")[0]);
						System.out.println(argsArray);
					}
				} else {
					argsArray.add(count, last);
					argsArray.set(count-1, argsArray.get(count-1).split("\\s(?=\\S+$)")[0]);
					break;
				}
			}
		}
		System.out.println(argsArray);
		this.count = argsArray.size();
		this.isFloat = false;

		if(validNumArgs()){

			this.title = getTitle(argsArray.get(0).trim());
			if(count > 1){
				this.dueDate = getDate(argsArray.get(1).trim());
				System.out.println(dueDate);
				this.endTime = dueDate == null ? -1 : dueDate.getTime();

				if(count > 2){
					this.recurrence = getRecurrence(argsArray.get(2));
					if(this.recurrence == null){
						invalidArgs.add("recurrence");
					}
				} else {
					this.recurrence = getRecurrence(STRING_EMPTY);
				}
			} else {
				this.dueDate = null;
				this.endTime = -1;
				this.recurrence = getRecurrence(STRING_EMPTY);
				this.isFloat = true;
			}

			if (title == null) {
				invalidArgs.add("title");
			}

			if(this.dueDate == null && !isFloat){
				invalidArgs.add("date");
				invalidArgs.add("time");
			}

			if (recurrence == null && !isFloat) {
				invalidArgs.add("recurrence");
			}

			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID + String.join(", ", invalidArgs));
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_FORMAT);
		}
	}

	private void removeEscapeCharacters() {
		for(int i = 0; i < argsArray.size(); i++){
			argsArray.set(i, argsArray.get(i).trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY));
		}
	}

	public boolean validNumArgs(){
		if(this.count > 3){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String execute() {
		item = new Item();
		item.setType("task");
		item.setTitle(title);
		item.setRecurrence(recurrence);
		item.setStartDate(null);
		item.setStartTime(-1);
		item.setEndDate(dueDate);
		item.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_TASK_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_TASK_CLASH;
			}
			Magical.getStorage().create(Storage.TASKS_INDEX, item);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
		} finally {
			GUIModel.setTaskList(Magical.getStorage().getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.getStorage().getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setCurrentTab("tasks");
		}
	}

	private boolean isClashing() {
		ArrayList<Item> tasks = Magical.getStorage().getList(Storage.TASKS_INDEX);
		for (Item t : tasks) {
			if (t.getEndDate() != null && t.getEndDate().equals(item.getEndDate())) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
//		AddCommand a = new AddCommand("wash my butt by 1st January at 12pm");
//		AddCommand b = new AddCommand("pass \\by the river \\at St.George by 1st January at 12pm");
//		AddCommand c = new AddCommand("smack him by 12-01-1993 at 1pm");
//		AddCommand d = new AddCommand("");
//		AddCommand e = new AddCommand("hihihihi by hi at hi");
//		AddCommand f = new AddCommand("go on stand \\by the hill by 12pm Monday daily asdgasgd asgas");
//		AddCommand g = new AddCommand("go on stand \\by the hill by 12a0193 12pm");
		AddCommand h = new AddCommand("task by tuesday 1pm msonthly");
//		System.out.println(Arrays.toString("a b c d".split(" ", 0)));

	}
}
