package main;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Item represents either a task or an event. This class stores all properties
 * essential to meaningfully describe a task or an event.
 *
 * @@author A0131729E
 * @author Varun Patro
 */
public class Item {

	/**
	 * Comparators for ordering items in different indexes
	 */
	public static class Comparators {
		public static final Comparator<Item> PRIORITY = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				int i1p, i2p = 0;
				if (i1.priority == null) {
					i1p = 0;
				} else if (i1.priority.equals("high")) {
					i1p = 3;
				} else if (i1.priority.equals("medium")) {
					i1p = 2;
				} else if (i1.priority.equals("low")) {
					i1p = 1;
				} else {
					i1p = 0;
				}
				if (i2.priority == null) {
					i2p = 0;
				} else if (i2.priority.equals("high")) {
					i2p = 3;
				} else if (i2.priority.equals("medium")) {
					i2p = 2;
				} else if (i2.priority.equals("low")) {
					i2p = 1;
				} else {
					i2p = 0;
				}
				return Integer.compare(i2p, i1p);
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

	private String type;
	private String title;
	private CustomDate startDate;
	private CustomDate endDate;
	private int startTime;
	private int endTime;
	private Set<String> tags = new HashSet<String>();
	private String priority;

	/**
	 * Method to deep copy this item
	 * 
	 * @return a deep copy of this item
	 */
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

	/**
	 * Getter for end date.
	 */
	public CustomDate getEndDate() {
		return endDate;
	}

	/**
	 * Getter for end time.
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Getter for priority.
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Getter for start date.
	 */
	public CustomDate getStartDate() {
		return startDate;
	}

	/**
	 * Getter for start time.
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Getter for tags.
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * Getter for title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for type.
	 */
	public String getType() {
		return type;
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

	/**
	 * Setter for end date.
	 */
	public void setEndDate(CustomDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * Setter for end time.
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * Setter for priority.
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Setter for start date.
	 */
	public void setStartDate(CustomDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Setter for start time.
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * Setter for tags.
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	/**
	 * Setter for title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Setter for type.
	 */
	public void setType(String type) {
		this.type = type;
	}
}
