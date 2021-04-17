package com.dev.ticketing.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.ticketing.system.business.ErrorHandler;
import com.dev.ticketing.system.business.UserBusiness;
import com.dev.ticketing.system.business.ticketassignment.AgentQueue;
import com.dev.ticketing.system.business.validator.TicketResponseValidator;
import com.dev.ticketing.system.business.validator.TicketValidator;
import com.dev.ticketing.system.constant.Constants;
import com.dev.ticketing.system.model.ResponseErrorModel;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.TicketResponseModel;
import com.dev.ticketing.system.model.UserModel;
import com.dev.ticketing.system.model.enumtype.TicketStatus;
import com.dev.ticketing.system.service.EmailService;

/**
 * Handles requests for the Ticket service.
 */
@Controller
public class TicketingApiController {
	
	public static Map<Integer, TicketModel> ticketsTable = new HashMap<Integer, TicketModel>();
	public static Map<String, UserModel> usersTable = new HashMap<String, UserModel>();
	public static AgentQueue agentQueue = new AgentQueue();
	int ticketCounter = 0;
	
	private TicketValidator ticketValidator = new TicketValidator();
	private TicketResponseValidator ticketResponseValidator = new TicketResponseValidator();
	private ErrorHandler errHandler = new ErrorHandler();
	
	@RequestMapping(value = Constants.GET_TICKET_API, method = RequestMethod.GET)
	public @ResponseBody Object getTicket(@PathVariable("id") int ticketId) {
		if(!ticketsTable.containsKey(ticketId)) {
			return errHandler.buildError("TICKET_NOT_AVAILABLE", "Ticket Id not available!");
		}
		return ticketsTable.get(ticketId);
	}
	
	@RequestMapping(value = Constants.GET_ALL_TICKET_API, method = RequestMethod.GET)
	public @ResponseBody Object getAllTickets(@RequestParam(required = false) String assignedAgentMail,
			@RequestParam(required = false) String customerMail,
			@RequestParam(required = false) TicketStatus status) {
		List<TicketModel> tickets = new ArrayList<TicketModel>();
		/*
		 * for(Integer i : ticketIdKeys){ tickets.add(ticketsTable.get(i)); }
		 */
		tickets = ticketsTable
			.entrySet()
			.stream()
			.filter(ticket -> (Objects.isNull(status) || ticket.getValue().getStatus() == status)
					&& (Objects.isNull(assignedAgentMail) || ticket.getValue().getAssignedAgentMail().equals(assignedAgentMail))
					&& (Objects.isNull(customerMail) || ticket.getValue().getCustomerMail().equals(customerMail)))
			.map(t -> t.getValue())
			.collect(Collectors.toList());
		return tickets;
	}
	
	@RequestMapping(value = Constants.SAVE_TICKET_API, method = RequestMethod.POST)
	public @ResponseBody Object saveTicket(@RequestBody TicketModel ticket, @RequestHeader("User-Mail") String userMail) {
		if(!UserBusiness.isValidUser(usersTable, userMail)) {
			return errHandler.buildError("USER_INVALID", "OOPS! User not valid!");
		}
		List<ResponseErrorModel> errors = new ArrayList<>();
		if (ticket.getId() == 0) {
			ticketValidator.validate(ticket, errors);
			if(errors.size() == 0) {
				int currTicketId = ++ticketCounter;
				ticket.setId(currTicketId);
				ticket.setCreatedDate(new Date());
				ticket.setCreatedUser(userMail);
				ticket.setAssignedAgentMail(agentQueue.assignTicket());
				ticketsTable.put(ticket.getId(), ticket);
				return ticket;
			}
		} else if (ticketsTable.containsKey(ticket.getId())) {
			TicketModel srcTicket = ticketsTable.get(ticket.getId());
			TicketStatus prevStatus = srcTicket.getStatus();
			srcTicket.setTitle(ticket.getTitle());
			srcTicket.setDescription(ticket.getDescription());
			srcTicket.setStatus(ticket.getStatus());
			srcTicket.setPriority(ticket.getPriority());
			srcTicket.setCustomerMail(ticket.getCustomerMail());
			srcTicket.setUpdatedDate(new Date());
			srcTicket.setUpdatedUser(userMail);
			ticketValidator.validate(ticket, errors);
			if(errors.size() == 0) {
				if(ticket.getStatus() != null && prevStatus != ticket.getStatus()
						&& srcTicket.getStatus() == TicketStatus.CLOSED) {
					agentQueue.ticketCompletedUpdate(usersTable.get(srcTicket.getAssignedAgentMail()).getAgentNode());
				}
				ticketsTable.put(ticket.getId(), srcTicket);
				return srcTicket;
			}
		} else {
			errors.add(errHandler.buildError("TICKET_NOT_AVAILABLE", "Ticket Id not available!"));
		}
		return errors;
	}
	
