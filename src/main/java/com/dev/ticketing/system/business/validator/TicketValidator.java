package com.dev.ticketing.system.business.validator;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.business.UserBusiness;
import com.dev.ticketing.system.common.ErrorHandler;
import com.dev.ticketing.system.model.responses.error.ResponseErrorModel;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.UserModel;

public class TicketValidator {

	@Autowired
	private ErrorHandler errHandler;

	@Autowired
	UserBusiness userBusiness;

	public void validate(TicketModel model, List<ResponseErrorModel> errors) {
		if (Objects.isNull(model.getTitle())) {
			errors.add(errHandler.buildError("TICKET_TITLE_REQUIRED", "Title is required!"));
		}
		if (Objects.isNull(model.getDescription())) {
			errors.add(errHandler.buildError("TICKET_DESCRIPTION_REQUIRED", "Description is required!"));
		}
		if (Objects.isNull(model.getCustomerMail())) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_REQUIRED", "Customer's mail is required!"));
		}
		if (model.getId() != 0 && Objects.isNull(model.getStatus())) {
			errors.add(errHandler.buildError("TICKET_STATUS_REQUIRED", "Status is required!"));
		}
		if (Objects.isNull(model.getPriority())) {
			errors.add(errHandler.buildError("TICKET_PRIORITY_REQUIRED", "Priority is required!"));
		}
		UserModel customer = userBusiness.getUserByEmailAddress(model.getCustomerMail());
		if (customer == null) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_INVALID", "Customer's mail is Invalid!"));
		} else if (customer.isAgent()) {
			errors.add(errHandler.buildError("TICKET_CUSTOMERMAIL_AGENT",
					"Customer's mail provided is registered as Agent!"));
		}
	}

}
