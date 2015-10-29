package main;

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
		String day = dayArray[getDay()];
		String month = monthArray[getMonth()];
		return String.format(DATE_FORMAT, getDate(), month, getYear()+1900, day);
	}
}
