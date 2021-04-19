package com.dev.ticketing.system.model;

import java.util.Date;

public class TicketResponseModel {

	private String text;
	private String responderMail;
	private Date timestamp;
	private int ticketId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResponderMail() {
		return responderMail;
	}

	public void setResponderMail(String responderMail) {
		this.responderMail = responderMail;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
}
