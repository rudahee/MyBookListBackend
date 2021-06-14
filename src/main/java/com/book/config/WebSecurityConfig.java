package com.book.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.book.security.OncePerRequestFilterImpl;
import com.book.security.UsernamePasswordAuthenticationFilterImpl;
import com.book.security.error.CustomSecurityEntryPoint;
import com.book.service.entity.users.UserService;

/*
 * Spring Security main configuration class. 
 * 
 * In this class we must activate web security adding some specific annotations:
 * @Configuration (because its a config class)
 * @EnableWebSecurity to activate web security 
 * @EnableGlobalMethodSecurity(prePostEndabled = true) to activave web security
 * 
 * in this class we will set various parameters such as csrf, CORS, sessions, 
 * and filters authentication and authorization among others.
 * 
 * @author J. Rubén Daza
 * 
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private OncePerRequestFilterImpl OPRFilter;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomSecurityEntryPoint securityEntryPoint;
	

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	/* 
	 * Spring Security configuration method. we will set various parameters 
	 * such as csrf, CORS, sessions, and filters authentication and authorization among others.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()  // We disable cors and csrf because it is not necessary. 
			.exceptionHandling()
				.authenticationEntryPoint(securityEntryPoint)
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //we will work without explicit session control
			.and()
			.authorizeRequests() // Access control to endpoints by roles.
				.antMatchers(HttpMethod.PUT, "/follow/*").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/follow/*").hasRole("USER")
				
				.antMatchers(HttpMethod.GET, "/friend/*").hasRole("USER")
				.antMatchers(HttpMethod.PUT, "/friend/*").hasRole("USER")
				.antMatchers(HttpMethod.DELETE, "/friend/*").hasRole("USER")

				
				.antMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/sign-up/*").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/auth/sign-in").permitAll()
				.antMatchers(HttpMethod.PUT, "/auth/activate").permitAll()
				
				.antMatchers(HttpMethod.GET, "/author/*").permitAll()
				.antMatchers(HttpMethod.PUT, "/author/follow/*").hasRole("USER")
				.antMatchers(HttpMethod.PUT, "/author/change-personal-data").hasRole("AUTHOR")

				.antMatchers(HttpMethod.GET, "/user/*").permitAll()
				.antMatchers(HttpMethod.GET, "/user/me").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/user/recommendations").hasRole("USER")

				.antMatchers(HttpMethod.GET, "/book/*").permitAll()
				.antMatchers(HttpMethod.POST, "/book/*").hasAnyRole("AUTHOR", "ADMIN")
				.antMatchers(HttpMethod.POST, "/book/approval").authenticated()
				.antMatchers(HttpMethod.PUT, "/*").hasRole("USER")
				.antMatchers(HttpMethod.DELETE, "/*").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/saga/*").permitAll()
				.antMatchers(HttpMethod.POST, "/saga").hasAnyRole("AUTHOR", "ADMIN")
				.antMatchers(HttpMethod.DELETE, "/saga/*").hasAnyRole("AUTHOR", "ADMIN")

				.antMatchers(HttpMethod.GET, "/statistics/user/*").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/statistics/author/*").hasRole("AUTHOR")
				.antMatchers(HttpMethod.GET, "/statistics/admin").hasRole("ADMIN")
				
				.anyRequest().authenticated()
			
				.and() // We define the implementation of the authentication and authorization filter.
			.addFilter(new UsernamePasswordAuthenticationFilterImpl(authenticationManagerBean()))
			.addFilterBefore(OPRFilter, BasicAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and() // We deactivate the login generated by Spring with Thymeleaf by default.
			.formLogin().disable();

	}
	
	/*
	 * In this method we define which service will be managed by the users of the system.
	 */
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}	