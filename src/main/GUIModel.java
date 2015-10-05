package main;

public class GUIModel {
	
	private boolean displayNotificationBar;
	private String notificationMessage;
	private String[] tableHeaders;
	private int numTables;
	private String errorMessage;
	
	public GUIModel() {
	}
	
	public void setNumTables(int i) {
		this.numTables = i;
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public void setTableHeaders(String[] headerArray) {
		this.tableHeaders = headerArray;
	}
	
	

}
