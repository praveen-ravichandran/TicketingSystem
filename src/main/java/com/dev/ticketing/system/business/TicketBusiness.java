package com.dev.ticketing.system.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.business.validator.TicketResponseValidator;
import com.dev.ticketing.system.business.validator.TicketValidator;
import com.dev.ticketing.system.common.ErrorHandler;
import com.dev.ticketing.system.constant.Constants;
import com.dev.ticketing.system.dao.TicketDao;
import com.dev.ticketing.system.dao.UserDao;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.TicketResponseModel;
import com.dev.ticketing.system.model.UserModel;
import com.dev.ticketing.system.model.enumtype.TicketStatus;
import com.dev.ticketing.system.model.requests.GetTicketsRequest;
import com.dev.ticketing.system.model.responses.DataUpdateResponse;
import com.dev.ticketing.system.model.responses.GetTicketByIdResponse;
import com.dev.ticketing.system.model.responses.GetTicketsResponse;
import com.dev.ticketing.system.model.responses.error.ResponseErrorModel;
import com.dev.ticketing.system.model.responses.error.ResponseMessageModel;
import com.dev.ticketing.system.service.EmailService;

public class TicketBusiness {

	@Autowired
	TicketDao ticketDao;

	@Autowired
	UserDao userDao;

	@Autowired
	ErrorHandler errHandler;

	@Autowired
	TicketValidator ticketValidator;

	@Autowired
	TicketResponseValidator ticketResponseValidator;

	@Autowired
	EmailService emailService;

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public GetTicketByIdResponse getTicketById(int ticketId) {
		GetTicketsRequest request = new GetTicketsRequest();
		request.setTicketId(ticketId);
		List<TicketModel> ticketsList = ticketDao.getTickets(request);
		GetTicketByIdResponse response = new GetTicketByIdResponse();
		if (ticketsList.size() > 0) {
			response.setTicket(ticketsList.get(0));
			response.getTicket().setResponses(ticketDao.getResponses(ticketId));
		} else {
			response.setMessages(errHandler.buildMessage(Constants.MSG_NODATA_CODE, Constants.MSG_NODATA_DESC));
		}
		return response;
	}

	public GetTicketsResponse getTickets(String assignedAgent, String customer, TicketStatus status) {
		GetTicketsRequest request = new GetTicketsRequest();
		request.setAssignedUser(assignedAgent);
		request.setCustomer(customer);
		request.setTicketStatus(status);
		List<TicketModel> ticketsList = ticketDao.getTickets(request);
		GetTicketsResponse response = new GetTicketsResponse();
		if (ticketsList.size() > 0) {
			response.setTickets(ticketsList);
			List<TicketResponseModel> ticketResponseList = ticketDao.getResponses(0);
			ticketsList.stream().forEach(t -> t.setResponses(ticketResponseList.stream()
					.filter(tr -> tr.getTicketId() == t.getId()).collect(Collectors.toList())));

		} else {
			response.setMessages(errHandler.buildMessage(Constants.MSG_NODATA_CODE, Constants.MSG_NODATA_DESC));
		}
		return response;
	}

	public DataUpdateResponse saveTicket(TicketModel ticket, String userMail) {
		List<ResponseErrorModel> errors = new ArrayList<>();
		List<ResponseMessageModel> messages = new ArrayList<>();
		DataUpdateResponse response = new DataUpdateResponse();
		String rowsAffected = null;
		if (ticket.getId() == 0) {
			ticketValidator.validate(ticket, errors);
			if (errors.size() == 0) {
				ticket.setStatus(TicketStatus.OPEN);
				ticket.setCreatedUser(userMail);
				rowsAffected = String.valueOf(ticketDao.insertTicket(ticket));
				checkDataUpdateResult(rowsAffected, errors, messages);
			}
		} else {
			TicketModel srcTicket = getTicketById(ticket.getId()).getTicket();
			if (srcTicket != null) {
				srcTicket.setTitle(ticket.getTitle());
				srcTicket.setDescription(ticket.getDescription());
				srcTicket.setStatus(ticket.getStatus());
				srcTicket.setPriority(ticket.getPriority());
				srcTicket.setCustomerMail(ticket.getCustomerMail());
				srcTicket.setUpdatedUser(userMail);
				ticketValidator.validate(ticket, errors);
				if (errors.size() == 0) {
					rowsAffected = String.valueOf(ticketDao.updateTicket(srcTicket));
					checkDataUpdateResult(rowsAffected, errors, messages);
				}
			} else {
				errors.add(errHandler.buildError(Constants.ERR_TICKET_NOT_FOUND_CODE,
						Constants.ERR_TICKET_NOT_FOUND_DESC));
			}
		}
		response.setTotalRecordsUpdated(rowsAffected);
		response.setErrors(errors.size() > 0 ? errors : null);
		response.setMessages(messages.size() > 0 ? messages : null);
		return response;
	}

