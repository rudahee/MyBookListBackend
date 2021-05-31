package com.book.service.utils;

import org.springframework.stereotype.Service;

import com.book.exception.BookManagementException;
import com.book.exception.UserManagementException;
import com.book.model.dto.BookDTO;
import com.book.model.dto.UserDTO;
import com.book.security.error.BodyErrorCode;

@Service
public class Checker {

	
	public Boolean checkRegister(UserDTO user) throws UserManagementException {
		
		if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()
				|| user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
		public Boolean checkRegisterSpecialAccount(UserDTO user) throws UserManagementException {
		
		if (user.getUsername().isBlank() || user.getEmail().isBlank()
				|| user.getUsername() == null || user.getEmail() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
	
	
	
	public Boolean checkLogin(UserDTO user) throws UserManagementException {
		if (user.getUsername().isBlank() || user.getPassword().isBlank()
				|| user.getUsername() == null || user.getPassword() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
	
	public Boolean checkActivationCode(UserDTO user) throws UserManagementException {
		if (user.getActivationCode().isBlank() || user.getActivationCode() == null || user.getActivationCode().length() != 8) {
			throw new UserManagementException(BodyErrorCode.ACTIVATION_CODE_MALFORMED);
		}
		
		return true;
	}
	
	public Boolean checkBook(BookDTO book) throws BookManagementException {
		
		if (book.getImageUrl().isBlank() 
			|| book.getImageUrl() == null
			|| book.getIsbn().length() < 10
			|| book.getName().isBlank() || book.getName() == null) {
			
			throw new BookManagementException(BodyErrorCode.FIELD_IS_MISSING);
		}
		
		return true;
	}
}
