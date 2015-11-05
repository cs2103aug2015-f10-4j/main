package Commands;

import java.util.ArrayList;

import main.Magical;

public class PathCommand extends Command {

	private String location;

	public PathCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>();
		argsArray.add(args);
		this.count = argsArray.size();
		this.location = args;

	}

	/**
	 * This method executes the path command. Which changes the file path of the
	 * database file.
	 * 
	 * @param None
	 * 
	 * @return message to show user
	 */
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
