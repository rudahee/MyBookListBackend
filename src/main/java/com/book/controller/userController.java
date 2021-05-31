package com.book.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.UserManagementException;
import com.book.model.dto.UserDTO;
import com.book.model.enumerated.UserRole;
import com.book.security.common.SecurityConstants;
import com.book.security.error.BodyErrorCode;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.UserService;
import com.book.service.converters.UserConverter;
import com.book.service.utils.Checker;
import com.book.service.utils.EmailServiceImpl;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUpUser(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		
		try {
			if (checker.checkRegister(user)) {
				user = service.signUpUser(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);				
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			emailService.sendActivationMail(user.getId(), user.getUsername(), user.getEmail(), user.getActivationCode());
		}
		
		return response;
	}
	
	@PutMapping("/activate")
	public ResponseEntity<?> activateAccount(@RequestBody UserDTO dataReceived) {
		UserDTO user = null;
		
		try {
			
			if (checker.checkActivationCode(dataReceived)) {
				
				
				if (!service.existsById(dataReceived.getId())) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.ACTIVATION_CODE_INVALID);
				} else {
					user = service.findById(dataReceived.getId());					
				}
				
				if (user.getActivationCode().equals(dataReceived.getActivationCode()) && !user.getEnableAccount()) {
					user.setActivationCode(null);
					user.setEnableAccount(true);
					
					user = service.edit(user);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BodyErrorCode.ACTIVATION_CODE_INVALID);
				}
			}
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
			
		} catch (UserManagementException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
	}
	
	@PostMapping("/sign-up/author")
	public ResponseEntity<?> signUpAuthor(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		
		try {
			if (checker.checkRegisterSpecialAccount(user)) {
				user = service.signUpAuthor(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);				
				emailService.sendSignUpComplete(user.getUsername(), user.getEmail());
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}		
		return response;
	}
	
	@PostMapping("/sign-up/admin")
	public ResponseEntity<?> signUpAdmin(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		
		try {
			if (checker.checkRegisterSpecialAccount(user)) {
				user = service.signUpAdmin(user);
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
				emailService.sendSignUpComplete(user.getUsername(), user.getEmail());
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
		
		return response;
	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		try {
			if (checker.checkLogin(user)) {
				response = ResponseEntity.status(HttpStatus.OK).body(BodyErrorCode.NO_ERROR);
			}
		} catch (UserManagementException ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}		
		return response;
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> get(HttpServletRequest request) {
		Long id = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
			);
		
		return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPublicUser(@PathVariable Long id) {
		try {
			UserDTO dto = this.service.findById(id);			

			if (dto.getRoles().contains(UserRole.USER)) {
				dto.setActivationCode(null);
				dto.setEnableAccount(null);
				dto.setLastPasswordChange(null);
				dto.setNextPasswordChange(null);
				dto.setPassword(null);
				
				return ResponseEntity.status(HttpStatus.OK).body(dto);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyErrorCode.INVALID_PUBLIC_USER);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
}
