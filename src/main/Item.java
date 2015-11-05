package main;

import java.util.*;

public class Item {

	private String type;
	private String title;
	private CustomDate startDate;
	private CustomDate endDate;
	private int startTime;
	private int endTime;
	private RecurrencePeriod recurrence;
	private Set<String> tags = new HashSet<String>();
	private int priority;

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

	public CustomDate getStartDate() {
		return startDate;
	}

	public void setStartDate(CustomDate startDate) {
		this.startDate = startDate;
	}

	public CustomDate getEndDate() {
		return endDate;
	}

	public void setEndDate(CustomDate endDate) {
		this.endDate = endDate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + endTime;
		result = prime * result + priority;
		result = prime * result
				+ ((recurrence == null) ? 0 : recurrence.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		Item other = (Item) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (endTime != other.endTime)
			return false;
		if (priority != other.priority)
			return false;
		if (recurrence != other.recurrence)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
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

	public static class Comparators {
		public static final Comparator<Item> PRIORITY = (Item t1, Item t2) -> Integer
				.compare(t1.priority, t2.priority);
		public static final Comparator<Item> DATE = (Item t1, Item t2) -> t1.endDate
				.compareTo(t2.endDate);
		public static final Comparator<Item> TITLE = (Item t1, Item t2) -> t1.title
				.compareTo(t2.title);
	}

	public Item copy() {
		Item copyTask = new Item();
		copyTask.setType(this.type);
		copyTask.setTitle(this.title);
		CustomDate sd = this.startDate != null ? new CustomDate(this.startDate.getDate()) : null;
		CustomDate ed = this.endDate != null ? new CustomDate(this.endDate.getDate()) : null;
		copyTask.setStartDate(sd);
		copyTask.setEndDate(ed);
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
}
