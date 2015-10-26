package main;

public enum RecurrencePeriod {
	DAILY, WEEKLY, MONTHLY, YEARLY, NONE;
	
	public static RecurrencePeriod toRecurrence(String input) {
		try {
			input = input.toUpperCase();
			return valueOf(input);
		} catch (Exception e) {
			return NONE;
		}
	}
}
