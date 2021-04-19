
DROP TABLE IF EXISTS ticketpriority;
CREATE TABLE IF NOT EXISTS ticketpriority (
  TicketPriorityId int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(25) NOT NULL,
  PRIMARY KEY (TicketPriorityId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
