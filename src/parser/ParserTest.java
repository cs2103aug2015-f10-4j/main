package parser;

import command.Command;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	
	/** Error Messages */
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command: %s";
	private static final String MESSAGE_INVALID_INPUT = "Please enter a command";
	
	Parser p = new Parser();

	public void testSuccess() {
		try {
			Command c = p.parse("Add this is a test by 24-9-2015 1000");
		} catch (Exception e) {
			fail("Parse error as command is valid.");
		}	
	}

	public void testInvalidCommand() {
		try {
			Command c = p.parse("foobar foobar foobar");
			fail("Invalid command but parse succeeded.");
		} catch (Exception e) {
			assertEquals(String.format(MESSAGE_INVALID_COMMAND, "foobar"), e.getMessage());
		}
	}

	public void testNoInput() {
		try {
			Command c = p.parse("");
			fail("Empty command but parser succeeded.");
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_INPUT, e.getMessage());
		}	
	}
}
