package com.dev.ticketing.system.business.validator;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.common.ErrorHandler;
import com.dev.ticketing.system.model.responses.error.ResponseErrorModel;
import com.dev.ticketing.system.model.TicketResponseModel;

public class TicketResponseValidator {

	@Autowired
	private ErrorHandler errHandler;

	public void validate(TicketResponseModel model, List<ResponseErrorModel> errors) {
		if (Objects.isNull(model.getText()) && model.getText().isEmpty()) {
			errors.add(errHandler.buildError("TICKET_RESPONSE_TEXT_REQUIRED", "Ticket Response Message is required!"));
		}
	}

}
