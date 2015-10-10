package Commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BlockCommand extends Command{

	private String[] argsArray;
	private String type;
	private String title;
	private String desc;
	private ArrayList<Date> dates;
	private String error = "";
	private String startD;
	private String endD;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private static final String MESSAGE_ARGUMENT_PARAMS = "\n1. block type/title/description/date 1/date 2/.../date n"
			+ "\n2. block type/title/description/start date//end date";
	
	public BlockCommand(String args) throws Exception {
		super(args);
		dates = new ArrayList<Date>();
		
		if(validNumArgs()){

			System.out.println(Arrays.toString(argsArray));
			
			this.type = argsArray[0].trim();
			this.title = argsArray[1].trim();
			this.desc = argsArray[2].trim();
			
			if(!validType()){
				error += "Type: " + type + "\n";
			}
			if(!validTitle()){
				error += "No Title" + "\n";
			}
			if (!error.equals("")) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}
			if(args.contains("//")){
				if(argsArray.length == 6){
					this.startD = argsArray[3].trim();
					this.endD = argsArray[5].trim();

					if (!validStartDate()) {
						error += "Start date: " + startD + "\n";
					}
					if (!validEndDate()) {
						error += "End date: " + endD + "\n";
					}
					if (!error.equals("")) {
						throw new Exception(MESSAGE_HEADER_INVALID + error);
					} else {

						// creates an array list of Dates within start and end
						// date
						try {
							Date first = dateFormat.parse(startD);
							Date last = dateFormat.parse(endD);
							dates.add(first);
							Calendar c = new GregorianCalendar();
							c.setTime(first);
							c.add(Calendar.DATE, 1);

							while (c.getTime().before(last)) {
								Date result = c.getTime();
								dates.add(result);
								c.add(Calendar.DATE, 1);
							}
							dates.add(last);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					error += "Invalid usage of //";
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
			} else {
				for(int i = 3; i < argsArray.length; i++){
					String currDate = argsArray[i].trim();
					System.out.println(currDate);
					boolean isValidDate = checkDate(currDate);
					if(!isValidDate){
						error += "Date: " + currDate + "\n";
					} else {
						Date toAdd = dateFormat.parse(currDate);
						dates.add(toAdd);
					}
				}
				if(!error.equals("")){
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
			}
			System.out.println(dates);
		} else {
			error += "Number of Arguments\n";
			throw new Exception(MESSAGE_HEADER_INVALID + error + "Use Format: " + MESSAGE_ARGUMENT_PARAMS);
		}
	}
	
	private boolean checkCount(){
		if(this.count < 3){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validNumArgs(){
		return checkCount();
	}
	
	public boolean validStartDate(){
		return checkDate(this.startD);
	}
	
	public boolean validEndDate(){
		return checkDate(this.endD);
	}
	
	public boolean validTitle(){
		return checkTitle(this.title);
	}
	
	public boolean validType(){
		return checkType(this.type);
	}
	
	@Override
	public String execute(){
		return null;
	}
}
