package com.dev.ticketing.system.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ticketing.system.dao.UserDao;
import com.dev.ticketing.system.model.UserModel;

public class UserBusiness {

	@Autowired
	UserDao userDao;

	public boolean isValidUser(String checkingEmail) {
		return userDao.getUserByEmailAddress(checkingEmail) != null;
	}

	public UserModel getUserByEmailAddress(String checkingEmail) {
		return userDao.getUserByEmailAddress(checkingEmail);
	}
}
