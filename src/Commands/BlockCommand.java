package Commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BlockCommand extends Command{

	private String type;
	private String title;
	private String desc;
	private ArrayList<Date> dates;
	private String error = "";
	private Date startD;
	private Date endD;
	
	private static final String MESSAGE_ERROR_PARAMS = "Number of Arguments\n"
			+"Use Format: \n1. block type/title/description/date 1/date 2/.../date n"
			+ "\n2. block type/title/description/start date//end date";
	private static final String MESSAGE_ERROR_TYPE = "Type: %s (type should be event or task)\n";
	private static final String MESSAGE_ERROR_TITLE = "No Title" + "\n";
	private static final String MESSAGE_ERROR_DATE = "Date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_ERROR_SDATE = "Start date: %s (Date should be dd-MM-yyyy)\n";
	private static final String MESSAGE_ERROR_EDATE = "End date: %s (Date should be dd-MM-yyyy)\n";
	
	public BlockCommand(String args) throws Exception {
		super(args);
		dates = new ArrayList<Date>();
		
		if(validNumArgs()){
			
			this.type = getType(argsArray[0].trim());
			this.title = getTitle(argsArray[1].trim());
			this.desc = argsArray[2].trim();
			
			if (type == null) {
				error += String.format(MESSAGE_ERROR_TYPE, argsArray[0].trim());
			}
			if (title == null) {
				error += MESSAGE_ERROR_TITLE;
			}
//			if (!error.equals("")) {
//				throw new Exception(MESSAGE_HEADER_INVALID + error);
//			}
			
			if(args.contains("//")){
				if(argsArray.length == 6){
					this.startD = getDate(argsArray[3].trim());
					this.endD = getDate(argsArray[5].trim());

					if (startD == null) {
						error +=  String.format(MESSAGE_ERROR_SDATE, argsArray[3].trim());
					}
					if (endD == null) {
						error +=  String.format(MESSAGE_ERROR_EDATE, argsArray[3].trim());
					}
					if (!error.equals("")) {
						throw new Exception(MESSAGE_HEADER_INVALID + error);
					} else {

						// creates an array list of Dates within start and end
						// date
						try {
							dates.add(startD);
							Calendar c = new GregorianCalendar();
							c.setTime(startD);
							c.add(Calendar.DATE, 1);

							while (c.getTime().before(endD)) {
								Date result = c.getTime();
								dates.add(result);
								c.add(Calendar.DATE, 1);
							}
							dates.add(endD);

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
					Date dateToAdd = getDate(currDate);
					if(dateToAdd == null){
						error += String.format(MESSAGE_ERROR_DATE, currDate);
					} else {
						dates.add(dateToAdd);
					}
				}
				if(!error.equals("")){
					throw new Exception(MESSAGE_HEADER_INVALID + error);
				}
			}
		} else {
			error += MESSAGE_ERROR_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}
	
	public boolean validNumArgs(){
		if(this.count < 4){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String execute(){
		return null;
	}
}
