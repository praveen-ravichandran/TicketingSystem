package com.dev.ticketing.system.service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dev.ticketing.system.controller.TicketingApiController;
import com.dev.ticketing.system.model.enumtype.TicketStatus;

@EnableScheduling
public class StatusUpdateScheduler {

	@Scheduled(cron = "0 0 12 * * *")
    public void statusUpdater()
    {
        System.out.println("statusUpdater execution start. Current time is :: "+ new Date());
        if (TicketingApiController.ticketsTable.size() > 0) {
        	TicketingApiController.ticketsTable
        		.entrySet()
        		.stream()
        		.filter(ticket -> ticket.getValue().getStatus() == TicketStatus.RESOLVED && Objects.nonNull(ticket.getValue().getUpdatedDate())
        			&& getDifferenceDays(ticket.getValue().getUpdatedDate(), new Date()) >= 30)
        		.forEach(ct -> ct.getValue().setStatus(TicketStatus.CLOSED));
        }
        System.out.println("statusUpdater execution end. Current time is :: "+ new Date());
    }
	
	private long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
}
