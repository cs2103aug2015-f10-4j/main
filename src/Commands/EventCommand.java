package Commands;

import static org.junit.Assert.assertNotNull;

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

public class EventCommand extends Command{

	/** Command parameters **/
	protected String title;
	protected CustomDate dateStart;
	protected CustomDate dateEnd;
	protected int startTime;
	protected int endTime;
	protected RecurrencePeriod recurrence;
	private Task task;

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: \nevent title/event date/start time/end time/recurrence";
	private static final String MESSAGE_INVALID_FLEXI = "Use format: event <title> from <start date> <start time> "
			+ "to <end date> <end time> <recurrence>";
	private static final String MESSAGE_INVALID_TITLE = "No Title" + "\n";
	private static final String MESSAGE_INVALID_DATE_TIME = "%s Date/Time: %s\n(Use dd-MM-yyyy for date and 24hours format for time)";
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

		/*
		isFlexi = !args.contains("/") || !args.replace("\\/", STRING_EMPTY).contains("/");
		if(!isFlexi){
			this.argsArray = new ArrayList<String>(Arrays.asList(args.split("(?<![\\\\])/", -1)));
			for (int i = 0; i < argsArray.size(); i++){
				String param = argsArray.get(i);
				param = param.replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
				argsArray.set(i, param);
			}
		} else {
		*/
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split("(?<=\\s)to(?=\\s)|(?<=\\s)from(?=\\s)", -1)));
		for(int i = 0; i < argsArray.size(); i++){
			argsArray.set(i, argsArray.get(i).trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY));
		}
		//}
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
		this.count = argsArray.size();
		System.out.println(argsArray);

		for(int i = 0; i < count; i++){
			assertNotNull(argsArray.get(i));
		}

		//if(!isFlexi){

		if(validNumArgs()){

			this.title = getTitle(argsArray.get(0).trim());
			this.dateStart = getDate(argsArray.get(1).trim());
			this.dateEnd = getDate(argsArray.get(2).trim());
			this.startTime = dateStart.getTime();
			this.endTime = dateEnd.getTime();
			if(count == 4){
				this.recurrence = getRecurrence(argsArray.get(3).trim());
			} else {
				this.recurrence = getRecurrence(STRING_EMPTY);
			}

			CustomDate today = getDate("today");
			if(dateEnd.getDateString().equals(today.getDateString())){
				dateEnd.set("day", dateStart.getDay());
				dateEnd.set("month", dateStart.getMonth());
				dateEnd.set("year", dateStart.getYear());
			}
			if (title == null) {
				invalidArgs.add("title");
			}
			if (dateStart == null) {
				invalidArgs.add("start time");
			}

			if (dateEnd == null) {
				invalidArgs.add("end time");
			}

			if (dateStart != null && dateEnd != null && !validDateRange()) {
				invalidArgs.add("End date is earlier than start date");
			}
			if (recurrence == null) {
				invalidArgs.add("recurrence");
			}
			if (invalidArgs.size() > 0) {
				throw new IllegalArgumentException(MESSAGE_HEADER_INVALID + String.join(", ", invalidArgs));
			}

			dateStart.setTime(startTime);
			dateEnd.setTime(endTime);
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_FLEXI);
		}
			/*
		} else {
			if(!argsArray.get(0).equals(STRING_EMPTY) && argsArray.size() <= 4){

				title = argsArray.get(0);
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
		//*/
	}

	public boolean validNumArgs(){
		if(this.count != 4 && this.count != 3){
			return false;
		} else {
			return true;
		}
	}

	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) != -1;
	}

	@Override
	public String execute() {
		task = new Task();
		task.setType("event");
		task.setTitle(title);
		task.setRecurrence(recurrence);
		task.setStartDate(dateStart);
		task.setStartTime(startTime);
		task.setEndDate(dateEnd);
		task.setEndTime(endTime);

		try {
			String retMsg = MESSAGE_EVENT_ADDED;
			if (isClashing()) {
				retMsg += MESSAGE_EVENT_CLASH;
			}
			Magical.storage.create(Storage.EVENTS_INDEX, task);
			return retMsg;
		} catch (IOException e) {
			return MESSAGE_EVENT_ERROR;
		} finally {
			GUIModel.setEventList(Magical.storage.getList(Storage.EVENTS_INDEX));
			GUIModel.setEventDoneList(Magical.storage.getList(Storage.EVENTS_DONE_INDEX));
			GUIModel.setCurrentTab("events");
		}
	}

	private boolean isClashing() {
		ArrayList<Task> tasks = Magical.storage.getList(Storage.EVENTS_INDEX);
		for (Task t : tasks) {
			if (t.getEndDate().equals(task.getEndDate())) {
				return true;
			}
		}
		return false;
	}

//	public static void main(String[] args) throws Exception {
//		//EventCommand e = new EventCommand("test on 12-07-1993 from 12pm to 3pm");
//		//EventCommand e = new EventCommand("test from 12pm to 3pm on 12-07-1993");
//		//EventCommand e = new EventCommand("test on tomorrow");
//		EventCommand e = new EventCommand("test from next monday 12pm to next tuesday 3pm");
//		//EventCommand e = new EventCommand("test");
//	}

}
