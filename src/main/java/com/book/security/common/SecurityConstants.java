package com.book.security.common;

/* This class is to represent a list of Spring Security related constants.
 * 
 * @author J. Rubén Daza
 */
public class SecurityConstants {
	public static final String SECRET = "kYp3s6v9y$B&E)H@McQeThWmZq4t7w!z%C*F-JaNdRgUjXn2r5u8x/A?D(G+KbPe"; 
	public static final long EXPIRATION_TIME = 604_000_000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String SIGN_UP_URL = "/auth/sign-up";
	public static final String SIGN_IN = "/auth/sign-in";
}
