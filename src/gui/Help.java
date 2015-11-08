package gui;

public class Help {

	public static final String ENDL = System.getProperty("line.separator");

	public static final String HEADER_TEXT = "Magical User Guide";

	public static final String BODY_TEXT = "Press any key to close this window." + ENDL +
			ENDL + "Sample formats for dates and times:" +
			ENDL + "- today" +
			ENDL + "- tomorrow 5pm" +
			ENDL + "- Jan 1 10am" +
			ENDL + "- 31 December 5.30pm" + ENDL +
			ENDL + "To add a task:" + ENDL +
			"> add [task title] by [due date]" + ENDL +
			ENDL +
			"To add an event:" + ENDL +
			"> event [title] from [start date] [start time] to [end date] [end time]" + ENDL +
			ENDL +
			"To mark an item as 'done':" + ENDL +
			"> done [item ID]" + ENDL +
			ENDL +
			"To delete an item:" + ENDL +
			"> delete [item ID]" + ENDL +
			ENDL +
			"To edit an item:" + ENDL +
			"> edit [item ID] [field] [updated value]" + ENDL +
			ENDL +
			"To tag an item:" + ENDL +
			"> tag [item ID] [tag name]" + ENDL +
			ENDL +
			"To untag an item:" + ENDL +
			"> untag [item ID] [tag name]" + ENDL +
			ENDL +
			"To show all tasks:" + ENDL +
			"> show" + ENDL +
			ENDL +
			"To show a different tab:" + ENDL +
			"> show [tasks / events]" + ENDL +
			ENDL +
			"To show items of a certain tag:" + ENDL +
			"> show [tag name]" + ENDL +
			ENDL +
			"To sort items:" + ENDL +
			"> sort [priority / date / title]" + ENDL +
			ENDL +
			"To search for an item:" + ENDL +
			"> search [query]" + ENDL +
			ENDL +
			"To change an item's priority:" + ENDL +
			"> set [task ID] [number from 1 to 10]" + ENDL +
			ENDL +
			"To undo a previous action:" + ENDL +
			"> undo" + ENDL +
			"or use the shortcut Ctrl + Z" + ENDL +
			ENDL +
			"To redo an undone action:" + ENDL +
			"> redo" + ENDL +
			"or use the shortcut Ctrl + Y" + ENDL +
			ENDL +
			"To change where your list is stored:" + ENDL +
			"> path [filepath]" + ENDL +
			ENDL +
			"To exit the application:" + ENDL +
			"> exit";

}
