
DROP TABLE IF EXISTS ticketstatusauditlog;
CREATE TABLE IF NOT EXISTS ticketstatusauditlog (
  TicketStatusAuditLogId int(11) NOT NULL AUTO_INCREMENT,
  TicketId int(11) NOT NULL,
  FromStatusId int(11) DEFAULT NULL,
  ToStatusId int(11) DEFAULT NULL,
  ActionUserId int(11) DEFAULT NULL,
  ActionDate datetime NOT NULL,
  PRIMARY KEY (TicketStatusAuditLogId),
  KEY FK_TicketStatusAuditLog_TicketId_Ticket_TicketId (TicketId),
  KEY FK_TicketStatusAuditLog_FromStatusId_Status_StatusId (FromStatusId),
  KEY FK_TicketStatusAuditLog_ToStatusId_Status_StatusId (ToStatusId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
