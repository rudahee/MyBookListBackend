package com.book.controller;

import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.controller.abstracts.BaseController;
import com.book.model.dto.UserDTO;
import com.book.model.entity.User;
import com.book.model.enumerated.UserRole;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.UserService;
import com.book.service.utils.EmailServiceImpl;

@RestController
@RequestMapping(path = "/user")
public class userController extends BaseController<User, UserDTO, UserService>{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUpUser(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
				
		try {
			if (super.checker.checkRegister(user)) {
				String activationCode = UUID.randomUUID().toString().substring(0, 8);
				user.setActivationCode(activationCode);
				user.setEnableAccount(false);
				user.setRoles(Set.of(UserRole.USER));
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				user = super.service.save(user);

				response = ResponseEntity.status(HttpStatus.OK).body("Correcto");				
			}
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
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
			
			if (super.checker.checkActivationCode(dataReceived)) {
				
				user = super.service.findById(dataReceived.getId());
				
				if (user.getActivationCode().equals(dataReceived.getActivationCode())) {
				
					user.setActivationCode(null);
					user.setEnableAccount(true);
					
					user = super.service.edit(user);
				}
			}
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PostMapping("/sign-up/author")
	public ResponseEntity<?> signUpAuthor(UserDTO user) {
		return null;
	}
	
	@PostMapping("/sign-up/admin")
	public ResponseEntity<?> signUpAdmin(UserDTO user) {
		return null;
	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		try {
			if (super.checker.checkLogin(user)) {
				response = ResponseEntity.status(HttpStatus.OK).body("Done");
			}
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		
		return response;
	}


	@PutMapping("/me")
	public ResponseEntity<?> edit(UserDTO user) {
		return null;
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> get(HttpServletRequest request) {
		Long id = JWTTokenProvider.getIdFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length()));
		
		return ResponseEntity.status(HttpStatus.OK).body(service.loadUserById(id));
	}

	@DeleteMapping("/me")
	public ResponseEntity<?> delete(UserDTO user) {
		return null;
	}

}
