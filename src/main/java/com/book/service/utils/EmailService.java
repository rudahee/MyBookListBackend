package com.book.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.book.service.utils.common.EmailConstants;

/* This class send emails, i used JavaMailSender, I recommend that you see the documentation for that class. 
 * 
 * @author J. Rubén Daza
 *
 * @see JavaMailSender
 */
@Service
public class EmailService {

	@Autowired
    private JavaMailSender emailSender;
	
	/* This method prepares everything you need to send an email by activation user
	 * 
	 * @param id Long
	 * @param username String
	 * @param activationCode String
	 */
	public void sendActivationMail(Long id, String username, String email, String activationCode) {
		String text = String.format(EmailConstants.ACTIVATION_TEXT, username, EmailConstants.ACTIVATION_URL + "/" + id.toString() + "/" + activationCode, activationCode+"/"+id.toString());

		this.sendMessage(email, EmailConstants.ACTIVATION_SUBJECT, text);
	}
	
	/* This method prepares everything you need to send an email by signup complete
	 * 
	 * @param username String
	 * @param email String
	 */
	public void sendSignUpComplete(String username, String email) {
		String text = String.format(EmailConstants.REGISTER_USER_TEXT, username);
		this.sendMessage(email, EmailConstants.REGISTER_USER_SUBJECT, text);
	}

	/* This method prepares everything you need to send an email by signup complete (for special account)
	 * 
	 * @param username String
	 * @param password String
	 * @param completeNameAndSurname String
	 * @param email String
	 */
	public void sendSignUpComplete(String username, String password, String completeNameAndSurname, String email) {
		String text = String.format(EmailConstants.REGISTER_SPECIAL_ACCOUNT_TEXT, completeNameAndSurname, username, password);
		String subject = String.format(EmailConstants.REGISTER_USER_SUBJECT, completeNameAndSurname);
		this.sendMessage(email, subject , text);
	}
	
	/* This method prepares everything you need to send an email by friend request
	 * 
	 * @param senderUsername String
	 * @param receiptUsername String
	 * @param email String
	 */
	public void sendFriendRequestReminder(String senderUsername, String receiptUsername, String email) {
		String text = String.format(EmailConstants.FRIEND_REQUEST_TEXT, receiptUsername, senderUsername);
		this.sendMessage(email, EmailConstants.FRIEND_REQUEST_SUBJECT, text);
	}
	
	/* This method send messages from emailSender
	 * 
	 * @param to string
	 * @param subject String
	 * @param text String
	 */
	private void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("info@mybooklist.site");
		message.setTo(to);
		message.setSubject(subject); 
	    message.setText(text);
	    emailSender.send(message);
	}
 
}