package com.dev.ticketing.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dev.ticketing.system.model.enumtype.TicketPriority;
import com.dev.ticketing.system.model.enumtype.TicketStatus;

public class TicketModel implements Serializable{

	private static final long serialVersionUID = -7788619177798333712L;
	
	private int id;
	private String title;
	private String description;
	private TicketStatus status;
	private TicketPriority priority;
	private String customerMail;
	private int assignedAgentUserId;
	private String createdUser;
	private Date createdDate;
	private String updatedUser;
	private Date updatedDate;
	private List<TicketResponseModel> responses;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	
	public TicketPriority getPriority() {
		return priority;
	}
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}
	
	public String getCustomerMail() {
		return customerMail;
	}
	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}
	
	public int getAssignedAgentUserId() {
		return assignedAgentUserId;
	}
	public void setAssignedAgentUserId(int assignedAgentUserId) {
		this.assignedAgentUserId = assignedAgentUserId;
	}
	
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public List<TicketResponseModel> getResponses() {
		return responses;
	}
	public void setResponses(List<TicketResponseModel> responses) {
		this.responses = responses;
	}
	
}