package com.dev.ticketing.system.common;

public class DBHelper {

	public String getFullMatch(String param) {
		return "%" + param + "%";
	}

	public String getForwardMatch(String param) {
		return "%" + param;
	}

	public String getBackwardMatch(String param) {
		return param + "%";
	}

}
