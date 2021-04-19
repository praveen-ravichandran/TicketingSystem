
DROP TABLE IF EXISTS ticketstatus;
CREATE TABLE IF NOT EXISTS ticketstatus (
  TicketStatusId int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(25) NOT NULL,
  PRIMARY KEY (TicketStatusId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
