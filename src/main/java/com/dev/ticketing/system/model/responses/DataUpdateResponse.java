package com.dev.ticketing.system.model.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataUpdateResponse extends ResponseBase {

	private String totalRecordsAffected;

	public String getTotalRecordsUpdated() {
		return totalRecordsAffected;
	}

	public void setTotalRecordsUpdated(String totalRecordsAffected) {
		this.totalRecordsAffected = totalRecordsAffected;
	}
}
