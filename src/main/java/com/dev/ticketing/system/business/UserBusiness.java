package com.dev.ticketing.system.business;

import java.util.Map;

import com.dev.ticketing.system.model.UserModel;

public class UserBusiness {

	public static UserModel buildUser(int id, String email, boolean isAgent) {
		UserModel user = new UserModel();
		user.setUserId(id);
		user.setEmailAddress(email);
		user.setAgent(isAgent);
		return user;
	}
	
	public static boolean isValidUser(Map<String, UserModel> users, String checkingEmail) {
		return users.containsKey(checkingEmail);
	}
}
