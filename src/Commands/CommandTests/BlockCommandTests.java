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
	public void testWrongStart() {
		try {
			Command event = new BlockCommand("event/testEvent/testing/9-10-2015/9-10-2015");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
