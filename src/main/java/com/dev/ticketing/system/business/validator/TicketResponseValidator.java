package com.dev.ticketing.system.business.validator;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.business.ErrorHandler;
import com.dev.ticketing.system.controller.TicketingApiController;
import com.dev.ticketing.system.model.ResponseErrorModel;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.TicketResponseModel;

public class TicketResponseValidator {
	
	private ErrorHandler errHandler = new ErrorHandler();
	
	public void validate(TicketResponseModel model, List<ResponseErrorModel> errors) {
		if (Objects.isNull(model.getText()) && model.getText().isEmpty()) {
			errors.add(errHandler.buildError("TICKET_RESPONSE_TEXT_REQUIRED", "Ticket Response Message is required!"));
		}
	}
	
}
