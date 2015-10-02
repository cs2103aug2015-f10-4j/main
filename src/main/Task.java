package main;

import java.io.*;
import java.util.*;

public class Task implements Serializable, Comparable<Task>, Cloneable {
	static enum RecurrencePeriod {
		DAILY, WEEKLY, MONTHLY, YEARLY, NONE
	}

	private String type;
	private String title;
	private String description;
	private Date dueDate;
	private int startTime;
	private int endTime;
	private RecurrencePeriod recurrence;
	private Set<String> tags = new HashSet<String>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + endTime;
		result = prime * result
				+ ((recurrence == null) ? 0 : recurrence.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (endTime != other.endTime)
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

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Task copy() throws IOException, ClassNotFoundException {
		Object obj = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(this);
		out.flush();
		out.close();

		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
		obj = in.readObject();
		return (Task) obj;
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
		if(str == null){
			this.recurrence = RecurrencePeriod.NONE;
		} else if (str.equals("yearly")) {
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

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
}
