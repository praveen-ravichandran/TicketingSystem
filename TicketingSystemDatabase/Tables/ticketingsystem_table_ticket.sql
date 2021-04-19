
DROP TABLE IF EXISTS ticket;
CREATE TABLE IF NOT EXISTS ticket (
  TicketId int(11) NOT NULL AUTO_INCREMENT,
  Title varchar(1024) NOT NULL,
  Description varchar(10240) NOT NULL,
  StatusId int(11) NOT NULL,
  PriorityId int(11) NOT NULL,
  CustomerUserId int(11) NOT NULL,
  AssignedAgentUserId int(11) DEFAULT NULL,
  CreatedUserId int(11) NOT NULL,
  CreatedDate datetime NOT NULL,
  UpdatedUserId int(11) DEFAULT NULL,
  UpdatedDate datetime DEFAULT NULL,
  PRIMARY KEY (TicketId),
  KEY FK_Ticket_CustomerUserId_User_UserId (CustomerUserId),
  KEY FK_Ticket_AssignedAgentUserId_User_UserId (AssignedAgentUserId),
  KEY FK_Ticket_CreatedUserId_User_UserId (CreatedUserId),
  KEY FK_Ticket_UpdatedUserId_User_UserId (UpdatedUserId),
  KEY FK_Ticket_PriorityId_TicketPriority_TicketPriorityId (PriorityId),
  KEY FK_Ticket_StatusId_TicketStatus_TicketStatusId (StatusId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
DROP TRIGGER IF EXISTS `Trigger_Ticket_Status_Insert`;
DELIMITER $$
CREATE TRIGGER `Trigger_Ticket_Status_Insert` AFTER INSERT ON `ticket` FOR EACH ROW BEGIN
      INSERT INTO TicketStatusAuditLog (TicketId, FromStatusId, ToStatusId, ActionUserId, ActionDate)
      VALUES (NEW.TicketId, NULL, NEW.StatusId, NEW.CreatedUserId, NEW.CreatedDate);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `Trigger_Ticket_Status_Update`;
DELIMITER $$
CREATE TRIGGER `Trigger_Ticket_Status_Update` AFTER UPDATE ON `ticket` FOR EACH ROW BEGIN
   IF !(NEW.StatusId <=> OLD.StatusId) THEN
      INSERT INTO TicketStatusAuditLog (TicketId, FromStatusId, ToStatusId, ActionUserId, ActionDate)
      VALUES (OLD.TicketId, OLD.StatusId, NEW.StatusId, NEW.UpdatedUserId, NEW.UpdatedDate);
   END IF;
END
$$
DELIMITER ;
