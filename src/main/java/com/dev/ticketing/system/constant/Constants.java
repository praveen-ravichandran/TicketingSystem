package com.dev.ticketing.system.constant;

public class Constants {
	public static final String GET_TICKET_API = "/api/ticket/{id}";
	public static final String GET_ALL_TICKET_API = "/api/tickets";
	public static final String SAVE_TICKET_API = "/api/ticket/save";
	public static final String DELETE_TICKET_API = "/api/ticket/{id}/delete";
	public static final String RESPONSE_TICKET_API = "/api/ticket/{id}/response/save";
	public static final String EMAIL_SUBJECT_TICKET_AGENT_RESPONSE = "Reg: Ticket %s: %s";
	public static final String EMAIL_BODY_TICKET_AGENT_RESPONSE_TEMPLATE = "Agent %s has commented on your Ticket.\n"
			+ "\"%s\"\n\nThanks,\nSupport Team.";
}
