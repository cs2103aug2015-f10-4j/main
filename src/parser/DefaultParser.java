package parser;

import command.Command;
import command.ExitCommand;
import command.HelpCommand;
import command.RedoCommand;
import command.UndoCommand;

public class DefaultParser extends ArgsParserAbstract {

	/**
	 * Constructor for default Parser objects.
	 * 
	 * @param args
	 */
	public DefaultParser(String args) {
		super(args);
	}

	@Override
	void setProperParams() {
	}

	@Override
	boolean validNumArgs() {
		return true;
	}

	@Override
	Command getCommand() {
		switch (args) {
		case "exit":
			Command exit = new ExitCommand();
			return exit;
		case "help":
			Command help = new HelpCommand();
			return help;
		case "undo":
			Command undo = new UndoCommand();
			return undo;
		case "redo":
			Command redo = new RedoCommand();
			return redo;
		default:
			break;
		}
		return null;
	}

}
