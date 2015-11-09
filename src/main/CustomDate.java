package main;

import java.util.Calendar;
import java.util.Date;

public class CustomDate implements Comparable<CustomDate> {

	/** Messaging **/
	private static final String DATE_FORMAT = "%s on %d %s %s, %s";
	private static final String[] dayArray = { "Sun", "Mon", "Tue",
			"Wed", "Thu", "Fri", "Sat" };

	private static final String[] monthArray = { "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	/** CustomDate parameters **/
	private Date date;

	/**
	 * Constructor for CustomDate. Uses the current date as the date object for
	 * the CustomDate.
	 */
	public CustomDate() {
		this.date = new Date();
	}

	/**
	 * Constructor for CustomDate. Takes in a date object for use by the
	 * CustomDate.
	 *
	 * @param date
	 */
	public CustomDate(Date date) {
		this.date = date;
	}

	public int compareTo(CustomDate that) {
		if (this.getDate() == null && that.getDate() == null) {
			return 0;
		} else if (this.getDate() == null) {
			return -1;
		} else if (that.getDate() == null) {
			return 1;
		}
		return this.getDate().compareTo(that.getDate());
	}

	/**
	 * Create a calendar for get methods
	 *
	 * @return
	 */
	Calendar createCal() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CustomDate)) {
			return false;
		}
		CustomDate other = (CustomDate) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns date object of the CustomDate
	 *
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Returns the date as a string in dd/MM/yyyy format
	 *
	 * @return
	 */
	public String getDateString() {
		String dateString = getDay() + "/" + getMonth() + "/" + getYear();
		return dateString;
	}

	/**
	 * Gets the day of the date object in CustomDate
	 *
	 * @return
	 */
	public int getDay() {
		Calendar cal = createCal();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * Gets the month of the date object in CustomDate
	 *
	 * @return
	 */
	public int getMonth() {
		Calendar cal = createCal();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * Gets the time of the date object in CustomDate
	 *
	 * @return
	 */
	public int getTime() {
		Calendar cal = createCal();
		return cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
	}

	/**
	 * Gets the year of the date object in CustomDate
	 *
	 * @return
	 */
	public int getYear() {
		Calendar cal = createCal();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	/**
	 * Empty command for storage compatibility purposes
	 *
	 * @param dateString
	 */
	public void setDateString(String dateString) {
	}

	/**
	 * Set day of the customdate using the value
	 *
	 * @param field
	 * @param val
	 */
	public void setDay(int day) {
		Calendar cal = createCal();
		cal.set(Calendar.DAY_OF_MONTH, day);
		this.date = cal.getTime();
	}

	/**
	 * Set month of the customdate using the value
	 *
	 * @param field
	 * @param val
	 */
	public void setMonth(int month) {
		Calendar cal = createCal();
		cal.set(Calendar.MONTH, month - 1);
		this.date = cal.getTime();
	}

	/**
	 * Set a time of the customdate using the value
	 *
	 * @param field
	 * @param val
	 */
	public void setTime(int time) {
		Calendar cal = createCal();
		cal.set(Calendar.HOUR_OF_DAY, time / 100);
		cal.set(Calendar.MINUTE, time % 100);
		cal.set(Calendar.SECOND, 0);
		this.date = cal.getTime();
	}

	/**
	 * Set year of the customdate using the value
	 *
	 * @param field
	 * @param val
	 */
	public void setYear(int year) {
		Calendar cal = createCal();
		cal.set(Calendar.YEAR, year);
		this.date = cal.getTime();
	}

	@Override
	public String toString() {
		Calendar cal = createCal();
		String day = dayArray[cal.get(Calendar.DAY_OF_WEEK) - 1];
		String month = monthArray[getMonth() - 1];
		int time = getTime();
		int hour = time/100 - 12 <= 0 ? time/100 : time/100 - 12;
		String timeString = String.format("%d.%02d", hour, time%100);
		if (time / 100 < 12) {
			timeString += " am";
		} else {
			timeString += " pm";
		}
		return String.format(DATE_FORMAT, timeString, getDay(), month,
				getYear(), day);
	}
}
