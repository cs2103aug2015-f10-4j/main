package Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PathCommand extends Command {

	private String location;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format: path <location>";
	
	public PathCommand(String args) throws Exception {
		super(args);

		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		
		if(validNumArgs()){
			this.location = argsArray.get(1);
			File file = new File(location);
			
			//this assumes that directory exists
			if(!file.isDirectory()){
				//wanted to throw error
			}
		} else {
			throw new IllegalArgumentException(MESSAGE_INVALID_PARAMS);
		}
		
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validNumArgs() {
		if(count != 1){
			return false;
		} else {
			return true;
		}
	}

}
