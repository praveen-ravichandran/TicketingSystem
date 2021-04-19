package com.dev.ticketing.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dev.ticketing.system.constant.DBQueryConst;
import com.dev.ticketing.system.model.UserModel;

public class UserDao {

	@Autowired
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public UserModel getUserByEmailAddress(String userMail) {
		return template.queryForObject(DBQueryConst.GET_USER_BY_ID, new RowMapper<UserModel>() {
			public UserModel mapRow(ResultSet rs, int row) throws SQLException {
				UserModel user = new UserModel();
				user.setUserId(rs.getInt("UserId"));
				user.setEmailAddress(rs.getString("EmailAddress"));
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setAgent(rs.getBoolean("IsAgent"));
				return user;
			}
		}, new Object[] { userMail });
	}
}
