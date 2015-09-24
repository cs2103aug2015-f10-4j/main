package main;

import java.io.*;
import java.util.*;

public class Task implements Serializable, Comparable<Task> {
	static enum RecurrencePeriod {
		DAILY, WEEKLY, MONTHLY, YEARLY
	}
	
	private String type;
	private String title;
	private String description;
	private Date dueDate;
	private int startTime;
	private int endTime;
	private RecurrencePeriod recurrence;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
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
	
	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public RecurrencePeriod getRecurrence() {
		return recurrence;
	}
	
	public void setRecurrence(String str) {
		if (str.equals("yearly")) {
			this.recurrence = RecurrencePeriod.YEARLY;
		} else if (str.equals("monthly")) {
			this.recurrence = RecurrencePeriod.MONTHLY;
		} else if (str.equals("weekly")) {
			this.recurrence = RecurrencePeriod.WEEKLY;
		} else if (str.equals("daily")) {
			this.recurrence = RecurrencePeriod.DAILY;
		}
	}
	
	public int compareTo(Task task) {
		return dueDate.compareTo(task.dueDate);
	}
}
