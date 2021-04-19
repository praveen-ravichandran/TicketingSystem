package com.dev.ticketing.system.model.responses;

import com.dev.ticketing.system.model.TicketModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTicketByIdResponse extends ResponseBase {

	private TicketModel ticket;

	public TicketModel getTicket() {
		return ticket;
	}

	public void setTicket(TicketModel ticket) {
		this.ticket = ticket;
	}
}
