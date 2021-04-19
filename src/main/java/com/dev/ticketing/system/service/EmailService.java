package com.dev.ticketing.system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class EmailService {

	@Async
	public void send(String emailTo, String subject, String contentText) {
		Email from = new Email(System.getenv("SENDGRID_FROM_EMAIL"));
		Email to = new Email(emailTo);
		Content content = new Content("text/plain", contentText);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println("--- Email Send Request's Response ---");
			System.out.println("Request - " + new ObjectMapper().writeValueAsString(request));
			System.out.println("Response - " + new ObjectMapper().writeValueAsString(response));
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}

}