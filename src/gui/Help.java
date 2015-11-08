package gui;

public class Help {

	public static final String ENDL = System.getProperty("line.separator");

	public static final String HEADER_TEXT = "Magical User Guide";

	public static final String BODY_TEXT = "Press any key to close this window." + ENDL +
			ENDL + "To add a task:" + ENDL +
			"> add [task title] by [due date] [recurrence]" + ENDL +
			ENDL +
			"To add an event:" + ENDL +
			"> event [title] from [start date] to [start time] to [end date] [end time] [recurrence]" + ENDL +
			ENDL +
			"To mark a task as 'done':" + ENDL +
			"> done [task ID]" + ENDL +
			ENDL +
			"To delete a task:" + ENDL +
			"> delete [task ID]" + ENDL +
			ENDL +
			"To edit a task:" + ENDL +
			"> edit [task ID] [field] [updated value]" + ENDL +
			ENDL +
			"To tag a task:" + ENDL +
			"> tag [task ID] [tag name]" + ENDL +
			ENDL +
			"To untag a task:" + ENDL +
			"> untag [taskID] [tag name]" + ENDL +
			ENDL +
			"To show all tasks:" + ENDL +
			"> show" + ENDL +
			ENDL +
			"To show a different tab:" + ENDL +
			"> show [tasks / events]" + ENDL +
			ENDL +
			"To show tasks/events of a certain tag:" + ENDL +
			"> show [tag name]" + ENDL +
			ENDL +
			"To sort tasks:" + ENDL +
			"> sort [priority / date / title]" + ENDL +
			ENDL +
			"To search for a task:" + ENDL +
			"> search [query]" + ENDL +
			ENDL +
			"To change a task's priority:" + ENDL +
			"> set [task ID] [number from 1 to 10]" + ENDL +
			ENDL +
			"To undo a previous action:" + ENDL +
			"> undo" + ENDL +
			ENDL +
			"To change where your list is stored:" + ENDL +
			"> path [filepath]" + ENDL +
			ENDL +
			"To exit the application:" + ENDL +
			"> exit";

}
