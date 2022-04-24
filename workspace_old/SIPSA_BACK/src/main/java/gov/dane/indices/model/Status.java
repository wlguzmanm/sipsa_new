package gov.dane.indices.model;

public class Status {
	public enum StatusType {

		OK, ERROR, WARNING
	}
	private String description;
	private StatusType type;

	public Status() {
	}

	public Status(String description, StatusType type) {
		this.description = description;
		this.type = type;
	}

	/**
	 * Get the value of description
	 *
	 * @return the value of description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of description
	 *
	 * @param description new value of description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of type
	 *
	 * @return the value of type
	 */
	public StatusType getType() {
		return type;
	}

	/**
	 * Set the value of type
	 *
	 * @param type new value of type
	 */
	public void setType(StatusType type) {
		this.type = type;
	}

}
