package com.dev.ticketing.system.business.ticketassignment;

import com.dev.ticketing.system.model.UserModel;

public class AgentQueue {

	private AgentNode head;
	private AgentNode tail;

	int size;

	public boolean isEmpty() {
		return (head == null);
	}

	public AgentNode addNewAgent(UserModel agent) {
		AgentWorkerModel data = new AgentWorkerModel();
		data.setAgent(agent);
		data.setTicketAssignedCounter(0);

		AgentNode newNode = new AgentNode();
		newNode.data = data;
		newNode.next = head;
		newNode.prev = null;
		if (head != null)
			head.prev = newNode;
		head = newNode;
		if (tail == null)
			tail = newNode;
		size++;

		printLinkedListForward();

		return newNode;
	}

	public String assignTicket() {
		AgentNode temp = head;

		if (head == null) {
			return null;
		}

		int currTicketCount = temp.data.getTicketAssignedCounter() + 1;
		temp.data.setTicketAssignedCounter(currTicketCount);

		if (currTicketCount >= temp.next.data.getTicketAssignedCounter()) {
			head = temp.next;
			head.prev = null;
			tail.next = temp;
			temp.prev = tail;
			tail = temp;
			tail.next = null;
		}

		printLinkedListForward();

		return temp.data.getAgent().getEmailAddress();
	}

	public void ticketCompletedUpdate(AgentNode curr) {
		AgentNode temp = curr;

		if (head == null) {
			return;
		}

		int currTicketCount = curr.data.getTicketAssignedCounter() - 1;
		curr.data.setTicketAssignedCounter(currTicketCount);

		if(temp.prev != null && temp.prev.data.getTicketAssignedCounter() != curr.data.getTicketAssignedCounter()) {
			
			AgentNode insertAfterTargetNode = null;
			while (temp.prev != null) {
				temp = temp.prev;
	
				if (temp.data.getTicketAssignedCounter() <= curr.data.getTicketAssignedCounter()) {
					insertAfterTargetNode = temp;
					break;
				}
			}
				
			if (curr.next == null)
				curr.prev.next = null;
			else
				curr.next.prev = curr.prev;
			
			if (curr.prev == null)
				curr.next.prev = null;
			else
				curr.prev.next = curr.next;
	
			if (insertAfterTargetNode != null) {				
				AgentNode currNextNode = insertAfterTargetNode.next;
				insertAfterTargetNode.next = curr;
				curr.prev = insertAfterTargetNode;
				curr.next = currNextNode;
			} else {
				head.prev = curr;
				curr.next = head;
				head = curr;
				head.prev = null;
			}
	
		}
		
		printLinkedListForward();

	}

	// For printing Doubly Linked List forward
	public void printLinkedListForward() {
		System.out.println("Printing Doubly LinkedList (head --> tail) ");
		AgentNode current = head;
		while (current != null) {
			current.displayNodeData();
			current = current.next;
		}
		System.out.println();
	}

	// For printing Doubly Linked List forward public void
	public void printLinkedListBackward() {
		System.out.println("Printing Doubly LinkedList (tail --> head) ");
		AgentNode current = tail;
		while (current != null) {
			current.displayNodeData();
			current = current.prev;
		}
		System.out.println();
	}

//    PriorityBlockingQueue<AgentWorkerModel> agentQueue = new PriorityBlockingQueue<AgentWorkerModel>(15, Comparator.comparing(AgentWorkerModel::getTicketAssignedCounter)); 
//    public AgentWorkerModel addNewAgent(UserModel agent) {
//    	AgentWorkerModel data = new AgentWorkerModel();
//    	data.setAgent(agent);
//    	data.setTicketAssignedCounter(0);
//        agentQueue.add(data);
//        //printQueue();
//        return data;
//    }
// 
//    public String assignTicket() {
//    	if(agentQueue.peek() != null) {
//    		AgentWorkerModel currentAgent = agentQueue.poll();
//    		int currTicketCount = currentAgent.getTicketAssignedCounter() + 1;
//    		currentAgent.setTicketAssignedCounter(currTicketCount);
//    		agentQueue.add(currentAgent);
//    		//printQueue();
//    		return currentAgent.getAgent().getEmailAddress();
//    	}
//        return null;
//    }
// 
//    public void ticketCompletedUpdate(AgentWorkerModel curr) {
//    	agentQueue.remove(curr);
//    	curr.setTicketAssignedCounter(curr.getTicketAssignedCounter() - 1);
//    	agentQueue.add(curr);
//    	//printQueue();
//    }
// 
//    public void printQueue() {
//        System.out.println("Printing Queue ");
//        PriorityBlockingQueue<AgentWorkerModel> agentQueueToPrint = agentQueue;
//        while (!agentQueueToPrint.isEmpty()) {
//        	AgentWorkerModel model = agentQueueToPrint.poll();
//        	System.out.println("{ " + model.getAgent().getEmailAddress() + " - " + model.getTicketAssignedCounter() + " } ");
//    	}
//        System.out.println();
//    }
}