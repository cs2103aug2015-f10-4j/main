package parser;

import command.Command;

public class DefaultParser extends ArgsParserSkeleton{

	public DefaultParser(String args) {
		super(args);
	}

	@Override
	void setProperParams() {
		// TODO Auto-generated method stub
		
	}

	@Override
	boolean validNumArgs() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	Command getCommand() {
		switch(args){
		case "exit":
			return null;
		case "help":
			return null;
		case "undo":
			return null;
		case "redo":
			return null;
		default:
			break;
		}
		return null;
	}

}
