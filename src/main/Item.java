package main;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Item {

	private String type;
	private String title;
	private CustomDate startDate;
	private CustomDate endDate;
	private int startTime;
	private int endTime;
	private Set<String> tags = new HashSet<String>();
	private String priority;

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

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + endTime;
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
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
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
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
		public static final Comparator<Item> PRIORITY = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				if (i1.priority == null & i2.priority == null) {
					return 0;
				} else if (i1.priority == null) {
					return 1;
				} else if (i2.priority == null) {
					return -1;
				} else {
					return i1.priority.compareTo(i2.priority);
				}
			}
		};
		public static final Comparator<Item> DATE = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				if (i1.endDate == null && i2.endDate == null) {
					return 0;
				}
				if (i1.endDate == null) {
					return 1;
				}
				if (i2.endDate == null) {
					return -1;
				}
				return i1.endDate.getDate().compareTo(i2.endDate.getDate());
			}
		};
		public static final Comparator<Item> TITLE = (Item i1, Item i2) -> i1.title
				.compareTo(i2.title);
	}

	public Item copy() {
		Item copyTask = new Item();
		copyTask.setType(this.type);
		copyTask.setTitle(this.title);
		CustomDate sd = this.startDate != null ? new CustomDate(
				this.startDate.getDate()) : null;
		CustomDate ed = this.endDate != null ? new CustomDate(
				this.endDate.getDate()) : null;
		copyTask.setStartDate(sd);
		copyTask.setEndDate(ed);
		copyTask.setStartTime(this.startTime);
		copyTask.setEndTime(this.endTime);
		Set<String> copyTags = new HashSet<String>();
		for (String tag : this.tags) {
			copyTags.add(tag);
		}
		copyTask.setTags(copyTags);
		copyTask.setPriority(this.priority);
		return copyTask;
	}
}
