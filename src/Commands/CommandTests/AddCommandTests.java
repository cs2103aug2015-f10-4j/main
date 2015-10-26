package Commands.CommandTests;

import static org.junit.Assert.*;

import org.junit.Test;

import Commands.AddCommand;
import Commands.Command;

public class AddCommandTests {

	// Arguments format: type/title/description/due date/start time/end time/recurrence
	@Test
	public void testNormalInputs() throws Exception {
		Command event = new AddCommand("event/testEvent/testing/9-10-2015/0001/2359/daily");
		Command task = new AddCommand("task/testTask/testing/9-10-2015//2359/weekly");
		Command floatTask = new AddCommand("task/testFloat/testing////monthly");
		Command noRecurrence = new AddCommand("event/testEvent/testing/9-10-2015/0001/2359/");
	}
	
	@Test
	public void testWrongNumArgs(){
		final String MESSAGE_INVALID = "\n----- Invalid arguments ---- \n" 
								+ "Number of Arguments\nUse Format: "
				 				+"\nadd type/title/description/due date"
				 				+ "/start time/end time/recurrence";
		try {
			Command sixArgs = new AddCommand("event/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_INVALID);
		}
		
		try {
			Command noArgs = new AddCommand("");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), "\n----- Invalid arguments ---- \n" + "Use format: add <title> by <date> at <time>");
		}
	}
	
	@Test
	public void testWrongType() {
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "Type: %s" 
									+ " (type should be event or task)\n";
		try {
			Command badType = new AddCommand("foobar/testEvent/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), String.format(MESSAGE_ARG, "foobar"));
		}
		
		try {
			Command noType = new AddCommand("/testEvent/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), String.format(MESSAGE_ARG, ""));
		}
	} 
	
	@Test
	public void testWrongTitle(){
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" + "No Title\n"; 
		try {
			Command noTitle = new AddCommand("event//testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ARG);
		}
	}
	
	@Test
	public void testWrongDate() throws Exception{
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "Due date: %s"
									+ " (Date should be dd-MM-yyyy)\n";
		try {
			Command impossibleDate = new AddCommand("event/test/testing/139-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "139-10-2015"), e.getMessage());
		}
		try {
			Command lettersInDate = new AddCommand("event/test/testing/9-10-2015a/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "9-10-2015a"), e.getMessage());
		}
		try {
			Command noDate = new AddCommand("event/test/testing//0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, ""), e.getMessage());
		}
	}
	
	@Test
	public void testWrongStart() {
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "Start time: %s"
									+ " (Time should be in 24hrs format)\n";
		try {
			Command wrongStartLength = new AddCommand("event/test/testing/9-10-2015/000/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "000"), e.getMessage());
		}
		try {
			Command negativeStart = new AddCommand("event/test/testing/9-10-2015/-999/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "-999"), e.getMessage());
		}
		try {
			Command startExceed24h = new AddCommand("event/test/testing/9-10-2015/9999/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "9999"), e.getMessage());
		}
		try {
			Command noStart = new AddCommand("event/test/testing/9-10-2015//2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, ""), e.getMessage());
		}
		try {
			Command taskWithStart = new AddCommand("task/test/testing/9-10-2015/0000/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals("\n----- Invalid arguments ---- \n" 
					+ "Start time: 0000"
					+ " (Task should not have start time)\n", e.getMessage());
		}
	}
	
	@Test
	public void testWrongEnd() throws Exception {
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "End time: %s"
									+ " (Time should be in 24hrs format)\n";
		try {
			Command wrongEndLength = new AddCommand("event/test/testing/9-10-2015/0000/235/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "235"), e.getMessage());
		}
		try {
			Command negativeEnd = new AddCommand("event/test/testing/9-10-2015/0000/-235/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "-235"), e.getMessage());
		}
		try {
			Command endExceed24h = new AddCommand("event/test/testing/9-10-2015/0000/9999/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "9999"), e.getMessage());
		}
		try {
			Command noEnd = new AddCommand("event/test/testing/9-10-2015/0000//daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, ""), e.getMessage());
		}
	}
	
	@Test
	public void testRecurrence(){
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "Recurrence: %s"
									+ "\n(Recurrence should be daily, weekly, monthly, yearly or left empty\n";
		try {
			Command invalidRecurrence = new AddCommand("event/test/testing/9-10-2015/0000/2359/fortnightly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "fortnightly"), e.getMessage());
		}
	}
}