	@RequestMapping(value = Constants.DELETE_TICKET_API, method = RequestMethod.PUT)
	public @ResponseBody Object deleteTicket(@PathVariable("id") int ticketId, @RequestHeader("User-Mail") String userMail) {
		if(!UserBusiness.isValidUser(usersTable, userMail)) {
			return errHandler.buildError("USER_INVALID", "OOPS! User not valid!");
		}
		TicketModel ticket = ticketsTable.get(ticketId);
		if (ticket == null) {
			return errHandler.buildError("TICKET_NOT_AVAILABLE", "Ticket Id not available!");
		}
		ticketsTable.remove(ticketId);
		return ticket;
	}
	
	@RequestMapping(value = Constants.RESPONSE_TICKET_API, method = RequestMethod.POST)
	public @ResponseBody Object responseToTicket(@PathVariable("id") int ticketId, @RequestBody TicketResponseModel ticketResponse, @RequestHeader("User-Mail") String userMail) {
		List<ResponseErrorModel> errors = new ArrayList<>();
		if(!UserBusiness.isValidUser(usersTable, userMail)) {
			errors.add(errHandler.buildError("USER_INVALID", "OOPS! User not valid!"));
			return errors;
		}
		TicketModel srcTicket = ticketsTable.get(ticketId);
		if (srcTicket == null) {
			errors.add(errHandler.buildError("TICKET_NOT_AVAILABLE", "Ticket Id not available!"));
			return errors;
		}
		ticketResponseValidator.validate(ticketResponse, errors);
		if (errors.size() > 0) {
			return errors;
		}
		List<TicketResponseModel> srcTicketResponses = srcTicket.getResponses();
		if(srcTicketResponses == null) {
			srcTicketResponses = new ArrayList<TicketResponseModel>();
		}
		if(usersTable.get(userMail).isAgent()) {
			EmailService.send(
					srcTicket.getCustomerMail(), 
					String.format(Constants.EMAIL_SUBJECT_TICKET_AGENT_RESPONSE, ticketId, srcTicket.getTitle()), 
					String.format(Constants.EMAIL_BODY_TICKET_AGENT_RESPONSE_TEMPLATE, userMail, ticketResponse.getText())
				);
		}
		ticketResponse.setTimestamp(new Date());
		ticketResponse.setResponderMail(userMail);
		srcTicketResponses.add(ticketResponse);
		srcTicket.setResponses(srcTicketResponses);
		ticketsTable.put(ticketId, srcTicket);
		return srcTicketResponses;
	}
	
	/*
	 * http://localhost:8080/TicketingSystem/api/user?id=4&userMail=sample@sample.com&agent=true
	 */
//	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
//	public @ResponseBody void addUser(@RequestParam int id, @RequestParam String userMail, @RequestParam boolean agent) {
//		usersTable.put(userMail, UserBusiness.buildUser(id, userMail, agent));
//	}
	
	static {
		usersTable.put("mailgsample@gmail.com", UserBusiness.buildUser(1, "mailgsample@gmail.com", true));
		usersTable.put("praveen1995rkn@gmail.com", UserBusiness.buildUser(2, "praveen1995rkn@gmail.com", false));
		usersTable.put("raja@growfin.ai", UserBusiness.buildUser(3, "raja@growfin.ai", true));
		usersTable.put("yogesh@sinecycle.com", UserBusiness.buildUser(4, "yogesh@sinecycle.com", true));
	}
}
