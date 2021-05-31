package com.book.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.book.exception.JwtException;
import com.book.model.entity.User;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
@WebFilter
public class OncePerRequestFilterImpl extends OncePerRequestFilter {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication;
        
        try {
        	authentication = getAuthentication(request);        	
        	authentication.setDetails(new WebAuthenticationDetails(request));
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
         	logger.info("OncePerRequest - JWT: " + e.getCode());
   		}
        
        
        chain.doFilter(request, response);
	}
	
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws JwtException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER).replace(SecurityConstants.TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken upat = null;
        
        if (token != null && !token.isEmpty() && JWTTokenProvider.validateToken(token)) {
        	try {
        		Long idUser = Long.valueOf(Jwts.parser().
        									setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes()))
        									.parseClaimsJws(token)
        									.getBody()
        									.getId());
        	
				User user = (User) userService.loadUserById(idUser);
				if (idUser != null) {
					upat = new UsernamePasswordAuthenticationToken(idUser, user.getRoles(), user.getAuthorities());
				}
			} catch (AuthenticationException e) {
				throw new RuntimeException("No user identifier has been found in the request");
			} 
        }
        return upat;	
    }

}