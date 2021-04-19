package com.dev.ticketing.system.model.requests;

import com.dev.ticketing.system.model.enumtype.TicketStatus;

public class GetTicketsRequest {

	private int ticketId;
	private String assignedUser;
	private String customer;
	private TicketStatus ticketStatus;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

}
