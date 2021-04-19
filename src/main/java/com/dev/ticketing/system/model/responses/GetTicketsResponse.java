package com.dev.ticketing.system.model.responses;

import java.util.List;

import com.dev.ticketing.system.model.TicketModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTicketsResponse extends ResponseBase {

	private List<TicketModel> tickets;

	public List<TicketModel> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketModel> tickets) {
		this.tickets = tickets;
	}

}
