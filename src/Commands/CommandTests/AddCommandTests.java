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
		final String MESSAGE_ARG = "\n----- Invalid arguments ---- \n" + "Type: ";
		try {
			Command badType = new AddCommand("foobar/testEvent/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ARG + "foobar\n");
		}
		
		try {
			Command noType = new AddCommand("/testEvent/testing/9-10-2015/0001/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ARG + "\n");
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
}
