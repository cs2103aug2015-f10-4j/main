package Commands.CommandTests;

import static org.junit.Assert.*;

import org.junit.Test;

import Commands.AddCommand;
import Commands.BlockCommand;
import Commands.Command;

public class BlockCommandTests {

	@Test
	public void testNormalInputs() throws Exception {
		Command event = new BlockCommand("event/testEvent/testing/9-10-2015");
		Command task = new BlockCommand("task/testTask/testing/9-10-2015");
		Command startEnd = new BlockCommand("task/testFloat/testing/9-10-2015//11-10-2015");
		Command threeDates = new BlockCommand("event/testEvent/testing/9-10-2015/10-10-2015/11-10-2015");
	}
	
	@Test
	public void testWrongNumArgs(){
		final String MESSAGE_INVALID = "\n----- Invalid arguments ---- \n" 
				+ "Number of Arguments\n"
				+ "Use Format: \n1. block type/title/description/date 1/date 2/.../date n"
				+ "\n2. block type/title/description/start date//end date";
		try {
			Command lessArgs = new BlockCommand("event/testEvent/testing");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID, e.getMessage());
		}		
		try {
			Command noArgs = new BlockCommand("");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_INVALID);
		}
	}
	
	@Test
	public void testWrongType() {
		final String MESSAGE_INVALID = "\n----- Invalid arguments ---- \n" 
									+ "Type: %s" 
									+ " (type should be event or task)\n";
		try {
			Command badType = new BlockCommand("foobar/testEvent/testing/9-10-2015/29-10-2015");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), String.format(MESSAGE_INVALID, "foobar"));
		}
		
		try {
			Command noType = new BlockCommand("/testEvent/testing/9-10-2015/29-10-2015");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), String.format(MESSAGE_INVALID, ""));
		}
	} 
	
	@Test
	public void testWrongStart() {
		final String MESSAGE_INVALID = "\n----- Invalid arguments ---- \n" 
				+ "Date: %s (Date should be dd-MM-yyyy)\n";

		try {
			Command impossibleDate = new BlockCommand("event/testEvent/testing/229-10-2015/9-10-2015");
			fail();
		} catch (Exception e){
			//System.out.println(e.getMessage());
			assertEquals(e.getMessage(), String.format(MESSAGE_INVALID, "229-10-2015"));
		}
		try {
			Command lettersInDate = new BlockCommand("event/test/testing/9-10-2015a/29-10-2015");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_INVALID, "9-10-2015a"), e.getMessage());
		}
		try {
			Command noDate = new AddCommand("event/test/testing//29-10-2015");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_INVALID, ""), e.getMessage());
		}
	}

}
