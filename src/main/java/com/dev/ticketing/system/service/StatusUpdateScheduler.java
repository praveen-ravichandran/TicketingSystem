package com.dev.ticketing.system.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dev.ticketing.system.business.TicketBusiness;

@EnableScheduling
public class StatusUpdateScheduler {

	@Autowired
	TicketBusiness ticketBusiness;

	@Scheduled(cron = "0 0 0 * * *")
	public void statusUpdater() {
		System.out.println("statusUpdater execution start. Current time is :: " + new Date());
		ticketBusiness.updateTicketStatusScheduled();
		System.out.println("statusUpdater execution end. Current time is :: " + new Date());
	}

}
