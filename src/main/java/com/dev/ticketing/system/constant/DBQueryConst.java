package com.dev.ticketing.system.constant;

public class DBQueryConst {

	public static final String GET_TICKETS = "SELECT TicketId, Title, Description, ts.Name AS Status, tp.Name AS Priority, cust.EmailAddress AS CustomerMail, agent.EmailAddress AS AssignedAgentMail FROM Ticket t"
			+ " LEFT JOIN TicketStatus ts ON t.StatusId = ts.TicketStatusId"
			+ " LEFT JOIN TicketPriority tp ON t.PriorityId = tp.TicketPriorityId"
			+ " LEFT JOIN User agent ON t.AssignedAgentUserId = agent.UserId"
			+ " LEFT JOIN User cust ON t.CustomerUserId = cust.UserId"
			+ " WHERE (? IS NULL OR t.TicketId = ?)"
			+ " AND (? IS NULL OR agent.FirstName LIKE ? OR agent.LastName LIKE ? OR agent.EmailAddress LIKE ?)"
			+ " AND (? IS NULL OR cust.FirstName LIKE ? OR cust.LastName LIKE ? OR cust.EmailAddress LIKE ?)"
			+ " AND (? IS NULL OR ts.Name = ?)";
	
	public static final String GET_TICKETS_RESPONSE = "SELECT Text, u.EmailAddress AS ResponsedUserMail, ResponsedDate, TicketId FROM TicketResponse tr"
			+ " LEFT JOIN User u ON tr.ResponsedUserId = u.UserId"
			+ " WHERE (? IS NULL OR TicketId = ?)"
			+ " ORDER BY tr.TicketId ASC, ResponsedDate DESC";

	public static final String GET_AGENT_FOR_TICKET = "SELECT UserId FROM (" + 
			"    SELECT u.UserId, COUNT(t.TicketId) AS TicketsCount, SUM(CASE WHEN ts.Name != 'CLOSED' THEN 1 ELSE 0 END) AS ActiveJobsCount" + 
			"    FROM User u" + 
			"    LEFT JOIN Ticket t on t.AssignedAgentUserId = u.UserId" + 
			"    LEFT JOIN TicketStatus ts ON ts.TicketStatusId = t.StatusId" + 
			"    WHERE u.IsAgent = true" + 
			"    GROUP BY u.UserId) t" + 
			" ORDER BY TicketsCount, ActiveJobsCount ASC" + 
			" LIMIT 1";
	
	public static final String INSERT_TICKET = "INSERT INTO Ticket"
			+ " (Title, Description, StatusId, PriorityId, CustomerUserId, AssignedAgentUserId, CreatedUserId, CreatedDate)"
			+ " VALUES"
			+ " (?,"
			+ " ?,"
			+ " (SELECT TicketStatusId FROM TicketStatus WHERE Name = ?),"
			+ " (SELECT TicketPriorityId FROM TicketPriority WHERE Name = ?),"
			+ " (SELECT UserId FROM User WHERE EmailAddress = ?),"
			+ " (" + GET_AGENT_FOR_TICKET + "),"
			+ " (SELECT UserId FROM User WHERE EmailAddress = ?),"
			+ " CURRENT_TIMESTAMP())";
	
	public static final String UPDATE_TICKET = "UPDATE Ticket"
			+ " SET"
			+ " Title = ?,"
			+ " Description = ?,"
			+ " StatusId = (SELECT TicketStatusId FROM TicketStatus WHERE Name = ?),"
			+ " PriorityId = (SELECT TicketPriorityId FROM TicketPriority WHERE Name = ?),"
			+ " CustomerUserId = (SELECT UserId FROM User WHERE EmailAddress = ?),"
			+ " UpdatedUserId = (SELECT UserId FROM User WHERE EmailAddress = ?),"
			+ " UpdatedDate = CURRENT_TIMESTAMP()"
			+ " WHERE TicketId = ?";

	public static final String DELETE_TICKET = "DELETE FROM Ticket"
			+ " WHERE TicketId = ?";
	
	public static final String DELETE_RESPONSE = "DELETE FROM TicketResponse"
			+ " WHERE TicketId = ?";
	
	public static final String DELETE_STATUS_LOGS = "DELETE FROM TicketStatusAuditLog"
			+ " WHERE TicketId = ?";

	public static final String INSERT_TICKET_RESPONSE = "INSERT INTO TicketResponse"
			+ " (TicketId, Text, ResponsedUserId, ResponsedDate)"
			+ " VALUES"
			+ " (?,"
			+ " ?,"
			+ " (SELECT UserId FROM User WHERE EmailAddress = ?),"
			+ " CURRENT_TIMESTAMP())";
	
	public static final String GET_USER_BY_ID = "SELECT * FROM User"
			+ " WHERE EmailAddress = ?";
	
	public static final String UPDATE_STATUS_SCHEDULED = "UPDATE Ticket t"
			+ " JOIN ("
			+ " SELECT t.TicketId, MAX(ActionDate) AS LastResolved FROM Ticket t"
			+ " JOIN TicketStatusAuditLog log ON t.TicketId = log.TicketId"
			+ " WHERE"
			+ " t.StatusId = (SELECT TicketStatusId FROM TicketStatus WHERE Name = \"RESOLVED\")"
			+ " AND log.ToStatusId = (SELECT TicketStatusId FROM TicketStatus WHERE Name = \"RESOLVED\")"
			+ " GROUP BY TicketId"
			+ " ORDER BY ActionDate DESC) l ON t.TicketId = l.TicketId"
			+ " SET"
			+ " StatusId = (SELECT TicketStatusId FROM TicketStatus WHERE Name = \"CLOSED\"),"
			+ " UpdatedUserId = (SELECT UserId FROM User WHERE EmailAddress = \"support@ticketing.com\"),"
			+ " UpdatedDate = CURRENT_TIMESTAMP"
			+ " WHERE"
			+ " DATEDIFF(CURRENT_TIMESTAMP, LastResolved) >= 30";
	
}
