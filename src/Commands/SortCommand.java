package Commands;

import java.util.ArrayList;
import java.util.Arrays;

import javax.print.DocFlavor.STRING;

public class SortCommand extends Command{

	private String sortParam;
	
	private static final String MESSAGE_INVALID_PARAMS = "Number of Arguments\n"
			+ "Use Format:\n"
			+ "1. sort\n"
			+ "2. sort <sortParam>\n";

	private static final String MESSAGE_INVALID_SORT = "Sort Parameter: %s";
	
	
	public SortCommand(String args) throws Exception {
		super(args);
		
		this.argsArray = new ArrayList<String>(Arrays.asList(args.split(" ")));
		this.count = argsArray.size();
		this.sortParam = argsArray.get(0).trim().toLowerCase();
		
		if(validNumArgs()){
			if(sortParam.equals(STRING_EMPTY)){
				sortParam = "priority";
			} else if (!isValidSortParam()){
				error += String.format(MESSAGE_INVALID_SORT, sortParam);
			}
			if (!error.equals(STRING_EMPTY)) {
				throw new Exception(MESSAGE_HEADER_INVALID + error);
			}	
		} else {
			error += MESSAGE_INVALID_PARAMS;
			throw new Exception(MESSAGE_HEADER_INVALID + error);
		}
	}
	
	private boolean isValidSortParam(){
		switch(sortParam){
			case "priority":
				return true;
			case "title":
				return true;
			case "date":
				return true;
			default:
				return false;
		}
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validNumArgs() {
		if (count > 1){
			return false;
		} else {
			return true;
		}
	}

}
