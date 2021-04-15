package com.dev.ticketing.system.business.validator;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.business.ErrorHandler;
import com.dev.ticketing.system.controller.TicketingApiController;
import com.dev.ticketing.system.model.ResponseErrorModel;
import com.dev.ticketing.system.model.TicketModel;

public class TicketValidator {
	
	private ErrorHandler errHandler = new ErrorHandler();
	
	public void validate(TicketModel model, List<ResponseErrorModel> errors) {
		if (Objects.isNull(model.getTitle())) {
			errors.add(errHandler.buildError("TICKET_TITLE_REQUIRED", "Title is required!"));
		}
		if(Objects.isNull(model.getDescription())) {
			errors.add(errHandler.buildError("TICKET_DESCRIPTION_REQUIRED", "Description is required!"));
		}
		if(Objects.isNull(model.getCustomerMail())) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_REQUIRED", "Customer's mail is required!"));
		}
		if(Objects.isNull(model.getStatus())) {
			errors.add(errHandler.buildError("TICKET_STATUS_REQUIRED", "Status is required!"));
		}
		if(Objects.isNull(model.getPriority())) {
			errors.add(errHandler.buildError("TICKET_PRIORITY_REQUIRED", "Priority is required!"));
		}
		if(!TicketingApiController.usersTable.containsKey(model.getCustomerMail())) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_INVALID", "Customer's mail is Invalid!"));
		} else if (TicketingApiController.usersTable.get(model.getCustomerMail()).isAgent()) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_AGENT", "Customer's mail provided is registered as Agent!"));
		}
	}
	
}
