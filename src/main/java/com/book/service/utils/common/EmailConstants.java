package com.book.service.utils.common;

public class EmailConstants {
	
	public static final String BASIC_URL = "www.mybooklist.site";
	
	public static final String SIGNIN_URL = "www.mybooklist.site/sign-in";
	
	public static final String ACTIVATION_URL = "www.mybooklist.site/activate-account";
	
	public static final String ACTIVATION_SUBJECT = "[MyBookList] Account Activation is Needed!";
	
	/* FORMATTING STIRNG
	 * 1-> Username
	 * 2-> Link of activation account
	 * 3-> Activation code
	 */
	public static final String ACTIVATION_TEXT = "Hello %s, \n\n" +
												 "Welcome to " + BASIC_URL + " \n\n" +
												 "To finish activating your account, you must click this link: %s \n\n" +
												 "If the link does not work, you can enter the code that appears below in this link: " + ACTIVATION_URL + "\n\n" +
												 "Activation Code: %s \n\n" +
												 "Thank you, " + BASIC_URL;
	
	
	
	public static final String REGISTER_USER_SUBJECT = "[MyBookList] Account Registration Complete!";

	/* FORMATTING STIRNG
	 * 1- Username
	 */
	public static final String REGISTER_USER_TEXT = "Hello %s, \n\n" +
			 								   		"Your account has been successfully activated \n\n" +
			 								   		"Now, you can sign in here: " + SIGNIN_URL + "\n\n" + 
			 								   		"Thank you, " + BASIC_URL;
	
	/* FORMATTING STRING
	 *  1- Complete Name and Surname
	 */
	public static final String REGISTER_SPECIAL_ACCOUNT_SUBJECT = "[MyBookList] %s, Your account is ready!!";

	/* FORMATTING STIRNG
	 * 1- Complete Name and Surname
	 * 2- Username
	 * 3- Password
	 */
	public static final String REGISTER_SPECIAL_ACCOUNT_TEXT = "Hello %s, \n\n" +
			 								   					"Your account has been successfully created \n\n" +
			 								   					"Your login credentials are: \n" +
			 								   					"  -Username: %s\n" +
			 								   					"  -Password: %s\n\n" +
			 								   					"Please, change your password, and fill in as much personal information as you can!\n\n" +
			 								   					"Now, you can sign in here: " + SIGNIN_URL + "\n\n" + 
			 								   					"Thank you, " + BASIC_URL;
	
	

	public static final String FRIEND_REQUEST_SUBJECT = "[MyBookList] You have a new friend request!";

	/* FORMATTING STIRNG
	 * 1- Username: friend request recipient
	 * 2- Username: friend request sender
	 */
	public static final String FRIEND_REQUEST_TEXT = "Hello %s, \n\n" +
			 								   		 "You can access your profile from" + BASIC_URL + " \n\n" +
			 								   		 "From your profile you can accept or reject %s's request \n\n" + 
			 								   		 "you can access your profile from here: " + BASIC_URL + "/me \n\n" +
			 								   		 "Thank you, " + BASIC_URL;

}


