package com.dev.ticketing.system.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.dev.ticketing.system.common.DBHelper;
import com.dev.ticketing.system.constant.DBQueryConst;
import com.dev.ticketing.system.model.TicketModel;
import com.dev.ticketing.system.model.TicketResponseModel;
import com.dev.ticketing.system.model.enumtype.TicketPriority;
import com.dev.ticketing.system.model.enumtype.TicketStatus;
import com.dev.ticketing.system.model.requests.GetTicketsRequest;

public class TicketDao {

	JdbcTemplate template;

	@Autowired
	DBHelper dbHelper;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public List<TicketModel> getTickets(GetTicketsRequest request) {
		return template.query(DBQueryConst.GET_TICKETS, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				String temp = null;
				ps.setString(1, request.getTicketId() == 0 ? null : String.valueOf(request.getTicketId()));
				ps.setString(2, request.getTicketId() == 0 ? null : String.valueOf(request.getTicketId()));
				ps.setString(3, request.getAssignedUser());
				temp = dbHelper.getFullMatch(request.getAssignedUser());
				ps.setString(4, temp);
				ps.setString(5, temp);
				ps.setString(6, temp);
				ps.setString(7, request.getCustomer());
				temp = dbHelper.getFullMatch(request.getCustomer());
				ps.setString(8, temp);
				ps.setString(9, temp);
				ps.setString(10, temp);
				ps.setString(11, request.getTicketStatus() != null ? request.getTicketStatus().name() : null);
				ps.setString(12, request.getTicketStatus() != null ? request.getTicketStatus().name() : null);
			}
		}, new RowMapper<TicketModel>() {
			public TicketModel mapRow(ResultSet rs, int row) throws SQLException {
				TicketModel ticket = new TicketModel();
				ticket.setId(rs.getInt("TicketId"));
				ticket.setTitle(rs.getString("Title"));
				ticket.setDescription(rs.getString("Description"));
				ticket.setStatus(TicketStatus.valueOf(rs.getString("Status")));
				ticket.setPriority(TicketPriority.valueOf(rs.getString("Priority")));
				ticket.setCustomerMail(rs.getString("CustomerMail"));
				ticket.setAssignedAgentMail(rs.getString("AssignedAgentMail"));
				return ticket;
			}
		});
	}

	public int insertTicket(TicketModel ticket) {
		return template.update(DBQueryConst.INSERT_TICKET, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, ticket.getTitle());
				ps.setString(2, ticket.getDescription());
				ps.setString(3, ticket.getStatus().name());
				ps.setString(4, ticket.getPriority().name());
				ps.setString(5, ticket.getCustomerMail());
				ps.setString(6, ticket.getCreatedUser());
			}
		});
	}

	public int updateTicket(TicketModel srcTicket) {
		return template.update(DBQueryConst.UPDATE_TICKET, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, srcTicket.getTitle());
				ps.setString(2, srcTicket.getDescription());
				ps.setString(3, srcTicket.getStatus().name());
				ps.setString(4, srcTicket.getPriority().name());
				ps.setString(5, srcTicket.getCustomerMail());
				ps.setString(6, srcTicket.getUpdatedUser());
				ps.setInt(7, srcTicket.getId());
			}
		});
	}

	public int deleteTicket(int ticketId) {
		return template.update(DBQueryConst.DELETE_TICKET, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, ticketId);
			}
		});
	}

	public int deleteResponse(int ticketId) {
		return template.update(DBQueryConst.DELETE_RESPONSE, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, ticketId);
			}
		});
	}

	public int deleteStatusLogs(int ticketId) {
		return template.update(DBQueryConst.DELETE_STATUS_LOGS, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, ticketId);
			}
		});
	}

	public List<TicketResponseModel> getResponses(int ticketId) {
		return template.query(DBQueryConst.GET_TICKETS_RESPONSE, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, ticketId == 0 ? null : String.valueOf(ticketId));
				ps.setString(2, String.valueOf(ticketId));
			}
		}, new RowMapper<TicketResponseModel>() {
			public TicketResponseModel mapRow(ResultSet rs, int row) throws SQLException {
				TicketResponseModel ticketResponse = new TicketResponseModel();
				ticketResponse.setText(rs.getString("Text"));
				ticketResponse.setTimestamp(rs.getDate("ResponsedDate"));
				ticketResponse.setResponderMail(rs.getString("ResponsedUserMail"));
				ticketResponse.setTicketId(rs.getInt("TicketId"));
				return ticketResponse;
			}
		});
	}

	public int saveTicketResponse(TicketResponseModel ticketResponse) {
		return template.update(DBQueryConst.INSERT_TICKET_RESPONSE, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1,
						ticketResponse.getTicketId() == 0 ? null : String.valueOf(ticketResponse.getTicketId()));
				ps.setString(2, ticketResponse.getText());
				ps.setString(3, ticketResponse.getResponderMail());
			}
		});
	}

	public void updateTicketStatusScheduled() {
		template.batchUpdate(
				DBQueryConst.GET_UPDATE_STATUS_SCHEDULED_DATASET,
				DBQueryConst.UPDATE_STATUS_SCHEDULED,
				DBQueryConst.DROP_UPDATE_STATUS_SCHEDULED_DATASET_TEMP_TABLE
			);
	}

}
