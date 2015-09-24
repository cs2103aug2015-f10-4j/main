package main;

import java.util.*;

public class Task {
	static enum Type {
		TASK, RECURRING_TASK, EVENT
	}
	
	static enum RecurrencePeriod {
		DAILY, WEEKLY, MONTHLY, YEARLY
	}
	
	private Type type;
	private String title;
	private String description;
	private Date dueDate;
	private Date startTime;
	private Date endTime;
	private RecurrencePeriod recurrence;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public RecurrencePeriod getRecurrence() {
		return recurrence;
	}
	
	public void setRecurrence(RecurrencePeriod recurrence) {
		this.recurrence = recurrence;
	}
}
