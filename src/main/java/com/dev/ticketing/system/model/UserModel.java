package com.dev.ticketing.system.model;

import com.dev.ticketing.system.business.ticketassignment.AgentNode;

public class UserModel {

	private int userId;
	private String emailAddress;
	private boolean isAgent;
	private AgentNode agentNode;

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

	public AgentNode getAgentNode() {
		return agentNode;
	}

	public void setAgentNode(AgentNode agentNode) {
		this.agentNode = agentNode;
	}

}
