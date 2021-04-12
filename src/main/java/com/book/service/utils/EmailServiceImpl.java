package com.book.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.book.service.utils.common.EmailConstants;

@Service
public class EmailServiceImpl {

	@Autowired
    private JavaMailSender emailSender;
	
	public void sendActivationMail(Long id, String username, String email,String activationCode) {
		String text = String.format(EmailConstants.ACTIVATION_TEXT, username, EmailConstants.ACTIVATION_URL + "/" + id.toString() + "/" + activationCode, activationCode+"/"+id.toString());

		this.sendMessage(email, EmailConstants.ACTIVATION_SUBJECT, text);
	}
	
	public void sendSignUpComplete(String username, String email) {
		String text = String.format(EmailConstants.REGISTER_TEXT, username);
		
		this.sendMessage(email, EmailConstants.REGISTER_SUBJECT, text);
	}
	
	
	private void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@mybooklist.com");
		message.setTo(to);
		message.setSubject(subject); 
	    message.setText(text);
	    emailSender.send(message);
	}
 
}