package Commands;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import gui.GUIModel;
import main.Magical;
import main.RecurrencePeriod;
import main.Storage;
import main.Task;

public class EventCommand extends Command{

	/** Command parameters **/
	protected String title;
	protected Date dateStart;
	protected Date dateEnd;
	protected int startTime;
	protected int endTime;
	//protected String recurrence;
	protected RecurrencePeriod recurrence;
	private Task task;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: \nevent title/event date/start time/end time/recurrence";
	private static final String MESSAGE_INVALID_FLEXI = "Use format: event <title> on <date> from "
			+ "<start time> to <end time>";
	private static final String MESSAGE_INVALID_TITLE = "No Title" + "\n";
	private static final String MESSAGE_INVALID_DATE = "Event date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_START = "Start time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_END = "End time: %s (Time should be in 24hrs format)\n";
	private static final String MESSAGE_INVALID_RECURRENCE = "Recurrence: %s"
			+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
	private static final String MESSAGE_EVENT_ADDED = "event added";
	private static final String MESSAGE_EVENT_CLASH = ". Another event exists on the same date.";
	private static final String MESSAGE_EVENT_ERROR = "unable to add event";
	
	public EventCommand(String args) throws Exception {
		super(args);
		
		isFlexi = !args.contains("/") || !args.replace("\\/", STRING_EMPTY).contains("/");
		if(!isFlexi){
			this.argsArray = args.split("(?<![\\\\])/", -1);
			for (int i = 0; i < argsArray.length; i++){
				String param = argsArray[i];
				param = param.replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
				argsArray[i] = param;
			}
		} else {
			this.argsArray = args.split("(?<=\\s)to(?=\\s)|(?<=\\s)from(?=\\s)|(?<=\\s)on(?=\\s)", -1);
			for(int i = 0; i < argsArray.length; i++){
				argsArray[i] = argsArray[i].trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
			}
		}
		this.count = argsArray.length;
		
		for(int i = 0; i < count; i++){
			assertNotNull(argsArray[i]);
		}
		
		if(!isFlexi){
			if(validNumArgs()){

				this.title = getTitle(argsArray[0].trim());
				this.dateStart = getDate(argsArray[1].trim());
				this.dateEnd = getDate(argsArray[1].trim());
				this.startTime = getTime(argsArray[2].trim());
				this.endTime = getTime(argsArray[3].trim());
				this.recurrence = getRecurrence(argsArray[4].trim());

				if (title == null) {
					error += MESSAGE_INVALID_TITLE;
				}
				if (dateStart == null || dateEnd == null) {
					error +=  String.format(MESSAGE_INVALID_DATE, argsArray[1].trim());
				}
				if (startTime == -1) {
					error += String.format(MESSAGE_INVALID_START, argsArray[2].trim());
				}
				if (endTime == -1) {
					error += String.format(MESSAGE_INVALID_END, argsArray[3].trim());
				}
				if (recurrence == null) {
					error += String.format(MESSAGE_INVALID_RECURRENCE, argsArray[4].trim());
				}
				if (!error.equals(STRING_EMPTY)) {
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
				
				dateStart = addTime(dateStart, startTime);
				dateEnd = addTime(dateEnd, endTime);
			} else {
				error += MESSAGE_INVALID_PARAMS;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
			if(!argsArray[0].equals(STRING_EMPTY) && argsArray.length <= 4){

				title = argsArray[0];
				recurrence = RecurrencePeriod.NONE;
				String tempDate = null;
				
				if(argsArray.length < 4){
					String tempStart = null;
					String tempEnd = null;
					if(argsArray.length == 3 || argsArray.length == 1){
						tempDate ="";
					} else{
						tempDate = argsArray[1];
					}
					if(argsArray.length <= 2){
						tempStart = "0000";
						tempEnd = "2359";
					} else {
						tempStart = argsArray[1];
						tempEnd = argsArray[2];
					}
					dateStart = flexiParse(tempDate + " " + tempStart);
					dateEnd = flexiParse(tempDate + " " + tempEnd);
				} else if(argsArray.length == 4){
					int dateIndex, startIndex, endIndex;
					if(args.indexOf("from") > args.indexOf("on")){
						dateIndex = 1;
						startIndex = 2;
						endIndex = 3;
					} else {
						dateIndex = 3;
						startIndex = 1;
						endIndex = 2;
					}
					dateStart = getDate(argsArray[dateIndex]);					
					if(dateStart == null){
						dateStart = flexiParse(argsArray[dateIndex] + " " + argsArray[startIndex]);
						dateEnd = flexiParse(argsArray[dateIndex] + " " + argsArray[endIndex]);
					} else {
						Calendar c = Calendar.getInstance();
						c.setTime(dateStart);
						
						tempDate = c.get(Calendar.MONTH) + "-" 
										+ c.get(Calendar.DAY_OF_MONTH) + "-" 
										+ c.get(Calendar.YEAR);

						dateStart = flexiParse(tempDate + " " + argsArray[startIndex]);
						dateEnd = flexiParse(tempDate + " " + argsArray[endIndex]);
					}
				}
					Calendar cal = dateToCal(dateStart);
					this.startTime = cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
					cal = dateToCal(dateEnd);
					this.endTime = cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
			} else {
				error += MESSAGE_INVALID_FLEXI;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		}
	}
	
	public boolean validNumArgs(){
		if(this.count != 5){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String execute() {
		task = new Task();
		task.setType("event");
		task.setTitle(title);
		task.setRecurrence(recurrence);
		task.setDueDate(dateStart);
		task.setStartTime(startTime);
		task.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_EVENT_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_EVENT_CLASH;
			}
			Magical.getStorage().create(Storage.EVENTS_INDEX, task);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_EVENT_ERROR;
		} finally {
			GUIModel.setTaskList(Magical.storage.getList(Storage.TASKS_INDEX));
			GUIModel.setDoneList(Magical.storage.getList(Storage.TASKS_DONE_INDEX));
		}
	}

	private boolean isClashing() {
		ArrayList<Task> tasks = Magical.storage.getList(Storage.EVENTS_INDEX);
		for (Task t : tasks) {
			if (t.getDueDate().equals(task.getDueDate())) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		//EventCommand e = new EventCommand("test on 12-07-1993 from 12pm to 3pm");
		//EventCommand e = new EventCommand("test from 12pm to 3pm on 12-07-1993");
		//EventCommand e = new EventCommand("test on tomorrow");
		//EventCommand e = new EventCommand("test from 12pm to 3pm");
		//EventCommand e = new EventCommand("test");
	}

}
