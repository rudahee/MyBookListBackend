package com.book.service.utils.common;

public class EmailConstants {
	
	
	public static final String ACTIVATION_URL = "www.mybooklist.com/activate-account";
	
	public static final String ACTIVATION_SUBJECT = "[MyBookList.com] Account Activation is Needed!";
	
	/* FORMATTING STIRNG
	 * 1-> username
	 * 2-> link of activation account
	 * 3-> activation code
	 */
	public static final String ACTIVATION_TEXT = "Hello %s, \n\n" +
												 "Welcome to MyBookList.com \n\n" +
												 "To finish activating your account, you must click this link %s \n\n" +
												 "If the link does not work, you can enter the code that appears below in this link: " + ACTIVATION_URL + "\n\n" +
												 "Activation Code: %s \n\n" +
												 "Thank you, MyBookList.com";
	
	
	
	public static final String REGISTER_SUBJECT = "[MyBookList.com] Account Registration Complete!";

	/*
	 * 1- username
	 */
	public static final String REGISTER_TEXT = "Hello %s, \n\n" +
			 "Your account has been successfully activated \n\n" +
			 "Now, you can sign in here: www.mybooklist.com/sign-in \n\n" + 
			 "Thank you, MyBookList.com";
}
