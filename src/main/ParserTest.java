package main;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	Parser p = new Parser();

	public void testAddSuccess() {
		try {
			Command c = p.parse("Add  task/test/this is a test/24-9-2015/1000/1700/daily");
		} catch (Exception e) {
			fail("Parse error as command is valid.");
		}	
	}

	public void testInvalidCommand() {
		try {
			Command c = p.parse("foobar task/test/this is a test/24092015/1000/1700/daily");
			fail("Invalid command but parse succeeded.");
		} catch (Exception e) {
			assertEquals(Parser.MESSAGE_INVALID_COMMAND, e.getMessage());
		}
	}

	public void testNull() {
		try {
			Command c = p.parse(null);
			fail("Null command but parser succeeded.");
		} catch (Exception e) {
			assertEquals("Must enter a command when calling execute", e.getMessage());
		}	
	}

	public void testAddInvalidType() {
		try {
			Command c = p.parse("Add foobar/test/this is a test/24-9-2015/1000/1700/daily");
			fail("Invalid type but parser succeeded.");
		} catch (Exception e) {
//			assertEquals("", e.getMessage());
		}
	}

	public void testAddInvalidTitle() {
		try {
			Command c = p.parse("Add task//this is a test/24-9-2015/1000/1700/daily");
			fail("Invalid title but parser succeeded.");
		} catch (Exception e) {
			
		}
	}
	
	public void testAddInvalidDate() {
		try {
			Command c = p.parse("Add task/test/this is a test/24-9-2015a/1000/1700/daily");
			fail("Invalid date but parser succeeded.");
		} catch (Exception e) {
			
		}
	}

	public void testAddValidDate() {
		try {
			Command c = p.parse("Add task/test/this is a test/ /1000/1700/daily");
		} catch (Exception e) {
			fail("Valid command but parser failed.");
		}
	}

	public void testAddInvalidStartTime() {
		try {
			Command c = p.parse("Add task/test/this is a test/24-9-2015/1260/1700/daily");
			fail("Invalid start time but parser succeeded.");
		} catch (Exception e) {
			
		}
	}

	public void testAddInvalidEndTime() {
		try {
			Command c = p.parse("Add task/test/this is a test/24-9-2015/1000/2400/daily");
			fail("Invalid end time but parser succeeeded.");
		} catch (Exception e){
			
		}
	}

	public void testAddInvalidRecurrence() {
		try {
			Command c = p.parse("Add task/test/this is a test/24-9-2015/1000/1700/foobar");
			fail("Invalid recurrence period but parser succeeded.");
		} catch (Exception e) {
			
		}
	}

	public void testSearchType() {
		try {
			Command c = p.parse("search /task");
		} catch (Exception e) {
			fail("Valid search type but parser failed.");
		}
	}
	
	public void testInvalidSearchType() {
		try {
			Command c = p.parse("search /foobar");
			fail("Invalid search type but parser succeeded.");
		} catch (Exception e) {
			
		}
	}
}
