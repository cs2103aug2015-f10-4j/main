package parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@author A0129654X
 */
public class AddParserTest {

	private static final String MESSAGE_INVALID_FORMAT = "Use format: add <title> by <date> <time>";
	private static final String MESSAGE_INVALID_TITLE = "[Title]";
	private static final String MESSAGE_INVALID_DATETIME = "[Date/Time]";
	private static final String MESSAGE_TASK_ADDED = "Task added";
	private static final String MESSAGE_TASK_CLASH = ". Another task exists on the same date.";
	private static final String MESSAGE_TASK_ERROR = "Unable to add task";
	private static final String MESSAGE_HEADER_INVALID = "Invalid arguments: %s";

	@Test
	public void testNormalInputs() throws Exception {
		ArgsParserAbstract task = new AddParser("testTask by 9-10-2015 at 2359");
		ArgsParserAbstract floatTask = new AddParser("testFloat");
		ArgsParserAbstract flexiTask1 = new AddParser(
				"testFlexi by 1st January at 12pm");
		ArgsParserAbstract flexiTask2 = new AddParser(
				"testFlexi by 21-12-15 at 1234");
		ArgsParserAbstract flexiDateOnly = new AddParser(
				"testFlexi by 21-12-15");
		ArgsParserAbstract flexiTimeOnly = new AddParser("testFlexi at 1234");
		ArgsParserAbstract reverseDateTime = new AddParser(
				"testFlexi at 12pm by 1st January");
	}

	@Test
	public void testWrongNumArgs() {
		final String MESSAGE_ERROR = String.format(MESSAGE_HEADER_INVALID,
				MESSAGE_INVALID_DATETIME);
		try {
			AddParser moreArgs = new AddParser(
					"testTask by 9-10-2015 at 0000 weekly");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}

		try {
			AddParser timeMoreArgs = new AddParser(
					"testFlexi by 1st January at 12pm at 2pm");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}

		try {
			AddParser dateMoreArgs = new AddParser(
					"testFlexi by 1st January by 2nd January at 2359");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_INVALID_FORMAT, e.getMessage());
		}
	}

	@Test
	public void testWrongTitle() {
		final String MESSAGE_ERROR = String.format(MESSAGE_HEADER_INVALID,
				MESSAGE_INVALID_TITLE);
		try {
			AddParser noTitle = new AddParser("");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
	}

	@Test
	public void testWrongDate() throws Exception {
		final String MESSAGE_ERROR = String.format(MESSAGE_HEADER_INVALID,
				MESSAGE_INVALID_DATETIME);

		try {
			AddParser impossibleDate = new AddParser(
					"testTask by 99-10-2015 at 2359");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}

		try {
			AddParser lettersInDate = new AddParser(
					"testTask by 9-10-2015a at 2359");
			fail();
		} catch (Exception e) {
			assertEquals(String.format(MESSAGE_ERROR, "9-10-2015a"),
					e.getMessage());
		}
		try {
			AddParser wrongTime = new AddParser("testTask by 25a78");
			fail();
		} catch (Exception e) {
			assertEquals(MESSAGE_ERROR, e.getMessage());
		}
	}
}
