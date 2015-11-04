package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import main.CustomDate;

public class BlockCommand extends Command{

	private String type;
	private String title;
	private ArrayList<CustomDate> dates;
	private String error = STRING_EMPTY;
	private CustomDate startD;
	private CustomDate endD;
	private boolean isRange;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+"Use Format: \n"
			+ "1. block type/title/date 1/date 2/...\n"
			+ "2. block type/title/start date//end date";
	private static final String MESSAGE_INVALID_FLEXI = "Use format:\n"
			+ "1. block <type> <title> <date 1> <date 2> ...\n"
			+ "2. block <type> <title> from <start date> to <end date>";
	private static final String MESSAGE_INVALID_TYPE = "Type: %s (type should be event or task)\n";
	private static final String MESSAGE_INVALID_TITLE = "No Title" + "\n";
	private static final String MESSAGE_INVALID_DATE = "Date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_SDATE = "Start date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_EDATE = "End date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_INVALID_USAGE = "Invalid usage of //";
	
	public BlockCommand(String args) throws Exception {
		super(args);
		
		/*
		isFlexi = !args.contains("/") || !args.replace("\\/", STRING_EMPTY).contains("/");
		if(!isFlexi){
			this.argsArray = args.split("(?<![\\\\])/", -1);
			for (int i = 0; i < argsArray.length; i++){
				String param = argsArray[i];

				param = param.replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
				argsArray[i] = param;

			}
			isRange = args.contains("//");
			
		} else {
		*/
		isRange = args.contains("from")||args.contains("to")||args.contains("till");
		System.out.println(isRange);
		if(isRange){
			String[] tempArray = args.split(" ", 3)[2].split("\\sto\\s"
										+ "|\\sfrom\\s"
										+ "|\\still\\s", -1);
			this.argsArray = new ArrayList<String>(Arrays.asList(tempArray));
			argsArray.add(0, args.split(" ", 2)[0]); 
			argsArray.add(1,  args.split(" ", 3)[1]);
		} else {
			this.argsArray =  new ArrayList<String>(Arrays.asList(args.split(" ")));
		}
		System.out.println(argsArray);
		
		for(int i = 0; i < argsArray.size(); i++){
			argsArray.set(i, argsArray.get(i).trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY));
		}
		
		this.count = argsArray.size();
		this.dates = new ArrayList<CustomDate>();
		
		/*
		if(!isFlexi){
			if(validNumArgs()){
			
				this.type = getType(argsArray.get(0).trim());
				this.title = getTitle(argsArray.get(1).trim());
				
				if (type == null) {
					error += String.format(MESSAGE_INVALID_TYPE, argsArray.get(0).trim());
				}
				if (title == null) {
					error += MESSAGE_INVALID_TITLE;
				}

				if(isRange){
					if(count == 5){
						this.startD = getDate(argsArray.get(2).trim());
						this.endD = getDate(argsArray.get(4).trim());
	
						if (startD == null) {
							error +=  String.format(MESSAGE_INVALID_SDATE, argsArray[2].trim());
						}
						if (endD == null) {
							error +=  String.format(MESSAGE_INVALID_EDATE, argsArray[4].trim());
						}
						if (!error.equals(STRING_EMPTY)) {
							throw new Exception(MESSAGE_HEADER_INVALID + error);
						} else if(validDateRange()){
							this.dates = getDateRange(startD, endD);
						} else {
							error += "End date is earlier than start date";
							throw new Exception(MESSAGE_HEADER_INVALID + error);
						}
					} else {
						error += MESSAGE_INVALID_USAGE;
						throw new Exception(MESSAGE_HEADER_INVALID + error);
					}
				} else {
					for(int i = 2; i < argsArray.length; i++){
						String currDate = argsArray[i].trim();
						Date dateToAdd = getDate(currDate);
						if(dateToAdd == null){
							error += String.format(MESSAGE_INVALID_DATE, currDate);
						} else {
							dates.add(dateToAdd);
						}
					}
					if(!error.equals(STRING_EMPTY)){
						throw new Exception(MESSAGE_HEADER_INVALID + error);
					}
				}
			} else {
				error += MESSAGE_INVALID_PARAMS;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		} else {
		//*/
		if(validNumArgs()){
			if(isRange){
				this.startD = getDate(argsArray.get(2).trim());
				this.endD = getDate(argsArray.get(3).trim());
				
				if(startD == null){
					error += String.format(MESSAGE_INVALID_SDATE, argsArray.get(2).trim());
				}
				if(endD == null){
					error += String.format(MESSAGE_INVALID_EDATE, argsArray.get(3).trim());
				}
				if(startD != null && endD != null){
					if(validDateRange()){
						this.dates = getDateRange(startD, endD);
					} else {
						error += "End date is earlier than start date";
					}
				}
				if (!error.equals(STRING_EMPTY)) {
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
			} else {
				for(int i = 2; i < count; i++){
					String currDate = argsArray.get(i).trim();
					CustomDate dateToAdd = getDate(currDate);
					dates.add(dateToAdd);
				}
			}
			System.out.println(dates);
		} else {
			error += MESSAGE_INVALID_FLEXI;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
		
	}

	private ArrayList<CustomDate> getDateRange(CustomDate start, CustomDate end) {
		ArrayList<CustomDate> range = new ArrayList<CustomDate>();
		try {
			range.add(start);
			start.add("date", 1);
			
			while (start.compareTo(end) == 1) {
				range.add(start);
				start.add("date", 1);
			}
			range.add(end);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return range;
	}
	
	
	
	public boolean validDateRange() {
		return endD.compareTo(startD) != -1;
	}
	
	public boolean validNumArgs(){
		if(this.count < 3){
			return false;
		} else {
			return true;
		}
	}
	
	public String execute(){
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		//BlockCommand b1 = new BlockCommand("");
		BlockCommand b2 = new BlockCommand("event test monday tuesday wednesday");
		BlockCommand b3 = new BlockCommand("task test 01-1a2-12 to 12-12-12a");
	}
}
