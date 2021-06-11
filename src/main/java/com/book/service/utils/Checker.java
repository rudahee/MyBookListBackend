package com.book.service.utils;

import org.springframework.stereotype.Service;

import com.book.exception.BookManagementException;
import com.book.exception.UserManagementException;
import com.book.model.dto.books.BookDTO;
import com.book.model.dto.users.UserDTO;
import com.book.model.enumerated.BodyErrorCode;

/* This class check if some entities are valid, if are invalid throw exception
 * 
 * @author J. Rubén Daza
 */
@Service
public class Checker {

	/* This method check user with user role
	 * 
	 * @param user UserDTO
	 * @param Boolean
	 * 
	 * @author J. Rubén Daza
	 */
	public Boolean checkRegister(UserDTO user) throws UserManagementException {
		
		if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()
				|| user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
	
	/* This method check user with author or admin role
	 * 
	 * @param user UserDTO
	 * @param Boolean
	 * 
	 * @author J. Rubén Daza
	 */
	public Boolean checkRegisterSpecialAccount(UserDTO user) throws UserManagementException {
		
		if (user.getUsername().isBlank() || user.getEmail().isBlank()
				|| user.getUsername() == null || user.getEmail() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
	
	
	/* This method check user (for login, only username and password)
	 * 
	 * @param user UserDTO
	 * @param Boolean
	 * 
	 * @author J. Rubén Daza
	 */
	public Boolean checkLogin(UserDTO user) throws UserManagementException {
		if (user.getUsername().isBlank() || user.getPassword().isBlank()
				|| user.getUsername() == null || user.getPassword() == null) {
						
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		
		return true;
	}
	
	/* This method check activation code
	 * 
	 * @param user UserDTO
	 * @param Boolean
	 * 
	 * @author J. Rubén Daza
	 */
	public Boolean checkActivationCode(UserDTO user) throws UserManagementException {
		if (user.getActivationCode().isBlank() || user.getActivationCode() == null || user.getActivationCode().length() != 8) {
			throw new UserManagementException(BodyErrorCode.ACTIVATION_CODE_MALFORMED);
		}
		
		return true;
	}
	
	/* This method check book
	 * 
	 * @param book BookDTO
	 * @param Boolean
	 * 
	 * @author J. Rubén Daza
	 */
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
