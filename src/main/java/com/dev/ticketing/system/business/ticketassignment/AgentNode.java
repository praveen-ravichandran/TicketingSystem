package com.dev.ticketing.system.business.ticketassignment;

public class AgentNode {
    public AgentWorkerModel data;
    public AgentNode next;
    public AgentNode prev;
 
    public void displayNodeData() {
        System.out.println("{ " + data.getAgent().getEmailAddress()+ " - " + data.getTicketAssignedCounter() + " } ");
    }
}