package com.dev.ticketing.system.business.ticketassignment;

import com.dev.ticketing.system.model.UserModel;

public class AgentWorkerModel {

	private UserModel agent;
	private int ticketAssignedCounter;
	
	public UserModel getAgent() {
		return agent;
	}
	public void setAgent(UserModel agent) {
		this.agent = agent;
	}
	
	public int getTicketAssignedCounter() {
		return ticketAssignedCounter;
	}
	public void setTicketAssignedCounter(int ticketAssignedCounter) {
		this.ticketAssignedCounter = ticketAssignedCounter;
	}
}
