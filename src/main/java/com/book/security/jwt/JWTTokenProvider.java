package com.book.security.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.book.exception.JwtException;
import com.book.model.entity.User;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.enumerated.UserRole;
import com.book.security.common.SecurityConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JWTTokenProvider {

	private static SecretKey key; 
	
	public static String generateToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
        return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE) 
				.setSubject(user.getEmail().toString())  													// User Email Header in Token
				.setId(user.getId().toString())																// Id user header in token
				.setIssuedAt(new Date(System.currentTimeMillis())) 											//Date and Time Creation Token
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)) 	//Date and Time Expiration Token
				.claim("roles", user.getRoles().stream().map(UserRole::name).collect(Collectors.joining(", ")))
				.signWith(getKey(), SignatureAlgorithm.HS512)
				.compact();
	}
	
	public static boolean validateToken(String token) throws JwtException {
		boolean valid = false;
		
		try {
			Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
			valid = true;
		} catch (SignatureException ex) {
			throw new JwtException(BodyErrorCode.TOKEN_NOT_VALID);
		} catch (MalformedJwtException ex) {
			throw new JwtException(BodyErrorCode.TOKEN_NOT_VALID);
		} catch (ExpiredJwtException ex) {
			throw new JwtException(BodyErrorCode.TOKEN_EXPIRED);
		} catch (UnsupportedJwtException ex) {
			throw new JwtException(BodyErrorCode.TOKEN_NOT_VALID);
		} catch (IllegalArgumentException ex) {
			throw new JwtException(BodyErrorCode.TOKEN_NOT_VALID);
		}
		
		return valid;
	}
	
	public static Long getIdFromToken(String token) {
		return Long.valueOf((String) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("jti"));
	}
	
	private static  SecretKey getKey() {
		if(key == null) {
			key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
		}
		return key;
	}
}
 