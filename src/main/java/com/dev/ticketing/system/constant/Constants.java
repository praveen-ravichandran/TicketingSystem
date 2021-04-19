package com.dev.ticketing.system.constant;

public class Constants {
	public static final String GET_TICKET_API = "/api/ticket/{id}";
	public static final String GET_ALL_TICKET_API = "/api/tickets";
	public static final String SAVE_TICKET_API = "/api/ticket/save";
	public static final String DELETE_TICKET_API = "/api/ticket/{id}/delete";
	public static final String RESPONSE_TICKET_API = "/api/ticket/response/save";
	
	public static final String EMAIL_SUBJECT_TICKET_AGENT_RESPONSE = "Reg: Ticket %s: %s";
	public static final String EMAIL_BODY_TICKET_AGENT_RESPONSE_TEMPLATE = "Agent %s has commented on your Ticket.\n"
			+ "\"%s\"\n\nThanks,\nSupport Team.";
	
	public static final String MSG_NODATA_CODE = "MSG_NO_DATA";
	public static final String MSG_NODATA_DESC = "Sorry! No Data found.";
	
	public static final String MSG_DATA_UPDATE_SUCCESS_CODE = "MSG_DATA_UPDATE_SUCCESS_CODE";
	public static final String MSG_DATA_UPDATE_SUCCESS_DESC = "Data Update Successful!";
	
	public static final String ERR_DATA_UPDATE_FAIL_CODE = "ERR_DATA_UPDATE_FAIL_CODE";
	public static final String ERR_DATA_UPDATE_FAIL_DESC = "Data Update Action Failed!";
	
	public static final String ERR_TICKET_NOT_FOUND_CODE = "ERR_TICKET_NOT_FOUND_CODE";
	public static final String ERR_TICKET_NOT_FOUND_DESC = "Ticket being accessed not found!";
}
