package command;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddCommandTest {

	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time>";
	private static final String MESSAGE_INVALID_TITLE = "Title";
	private static final String MESSAGE_INVALID_DATETIME = "[Date/Time]";
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";
	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %s";
	
	@Test
	public void testNormalInputs() throws Exception {
		Command task = new AddCommand("testTask by 9-10-2015 at 2359");
		Command floatTask = new AddCommand("testFloat");
		Command flexiTask1 = new AddCommand("testFlexi by 1st January at 12pm");
		Command flexiTask2 = new AddCommand("testFlexi by 21-12-15 at 1234");
		Command flexiDateOnly = new AddCommand("testFlexi by 21-12-15");
		Command flexiTimeOnly = new AddCommand("testFlexi at 1234");
		Command reverseDateTime = new AddCommand("testFlexi at 12pm by 1st January");
	}
	
	@Test
	public void testWrongNumArgs(){
		final String MESSAGE_ERROR = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_PARAMS;
		
		try {
			AddCommand moreArgs = new AddCommand("testTask/9-10-2015/0000/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}

		try {
			AddCommand flexiMoreArgs = new AddCommand("testFlexi by 1st January at 12pm at 2pm");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR_FLEXI);
		}
		
		try {
			AddCommand lessArgs = new AddCommand("testFlexi/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
		
		/** flexi with less args is okay */
		
		try {
			AddCommand noArgs = new AddCommand("");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR_FLEXI);
		}
	}
	
	@Test
	public void testWrongTitle(){
		final String MESSAGE_ERROR = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_TITLE;
		try {
			AddCommand noTitle = new AddCommand("/9-10-2015/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
		try {
			AddCommand flexiNoTitle = new AddCommand("by 1st January at 12pm");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR_FLEXI);
		}
	}
	
	@Test
	//NATTY PARSER ALLOWS NONSENSE FOR DATE PARSING
	public void testWrongDate() throws Exception{
		final String MESSAGE_ERROR = String.format(MESSAGE_HEADER_INVALID, MESSAGE_INVALID_DATETIME);
		
		try {
			AddCommand impossibleDate = new AddCommand("testTask by 99-10-2015 at 2359");
			fail();
		} catch (Exception e){
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
		
		try {
			AddCommand lettersInDate = new AddCommand("testTask by 9-10-2015a at 2359");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "9-10-2015a"), e.getMessage());
		}
		try {
			AddCommand wrongTime = new AddCommand("testTask by 2578");
			fail();
		} catch (Exception e){
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
	}
	
	@Test
	public void testWrongDate() throws Exception {
		final String MESSAGE_ERROR = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_END;
		
		try {
			AddCommand wrongEndLength = new AddCommand("testTask 9-10-2015 235 daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "235"), e.getMessage());
		}
		try {
			AddCommand negativeEnd = new AddCommand("testTask/9-10-2015/-2345/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "-2345"), e.getMessage());
		}
		try {
			AddCommand endExceed24h = new AddCommand("testTask/9-10-2015/9999/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "9999"), e.getMessage());
		}
		try {
			AddCommand noEnd = new AddCommand("testTask/9-10-2015//daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, ""), e.getMessage());
		}
	}
	
	@Test
	public void testRecurrence(){
		final String MESSAGE_ARG = MESSAGE_HEADER_INVALID + MESSAGE_INVALID_RECURRENCE;
		try {
			AddCommand invalidRecurrence = new AddCommand("testTask/9-10-2015/1200/fortnightly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "fortnightly"), e.getMessage());
		}
	}
}
