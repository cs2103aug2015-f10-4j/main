package main;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item task1 = new Item();
	Item task2 = new Item();
	Item event1 = new Item();
	Item event2 = new Item();
	Item event3 = new Item();
	private Set<String> tags = new HashSet<String>();
	
	@Before
	public void setUp() {
		task1.setType("task");
		task1.setTitle("help mum buy groceries");
		task1.setEndDate(createCustomDate(1992, 3, 17, 22, 0, 0));
		task1.setEndTime(2200);
		task1.setPriority("medium");
		
		task2.setType("task");
		task2.setTitle("study for midterms");
		task2.setEndDate(createCustomDate(1993, 10, 12, 20, 0, 0));
		task2.setEndTime(2000);
		task2.setPriority("high");
		
		event1.setType("event");
		event1.setTitle("my birthday");
		event1.setStartDate(createCustomDate(1988, 2, 12, 7, 0, 0));
		event1.setEndDate(createCustomDate(1988, 2, 16, 18, 0, 0));
		event1.setStartTime(700);
		event1.setEndTime(1800);
		tags.clear();
		tags.add(null);
		event1.setTags(tags);
		event1.setPriority("high");
		
		event2.setType("event");
		event2.setTitle("eat dinner at utown");
		event2.setStartDate(createCustomDate(1988, 2, 12, 5, 0, 0));
		event2.setEndDate(createCustomDate(1988, 8, 18, 14, 0, 0));
		event2.setStartTime(500);
		event2.setEndTime(1400);
		event2.setPriority("low");
		
		// exactly the same as event1
		event3.setType("event");
		event3.setTitle("my birthday");
		event3.setStartDate(createCustomDate(1988, 2, 12, 7, 0, 0));
		event3.setEndDate(createCustomDate(1988, 2, 16, 18, 0, 0));
		event3.setStartTime(700);
		event3.setEndTime(1800);
		tags.clear();
		tags.add("");
		event3.setTags(tags);
		event3.setPriority("high");
	}
	
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

		return dateToReturn;
	}
	
	@Test
	public void testEquals() {
		// to test if 2 task objects are equal
		assertEquals(event1, event3);
	}
	
	@Test
	public void testNotEquals() {
		// to test if 2 task objects not equal
		assertNotEquals(task1, task2);
	}
}
