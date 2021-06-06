package com.book.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.dto.users.UserDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.enumerated.UserRole;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.converters.users.UserConverter;
import com.book.service.entity.users.UserService;
import com.book.service.utils.Checker;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	protected Checker checker;

	@Autowired
	protected UserService service;
	
	@Autowired
	protected UserConverter converter;
	
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
