package parser;

import static org.junit.Assert.assertNotNull;
import java.util.Date;

import command.Command;
import command.DateCommand;
import main.CustomDate;

/**
 * @@author A0129654X
 */
public class DateParser extends ArgsParserAbstract {

	/** Messaging **/
	private static final String MESSAGE_INVALID_FORMAT = "Use Format: date <start date> to <end date>";
	private static final String MESSAGE_INVALID_DATETIME_END = "End date/time";
	private static final String MESSAGE_INVALID_DATETIME_START = "Start date/time";
	private static final String MESSAGE_INVALID_DATETIME_RANGE = "End date/time is earlier than Start date/time";
	
	/** Command parameters **/
	private CustomDate dateStart;
	private CustomDate dateEnd;
	
	/**
	 * Constructor for DateParser objects. Checks if arguments are valid and
	 * stores the correct arguments properly. Throws the appropriate exception
	 * if arguments are invalid. Contains methods to display the date within the
	 * time range.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public DateParser(String args) throws Exception {
		super(args);
		this.argsArray = splitArgs("\\sto\\s", -1);
		this.count = argsArray.size();

		if (validNumArgs()) {
			setProperParams();
			checkDateTime(dateStart, 0);
			checkDateTime(dateEnd, 1);
			checkDateRange();
			errorInvalidArgs();
		} else {
			errorInvalidFormat(MESSAGE_INVALID_FORMAT);
		}
	}
	
	/**
	 * Adds error message if invalid date and time specified, according to if
	 * the date is the start or end date.
	 */
	private void checkDateTime(CustomDate date, int type) {
		assert (type == 0 || type == 1);
		if (date == null) {
			if (type == 0) {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_START);
			} else {
				invalidArgs.add(MESSAGE_INVALID_DATETIME_END);
			}
		}
	}
	
	/**
	 * Adds error message if end date is before start date
	 */
	void checkDateRange() {
		if (dateStart != null && dateEnd != null && !validDateRange()) {
			invalidArgs.add(MESSAGE_INVALID_DATETIME_RANGE);
		}
	}
	
	/**
	 * Check if the end date given is after the start date
	 * 
	 * @return
	 */
	public boolean validDateRange() {
		return dateEnd.compareTo(dateStart) > 0;
	}

	@Override
	void setProperParams() {
		String start = argsArray.get(0).trim();
		String end = count == 2 ? argsArray.get(1).trim() : STRING_EMPTY;

		dateStart = start.equals(STRING_EMPTY) ? new CustomDate(new Date(0))
				: getDateTimeZero(start);
		dateEnd = end.equals(STRING_EMPTY) ? new CustomDate(new Date(
				Long.MAX_VALUE)) : getDate(end);
	}

	@Override
	boolean validNumArgs() {
		if (this.count > 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	Command getCommand() {
		Command date = new DateCommand(this.dateStart, this.dateEnd);
		assertNotNull(date);
		return date;
	}
}
