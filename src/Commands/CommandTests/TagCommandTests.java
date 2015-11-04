package Commands.CommandTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Task;

import org.junit.Before;
import org.junit.Test;

import Commands.TagCommand;
import gui.GUIModel;
import Commands.Command;

public class TagCommandTests {

	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: tag <task_id> <tag name>";
	private static final String MESSAGE_INVALID_ID = "Task ID: %s\n";
	
	@Before
	public void setUp() {
		ArrayList<Task> testList = new ArrayList<Task>();
		for (int i = 0; i < 10; i ++) {
			testList.add(new Task());
		}
		GUIModel.setTaskList(testList);
		GUIModel.setTaskDoneList(testList);
	}
	
	@Test
	public void testNormalInputs() throws Exception {
		Command chemicalTask = new TagCommand("t1 hydrogen");
		Command oxyTask = new TagCommand("t1 oxygen");
		Command vigilanteTask = new TagCommand("t2 batman");
		Command philanthrophicTask = new TagCommand("t10 superman");
		Command filmNoirTask = new TagCommand("t4 kubrick");
		Command annoyingTask = new TagCommand("t10 orange");
		Command yellowStoneTask = new TagCommand("t2 geyser");
		Command sodaTask = new TagCommand("t1 cocacola");
		Command animalTask = new TagCommand("t9 moo");
	}
	
	@Test
	public void testWrongNumArgs(){	
		final String MESSAGE_ERROR = MESSAGE_INVALID_PARAMS;
		try {
			TagCommand moreArgs = new TagCommand("testTask/9-10-2015/0000/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}

		try {
			TagCommand flexiMoreArgs = new TagCommand("testFlexi by 1st January at 12pm at 2pm");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
		
		try {
			TagCommand lessArgs = new TagCommand("testFlexi/2359/daily");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
		
		/** flexi with less args is okay */
		
		try {
			TagCommand noArgs = new TagCommand("");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
	}
	
	@Test
	public void testWrongTitle(){
		final String MESSAGE_ERROR = MESSAGE_INVALID_PARAMS;
		try {
			TagCommand noTitle = new TagCommand("/9-10-2015/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
		try {
			TagCommand flexiNoTitle = new TagCommand("by 1st January at 12pm");
			fail();
		} catch (Exception e){
			assertEquals(e.getMessage(), MESSAGE_ERROR);
		}
	}
	
	@Test
	//NATTY PARSER ALLOWS NONSENSE FOR DATE PARSING
	public void testWrongDate() throws Exception{
		final String MESSAGE_ERROR = MESSAGE_INVALID_PARAMS;
		
		try {
			TagCommand impossibleDate = new TagCommand("testTask/99-10-2015/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "99-10-2015"), e.getMessage());
		}
		
		try {
			TagCommand lettersInDate = new TagCommand("testTask/9-10-2015a/2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "9-10-2015a"), e.getMessage());
		}
		try {
			TagCommand noDate = new TagCommand("testTask//2359/weekly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, ""), e.getMessage());
		}
	}
	
	@Test
	//NATTY PARSER ALLOWS NONSENSE FOR TIME PARSING
	public void testWrongEnd() throws Exception {
		final String MESSAGE_ERROR = MESSAGE_INVALID_PARAMS;
		
		try {
			TagCommand wrongEndLength = new TagCommand("testTask/9-10-2015/235/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "235"), e.getMessage());
		}
		try {
			TagCommand negativeEnd = new TagCommand("testTask/9-10-2015/-2345/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "-2345"), e.getMessage());
		}
		try {
			TagCommand endExceed24h = new TagCommand("testTask/9-10-2015/9999/daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "9999"), e.getMessage());
		}
		try {
			TagCommand noEnd = new TagCommand("testTask/9-10-2015//daily");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, ""), e.getMessage());
		}
	}
	
	@Test
	public void testRecurrence(){
		final String MESSAGE_ERROR = MESSAGE_INVALID_PARAMS;
		try {
			TagCommand invalidRecurrence = new TagCommand("testTask/9-10-2015/1200/fortnightly");
			fail();
		} catch (Exception e){
			assertEquals(String.format(MESSAGE_ERROR, "fortnightly"), e.getMessage());
		}
	}
}
