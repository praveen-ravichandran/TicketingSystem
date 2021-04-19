package com.dev.ticketing.system.model;

public class UserModel {

	private int userId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private boolean isAgent;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean isAgent() {
		return isAgent;
	}

	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}

}
