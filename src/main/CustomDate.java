package main;

import java.util.Calendar;
import java.util.Date;

public class CustomDate {

	private static final String DATE_FORMAT = "%d %s %s, %s";

	private static String[] dayArray = {"Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday"};
	private static String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	private static Date date;

	private Calendar cal;

	public CustomDate(Date args) {
		this.date = args;
		this.cal = Calendar.getInstance();
		cal.setTime(this.date);
	}

	@Override
	public String toString() {
		String day = dayArray[cal.get(Calendar.DAY_OF_WEEK)];
		String month = monthArray[cal.get(Calendar.MONTH)];
		return String.format(DATE_FORMAT, cal.get(Calendar.DAY_OF_MONTH), month, cal.get(Calendar.YEAR), day);
	}
	
	public String getDate(){
		String dateString = getDay() + "/" + getMonth() + "/" + getYear();
		return dateString;
	}
	
	public int getYear(){
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	public int getMonth(){
		int month = cal.get(Calendar.MONTH);
		return month;
	}
	
	public int getDay(){
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	public int getTime(){
		return cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
	}

	public int compareTo(CustomDate that) {
		long thisYear = this.getYear();
		thisYear *= 10000;
		long thisDate = thisYear + this.getMonth()*100 + this.getDay();
		long thatYear = that.getYear();
		thatYear *= 10000;
		long thatDate = thatYear + that.getMonth()*100 + that.getDay();
		
		if(thisDate > thatDate){
			return 1;
		} else if (thisDate < thatDate){
			return -1;
		} else {
			return 0;
		}
	}
	
	public void add(String field, int amt){
		switch(field){
			case "date":
				cal.add(Calendar.DATE, amt);
				break;
			case "month":
				cal.add(Calendar.MONTH, amt);
				break;
			case "year":
				cal.add(Calendar.YEAR, amt);
				break;
			default:
				break;
		}
	}
}
