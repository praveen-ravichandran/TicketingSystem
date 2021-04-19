package com.dev.ticketing.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.ticketing.system.common.ErrorHandler;
import com.dev.ticketing.system.business.TicketBusiness;
import com.dev.ticketing.system.constant.Constants;
import com.dev.ticketing.system.model.responses.DataUpdateResponse;
import com.dev.ticketing.system.model.responses.GetTicketByIdResponse;
import com.dev.ticketing.system.model.responses.GetTicketsResponse;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.TicketResponseModel;
import com.dev.ticketing.system.model.enumtype.TicketStatus;

/**
 * Handles requests for the Ticket service.
 */
@Controller
public class TicketingApiController {

	@Autowired
	ErrorHandler errHandler;

	@Autowired
	TicketBusiness ticketBusiness;

	@RequestMapping(value = Constants.GET_TICKET_API, method = RequestMethod.GET)
	public @ResponseBody GetTicketByIdResponse getTicket(@PathVariable("id") int ticketId) {
		return ticketBusiness.getTicketById(ticketId);
	}

	@RequestMapping(value = Constants.GET_ALL_TICKET_API, method = RequestMethod.GET)
	public @ResponseBody GetTicketsResponse getAllTickets(@RequestParam(required = false) String assignedAgent,
			@RequestParam(required = false) String customer, @RequestParam(required = false) TicketStatus status) {
		return ticketBusiness.getTickets(assignedAgent, customer, status);
	}

	@RequestMapping(value = Constants.SAVE_TICKET_API, method = RequestMethod.POST)
	public @ResponseBody DataUpdateResponse saveTicket(@RequestBody TicketModel ticket,
			@RequestHeader("User-Mail") String userMail) {
		return ticketBusiness.saveTicket(ticket, userMail);
	}

	@RequestMapping(value = Constants.DELETE_TICKET_API, method = RequestMethod.PUT)
	public @ResponseBody DataUpdateResponse deleteTicket(@PathVariable("id") int ticketId,
			@RequestHeader("User-Mail") String userMail) {
		return ticketBusiness.deleteTicket(ticketId);
	}

	@RequestMapping(value = Constants.RESPONSE_TICKET_API, method = RequestMethod.POST)
	public @ResponseBody DataUpdateResponse responseToTicket(@RequestBody TicketResponseModel ticketResponse,
			@RequestHeader("User-Mail") String userMail) {
		return ticketBusiness.responseToTicket(ticketResponse, userMail);
	}

}
