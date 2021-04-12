package com.book.service.utils;

import org.springframework.stereotype.Service;

import com.book.model.dto.UserDTO;

@Service
public class Checker {

	
	public Boolean checkRegister(UserDTO user) throws Exception {
		
		if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()
				|| user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
						
			throw new Exception("Bad Register");
		} 
		
		return true;
	}
	
	public Boolean checkLogin(UserDTO user) throws Exception {
		
		if (user.getUsername().isBlank() || user.getPassword().isBlank()
				|| user.getUsername() == null || user.getPassword() == null) {
						
			throw new Exception("Bad Login");
		} 
		
		return true;
	}
	
	public Boolean checkActivationCode(UserDTO user) throws Exception {
		if (user.getActivationCode().isBlank() || user.getActivationCode() == null || user.getActivationCode().length() != 8) {
			throw new Exception("Activation code not valid");
		}
		
		return true;
	}
}
