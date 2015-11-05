package main;

import java.io.*;
import java.util.*;

public class Task implements Comparable<Task>, Cloneable {
	private String type;
	private String title;
	private CustomDate dueDate;
	private int startTime;
	private int endTime;
	private RecurrencePeriod recurrence;
	private Set<String> tags = new HashSet<String>();
	private int priority;

	public Task copy(){
			Task copyTask = new Task();
			copyTask.setType(this.type);
			copyTask.setTitle(this.title);
			copyTask.setDueDate(this.dueDate);
			copyTask.setStartTime(this.startTime);
			copyTask.setEndTime(this.endTime);
			copyTask.setRecurrence(this.recurrence);
			Set<String> copyTags = new HashSet<String>();
			for (String tag : this.tags) {
				copyTags.add(tag);
			}
			copyTask.setTags(copyTags);
			copyTask.setPriority(this.priority);
			return copyTask;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + endTime;
		result = prime * result + priority;
		result = prime * result + ((recurrence == null) ? 0 : recurrence.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (endTime != other.endTime)
			return false;
		if (priority != other.priority)
			return false;
		if (recurrence != other.recurrence)
			return false;
		if (startTime != other.startTime)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public CustomDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(CustomDate dueDate2) {
		this.dueDate = dueDate2;
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

	public void setRecurrence(RecurrencePeriod recurrence) {
		this.recurrence = recurrence;
	}

	public int compareTo(Task task) {
		return dueDate.compareTo(task.dueDate);
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public static class Comparators {
	    public static final Comparator<Task> PRIORITY = (Task t1, Task t2) -> Integer.compare(t1.priority, t2.priority);
	    public static final Comparator<Task> DATE = (Task t1, Task t2) -> t1.dueDate.compareTo(t2.dueDate);
	    public static final Comparator<Task> TITLE = (Task t1, Task t2) -> t1.title.compareTo(t2.title);
	}
}
