package com.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exception.UserManagementException;
import com.book.model.dto.AuthorPublicDTO;
import com.book.model.dto.SimplifiedAuthorDTO;
import com.book.model.dto.UserDTO;
import com.book.model.entity.User;
import com.book.model.entity.Customer.AuthorCustomer;
import com.book.model.entity.Customer.UserCustomer;
import com.book.model.repository.AuthorCustomerRepository;
import com.book.model.repository.UserCustomerRepository;
import com.book.model.repository.UserRepository;
import com.book.security.error.BodyErrorCode;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.AuthorPublicConverter;
import com.book.service.converters.SimplifiedAuthorConverter;
import com.book.service.converters.UserConverter;

@Service
public class AuthorService extends BaseService<User, UserDTO, UserConverter, UserRepository, Long> {
	
	@Autowired
	private AuthorPublicConverter authorPublicConverter;
	
	@Autowired
	private SimplifiedAuthorConverter simplifiedAuthorConverter;
	
	@Autowired
	private AuthorCustomerRepository authorRepository;
	
	@Autowired
	private UserCustomerRepository userRepository;
	
	public List<UserDTO> getAllAuthors() {
		List<User> users = this.repository.findAll();
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		
		for (User user: users) {
			if (user.getCustomer() instanceof AuthorCustomer) {
				dtos.add(super.DtoConverter.fromEntity(user));
			}
		}
		return dtos;
	}
	
	
	public AuthorPublicDTO getPublicAuthor(Long id) throws UserManagementException {
		Optional<User> user = this.repository.findById(id);
		
		if (user.isPresent()) {
			if (!user.get().getCustomer().getClass().isAssignableFrom(AuthorCustomer.class)) {
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
			}
			
			AuthorPublicDTO authorDTO = authorPublicConverter.fromEntity(user.get());
			return authorDTO;
		
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}
	
	public List<SimplifiedAuthorDTO> getFollowedAuthor(Long id) throws UserManagementException {
		Optional<User> user = this.repository.findById(id);
		ArrayList<SimplifiedAuthorDTO> authorsDTO = new ArrayList<SimplifiedAuthorDTO>();
		
		if (user.isPresent()) {
			if(!user.get().getCustomer().getClass().isAssignableFrom(UserCustomer.class)) {
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
			}
			
			UserCustomer customer = (UserCustomer) user.get().getCustomer();
			
			customer.getFollows().forEach(
				authorCustomer -> {
					authorsDTO.add(simplifiedAuthorConverter.fromEntity(authorCustomer.getUser()));
			});
			
			return authorsDTO;
			
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}	
	}
	
	public Boolean followAuthor(Long userId, Long authorId) throws UserManagementException {
		Optional<User> userUser = this.repository.findById(userId);
		Optional<User> userAuthor = this.repository.findById(authorId);

		if (userUser.isPresent() && userAuthor.isPresent()) {
			if(!userUser.get().getCustomer().getClass().isAssignableFrom(UserCustomer.class) 
				&& !userAuthor.get().getCustomer().getClass().isAssignableFrom(AuthorCustomer.class)) {
				
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);	
			}
			
			UserCustomer userCustomer = (UserCustomer) userUser.get().getCustomer();
			AuthorCustomer authorCustomer = (AuthorCustomer) userAuthor.get().getCustomer();

			userCustomer.addAuthorFollow(authorCustomer);
			authorCustomer.addFollower(userCustomer);
			
			this.userRepository.save(userCustomer);
			this.authorRepository.save(authorCustomer);
			
			return true;
			
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}
}
