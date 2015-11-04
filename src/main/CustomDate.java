package main;

import java.util.Calendar;
import java.util.Date;

public class CustomDate extends Date {

	private static final String DATE_FORMAT = "%i %s %s, %s";

	private static String[] dayArray = {"Sun", "Mon", "Tues", "Wed",
			"Thur", "Fri", "Sat"};
	private static String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	public CustomDate(String args) {
		super(args);
	}

	public String getYearString() {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		String year = Integer.toString(c.get(Calendar.YEAR));
		return "'" + year.substring(2);
	}

	@Override
	public String toString() {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		String day = dayArray[c.get(Calendar.DAY_OF_WEEK)];
		String month = monthArray[c.get(Calendar.MONTH)];
		return String.format(DATE_FORMAT, c.get(Calendar.DAY_OF_MONTH), month, getYearString(), day);
	}
}
