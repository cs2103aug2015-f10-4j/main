package Commands;

public class ExitCommand extends Command {
	
	public ExitCommand(String args) throws Exception {
		super(args);
	}
	
	public String execute() {
		System.exit(0);
		return null;
	}

	@Override
	public boolean validNumArgs() {
		return true;
	}
}
