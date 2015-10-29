package main;

import java.util.Calendar;
import java.util.Date;

public class CustomDate extends Date {

	private static final String DATE_FORMAT = "%i %s %i, %s";

	private static String[] dayArray = {"Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday"};
	private static String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	public CustomDate(String args) {
		super(args);
	}

	@Override
	public String toString() {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		String day = dayArray[c.get(Calendar.DAY_OF_WEEK)];
		String month = monthArray[c.get(Calendar.MONTH)];
		return String.format(DATE_FORMAT, c.get(Calendar.DAY_OF_MONTH), month, c.get(Calendar.YEAR), day);
	}
}
