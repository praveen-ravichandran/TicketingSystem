
DROP TABLE IF EXISTS ticketresponse;
CREATE TABLE IF NOT EXISTS ticketresponse (
  ResponseId int(11) NOT NULL AUTO_INCREMENT,
  TicketId int(11) NOT NULL,
  Text varchar(10240) NOT NULL,
  ResponsedUserId int(11) NOT NULL,
  ResponsedDate datetime NOT NULL,
  PRIMARY KEY (ResponseId),
  KEY FK_TicketResponse_TicketId_Ticket_TicketId (TicketId),
  KEY FK_TicketResponse_RespondedUserId_User_UserId (ResponsedUserId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