	public DataUpdateResponse deleteTicket(int ticketId) {
		List<ResponseErrorModel> errors = new ArrayList<>();
		List<ResponseMessageModel> messages = new ArrayList<>();
		DataUpdateResponse response = new DataUpdateResponse();
		TicketModel ticket = getTicketById(ticketId).getTicket();
		String rowsAffected = null;
		if (ticket == null) {
			errors.add(errHandler.buildError(Constants.ERR_TICKET_NOT_FOUND_CODE, Constants.ERR_TICKET_NOT_FOUND_DESC));
		} else {
			ticketDao.deleteResponse(ticketId);
			ticketDao.deleteStatusLogs(ticketId);
			rowsAffected = String.valueOf(ticketDao.deleteTicket(ticketId));
			checkDataUpdateResult(rowsAffected, errors, messages);
		}
		response.setTotalRecordsUpdated(rowsAffected);
		response.setErrors(errors.size() > 0 ? errors : null);
		response.setMessages(messages.size() > 0 ? messages : null);
		return response;
	}

	public DataUpdateResponse responseToTicket(TicketResponseModel ticketResponse, String userMail) {
		List<ResponseErrorModel> errors = new ArrayList<>();
		List<ResponseMessageModel> messages = new ArrayList<>();
		DataUpdateResponse response = new DataUpdateResponse();
		String rowsAffected = null;
		TicketModel srcTicket = getTicketById(ticketResponse.getTicketId()).getTicket();
		if (srcTicket == null) {
			errors.add(errHandler.buildError(Constants.ERR_TICKET_NOT_FOUND_CODE, Constants.ERR_TICKET_NOT_FOUND_DESC));
		} else {
			ticketResponseValidator.validate(ticketResponse, errors);
			if (errors.size() == 0) {
				ticketResponse.setResponderMail(userMail);
				rowsAffected = String.valueOf(ticketDao.saveTicketResponse(ticketResponse));
				checkDataUpdateResult(rowsAffected, errors, messages);
				UserModel user = userDao.getUserByEmailAddress(userMail);
				if (user != null && user.isAgent()) {
					emailService.send(srcTicket.getCustomerMail(),
							String.format(Constants.EMAIL_SUBJECT_TICKET_AGENT_RESPONSE, ticketResponse.getTicketId(),
									srcTicket.getTitle()),
							String.format(Constants.EMAIL_BODY_TICKET_AGENT_RESPONSE_TEMPLATE, userMail,
									ticketResponse.getText()));
				}
			}
		}
		response.setTotalRecordsUpdated(rowsAffected);
		response.setErrors(errors.size() > 0 ? errors : null);
		response.setMessages(messages.size() > 0 ? messages : null);
		return response;
	}

	public void updateTicketStatusScheduled() {
		ticketDao.updateTicketStatusScheduled();
	}

	private void checkDataUpdateResult(String rowsAffected, List<ResponseErrorModel> errors,
			List<ResponseMessageModel> messages) {
		if (Integer.parseInt(rowsAffected) == 0) {
			errors.add(errHandler.buildError(Constants.ERR_DATA_UPDATE_FAIL_CODE, Constants.ERR_DATA_UPDATE_FAIL_DESC));
		} else {
			messages.add(errHandler.buildMessage(Constants.MSG_DATA_UPDATE_SUCCESS_CODE,
					Constants.MSG_DATA_UPDATE_SUCCESS_DESC));
		}
	}

}
