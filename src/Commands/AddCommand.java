package Commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import main.CustomDate;
import main.Magical;
import main.RecurrencePeriod;
import main.Storage;
import main.Task;
import gui.GUIModel;

/**
 * @author James
 * Functionality:
 * If date and time specified, but one of them is invalid, the invalid field will use the default.
 * If only date or time is specified, is they are invalid, returns null.
 */
public class AddCommand extends Command{

	/** Command parameters **/
	protected String title;
	protected CustomDate dueDate;
	protected int endTime;
	protected RecurrencePeriod recurrence;
	protected boolean isFloat;
	private Task task;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: \nadd title/due date/end time/recurrence";
	private static final String MESSAGE_INVALID_FLEXI = "Use format: add <title> by <date> <time> <recurrence>";
	private static final String MESSAGE_INVALID_TITLE = "No Title\n";
	private static final String MESSAGE_INVALID_DATE = "Due date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_DATE_TIME = "Date/Time: %s\n(Use dd-MM-yyyy for date and 24hours format for time)";
	private static final String MESSAGE_INVALID_END = "End time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_RECURRENCE = "Recurrence: %s"
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	private static final String MESSAGE_TASK_ADDED = "task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "unable to add task";

	public AddCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split("\\s+by\\s+", 2)));

		for(int i = 0; i < argsArray.size(); i++){
			argsArray.set(i, argsArray.get(i).trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY));
		}

		this.count = argsArray.size();
		System.out.println(argsArray);

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
						error += error += String.format(MESSAGE_INVALID_RECURRENCE, argsArray.get(2));
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
				error += MESSAGE_INVALID_TITLE;
			}

			if(this.dueDate == null && !isFloat){
				error += String.format(MESSAGE_INVALID_DATE_TIME, argsArray.get(1));
			}

			if (recurrence == null && !isFloat) {
				error += String.format(MESSAGE_INVALID_RECURRENCE, argsArray.get(3).trim());
			}

			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			error += MESSAGE_INVALID_FLEXI;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
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
		task = new Task();
		task.setType("task");
		task.setTitle(title);
		task.setRecurrence(recurrence);

		task.setDueDate(dueDate);
		task.setStartTime(-1);
		task.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_TASK_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_TASK_CLASH;
			}
			Magical.storage.create(Storage.TASKS_INDEX, task);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_TASK_ERROR;
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setTaskDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
			GUIModel.setCurrentTab("tasks");
		}
	}

	private boolean isClashing() {
		ArrayList<Task> tasks = Magical.storage.getList(Storage.TASKS_INDEX);
		for (Task t : tasks) {
			if (t.getDueDate() != null && t.getDueDate().equals(task.getDueDate())) {
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
//		AddCommand h = new AddCommand("task by 21/02 12pm");

	}
}
