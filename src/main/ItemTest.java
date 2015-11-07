package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item task1 = new Item();
	Item event1 = new Item();
	Item task2 = new Item();
	Item event2 = new Item();
	Item event3 = new Item();
	private Set<String> tags = new HashSet<String>();
	
	@Before
	public void setUp() {
		task1.setType("task");
		task1.setTitle("help mum buy groceries");
		task1.setEndDate(createCustomDate(1992, 3, 17, 15, 9, 17));
		task1.setStartTime(900);
		task1.setEndTime(2200);
		task1.setRecurrence(RecurrencePeriod.WEEKLY);
		task1.setPriority(1);
		
		task2.setType("task");
		task2.setTitle("study for midterms");
		task2.setEndDate(createCustomDate(1993, 10, 12, 3, 8, 16));
		task2.setStartTime(800);
		task2.setEndTime(2000);
		task2.setRecurrence(RecurrencePeriod.DAILY);
		task2.setPriority(2);
		
		event1.setType("event");
		event1.setTitle("my birthday");
		// event1.setEndDate(createCustomDate(1988, 2, 16, 8, 18, 58));
		event1.setStartTime(700);
		event1.setEndTime(1800);
		tags.clear();
		tags.add(null);
		event1.setTags(tags);
		event1.setRecurrence(RecurrencePeriod.YEARLY);
		event1.setPriority(3);
		
		event2.setType("event");
		event2.setTitle("eat dinner at utown");
		event2.setEndDate(createCustomDate(1988, 8, 18, 3, 19, 16));
		event2.setStartTime(500);
		event2.setEndTime(1400);
		event2.setRecurrence(RecurrencePeriod.WEEKLY);
		event2.setPriority(4);
		
		event3.setType("event"); // exactly the same as event1
		event3.setTitle("my birthday");
		// event3.setEndDate(createCustomDate(1988, 2, 16, 8, 18, 58));
		event3.setStartTime(700);
		event3.setEndTime(1800);
		tags.clear();
		tags.add("");
		event3.setTags(tags);
		event3.setRecurrence(RecurrencePeriod.YEARLY);
		event3.setPriority(3);
	}
	
	/******************* HELPER METHODS *******************/
	
	// creates localArray by adding default tasks to test against
	private CustomDate createCustomDate(int year, int month, int day, int hour, int min, int sec) {
		Calendar date = Calendar.getInstance();
		date.clear();
		
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DATE, day);
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, min);
		date.set(Calendar.SECOND, sec);
		
		CustomDate dateToReturn = new CustomDate(date.getTime());

		System.out.println("date: " + dateToReturn.getDateString());
		System.out.println("time: " + dateToReturn.getTime());
		System.out.println();
		return dateToReturn;
	}
	
	@Test
	public void testEquals() {
		// to test if 2 task objects are equal
		System.out.println("event1 hashcode: " + event1.hashCode());
		System.out.println("event2 hashcode: " + event2.hashCode());
		System.out.println("event3 hashcode: " + event3.hashCode());
		assertEquals(event1, event3);
	}
	
	
	@Test
	public void testNotEquals() {
		// to test if 2 task objects not equal
		assertNotEquals(event1, event2);
	}
	
	

}
