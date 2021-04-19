

ALTER TABLE ticket
  ADD CONSTRAINT FK_Ticket_AssignedAgentUserId_User_UserId FOREIGN KEY (AssignedAgentUserId) REFERENCES `user` (UserId),
  ADD CONSTRAINT FK_Ticket_CreatedUserId_User_UserId FOREIGN KEY (CreatedUserId) REFERENCES `user` (UserId),
  ADD CONSTRAINT FK_Ticket_CustomerUserId_User_UserId FOREIGN KEY (CustomerUserId) REFERENCES `user` (UserId),
  ADD CONSTRAINT FK_Ticket_PriorityId_TicketPriority_TicketPriorityId FOREIGN KEY (PriorityId) REFERENCES ticketpriority (TicketPriorityId),
  ADD CONSTRAINT FK_Ticket_StatusId_TicketStatus_TicketStatusId FOREIGN KEY (StatusId) REFERENCES ticketstatus (TicketStatusId),
  ADD CONSTRAINT FK_Ticket_UpdatedUserId_User_UserId FOREIGN KEY (UpdatedUserId) REFERENCES `user` (UserId);

ALTER TABLE ticketresponse
  ADD CONSTRAINT FK_TicketResponse_RespondedUserId_User_UserId FOREIGN KEY (ResponsedUserId) REFERENCES `user` (UserId),
  ADD CONSTRAINT FK_TicketResponse_TicketId_Ticket_TicketId FOREIGN KEY (TicketId) REFERENCES ticket (TicketId);

ALTER TABLE ticketstatusauditlog
  ADD CONSTRAINT FK_TicketStatusAuditLog_FromStatusId_Status_StatusId FOREIGN KEY (FromStatusId) REFERENCES ticketstatus (TicketStatusId),
  ADD CONSTRAINT FK_TicketStatusAuditLog_TicketId_Ticket_TicketId FOREIGN KEY (TicketId) REFERENCES ticket (TicketId),
  ADD CONSTRAINT FK_TicketStatusAuditLog_ToStatusId_Status_StatusId FOREIGN KEY (ToStatusId) REFERENCES ticketstatus (TicketStatusId);
