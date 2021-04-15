package com.dev.ticketing.system.model;

public class UserModel {
	
	private int userId;
	private String emailAddress;
	private boolean isAgent;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
