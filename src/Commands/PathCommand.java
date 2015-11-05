package Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import main.Magical;

public class PathCommand extends Command {

	private String location;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: path <location>";
	
	public PathCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>();
		argsArray.add(args);
		this.count = argsArray.size();
		this.location = args;
		
	}

	@Override
	public String execute() throws Exception {
		Magical.storage.changeFilePath(location);
		return "Path changed to: " + location;
	}

	@Override
	public boolean validNumArgs() {
		return true;
	}

}
