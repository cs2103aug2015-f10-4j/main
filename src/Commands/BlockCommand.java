package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BlockCommand extends Command{

	private String type;
	private String title;
	private ArrayList<Date> dates;
	private String error = STRING_EMPTY;
	private Date startD;
	private Date endD;
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
			isRange = args.contains("from")||args.contains("to")||args.contains("till");
			if(isRange){
				String[] tempArray = args.split(" ", 3)[2].split("(?<=\\s)to(?=\\s)"
											+ "|(?<=\\s)from(?=\\s)"
											+ "|(?<=\\s)till(?=\\s)", -1);
				this.argsArray = new String[2+tempArray.length];
				argsArray[0] = args.split(" ", 2)[0];
				argsArray[1] = args.split(" ", 3)[1];
				System.arraycopy(tempArray, 0, argsArray, 2, tempArray.length);
			} else {
				this.argsArray = args.split(" ");
			}
			
			for(int i = 0; i < argsArray.length; i++){
				argsArray[i] = argsArray[i].trim().replaceAll("(?<![\\\\])\\\\", STRING_EMPTY);
			}
		}
		
		this.count = argsArray.length;
		this.dates = new ArrayList<Date>();
		
		if(!isFlexi){
			if(validNumArgs()){
			
				this.type = getType(argsArray[0].trim());
				this.title = getTitle(argsArray[1].trim());
				
				if (type == null) {
					error += String.format(MESSAGE_INVALID_TYPE, argsArray[0].trim());
				}
				if (title == null) {
					error += MESSAGE_INVALID_TITLE;
				}

				if(isRange){
					if(argsArray.length == 5){
						this.startD = getDate(argsArray[2].trim());
						this.endD = getDate(argsArray[4].trim());
	
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
			if(validNumArgs()){
				if(isRange){
					this.startD = getDate(argsArray[2].trim());
					this.endD = getDate(argsArray[3].trim());
					if(startD == null){
						startD = flexiParse(argsArray[2].trim());
					}
					if(endD == null){
						endD = flexiParse(argsArray[3].trim());
					}
					if(validDateRange()){
						this.dates = getDateRange(startD, endD);
					} else {
						error += "End date is earlier than start date";
						throw new Exception(MESSAGE_HEADER_INVALID + error);
					}
				} else {
					for(int i = 2; i < argsArray.length; i++){
						String currDate = argsArray[i].trim();
						Date dateToAdd = getDate(currDate);
						if(dateToAdd == null){
							dateToAdd = flexiParse(currDate);
							dates.add(dateToAdd);
						} else {
							dates.add(dateToAdd);
						}
					}
				}
			} else {
				error += MESSAGE_INVALID_FLEXI;
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
		}
	}

	private ArrayList<Date> getDateRange(Date startD, Date endD) {
		ArrayList<Date> range = new ArrayList<Date>();
		try {
			range.add(startD);
			Calendar c = new GregorianCalendar();
			c.setTime(startD);
			c.add(Calendar.DATE, 1);

			while (c.getTime().before(endD)) {
				Date result = c.getTime();
				range.add(result);
				c.add(Calendar.DATE, 1);
			}
			range.add(endD);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return range;
	}
	
	
	
	public boolean validDateRange() {
		return endD.after(startD);
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
		BlockCommand b3 = new BlockCommand("task test 01-12-12 to 12-12-12");
	}
}
