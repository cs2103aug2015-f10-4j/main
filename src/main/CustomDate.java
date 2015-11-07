package main;

import java.util.Calendar;
import java.util.Date;

public class CustomDate implements Comparable {

	private static final String DATE_FORMAT = "%04d on %d %s %s, %s";

	private static final String[] dayArray = { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };
	private static final String[] monthArray = { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private Date date;

	public CustomDate(Date date) {
		this.date = date;
	}

	public CustomDate() {
		this.date = new Date();
	}

	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day = dayArray[cal.get(Calendar.DAY_OF_WEEK) - 1];
		String month = monthArray[getMonth() - 1];
		return String.format(DATE_FORMAT, getTime(), getDay(), month,
				getYear(), day);
	}

	public String getDateString() {
		String dateString = getDay() + "/" + getMonth() + "/" + getYear();
		return dateString;
	}

	public void setDateString(String dateString) {
	}

	public Date getDate() {
		return this.date;
	}

	public int getYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public void setYear(int year) {
	}

	public int getMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public void setMonth(int month) {
	}

	public int getDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public void setDay(int day) {
	}

	public int getTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
	}

	public void setTime(int time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, time / 100);
		cal.set(Calendar.MINUTE, time % 100);
		cal.set(Calendar.SECOND, 0);
		this.date = cal.getTime();
	}

	public int compareTo(CustomDate that) {
		long thisYear = this.getYear();
		thisYear *= 10000;
		long thisDate = thisYear + this.getMonth() * 100 + this.getDay();
		long thatYear = that.getYear();
		thatYear *= 10000;
		long thatDate = thatYear + that.getMonth() * 100 + that.getDay();

		if (thisDate > thatDate) {
			return 1;
		} else if (thisDate < thatDate) {
			return -1;
		} else {
			return 0;
		}
	}

	public void set(String field, int val) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (field) {
		case "day":
			cal.set(Calendar.DAY_OF_MONTH, val);
			break;
		case "month":
			cal.set(Calendar.MONTH, val);
			break;
		case "year":
			cal.set(Calendar.YEAR, val);
			break;
		default:
			break;
		}
		this.date = cal.getTime();
	}
}
