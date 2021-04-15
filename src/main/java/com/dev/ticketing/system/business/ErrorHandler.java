package com.dev.ticketing.system.business;

import com.dev.ticketing.system.model.ResponseErrorModel;

public class ErrorHandler {
	
	public ResponseErrorModel buildError(String errCode, String description) {
		ResponseErrorModel error = new ResponseErrorModel();
		error.setErrorCode(errCode);
		error.setDescription(description);
		return error;
	}
	
}
