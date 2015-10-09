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
		final String MESSAGE_INVALID = "\n----- Invalid arguments ---- \n" + "Use Format: "
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
			assertEquals(e.getMessage(), MESSAGE_INVALID);
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
			System.out.println(e.getMessage());
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
	public void testWrongDate(){
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" 
									+ "Due date: %s"
									+ " (Date should be dd-MM-yyyy)\n";
		/*
		try {
			Command pastDate = new AddCommand("event/test/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		*/
		try {
			Command impossibleDate = new AddCommand("event/test/testing/139-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			System.out.println(e.getMessage());
			assertEquals(String.format(MESSAGE_ARG, "139-10-2015"), e.getMessage());
		}
		try {
			Command lettersInDate = new AddCommand("event/test/testing/9-10-2015a/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ARG, "9-10-2015a"), e.getMessage());
		}
	}
}
