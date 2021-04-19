package com.dev.ticketing.system.common;

import com.dev.ticketing.system.model.responses.error.ResponseErrorModel;
import com.dev.ticketing.system.model.responses.error.ResponseMessageModel;

public class ErrorHandler {

	public ResponseErrorModel buildError(String errCode, String description) {
		ResponseErrorModel error = new ResponseErrorModel();
		error.setErrorCode(errCode);
		error.setDescription(description);
		return error;
	}

	public ResponseMessageModel buildMessage(String msgCode, String description) {
		ResponseMessageModel message = new ResponseMessageModel();
		message.setMessageCode(msgCode);
		message.setDescription(description);
		return message;
	}
}
