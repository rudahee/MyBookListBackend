package com.book.service.entity.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exception.UserManagementException;
import com.book.model.dto.minimal.AuthorPersonalDataDTO;
import com.book.model.dto.users.AuthorPublicDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.dto.users.UserDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.repository.users.AuthorCustomerRepository;
import com.book.model.repository.users.UserCustomerRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.users.AuthorPublicConverter;
import com.book.service.converters.users.SimplifiedAuthorConverter;
import com.book.service.converters.users.UserConverter;

/* Implements BaseService for Author (Its User + AuthorCustomer)
 * 
 * @author J. Rubén Daza
 * 
 * @see BaseService
 */
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
	
	/* 
	 * this method get all authors.
	 *
	 * @return List<UserDTO>
	 */
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
	
	
	/* 
	 * this method get an author
	 * 
	 * @param id Long
	 *
	 * @return AuthorPublicDTO
	 */
	public AuthorPublicDTO getPublicAuthor(Long id) throws UserManagementException {
		Optional<User> user = this.repository.findById(id);
		
		if (user.isPresent()) {
			if (!user.get().getCustomer().getClass().isAssignableFrom(AuthorCustomer.class)) { // If is author
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
			}
			
			AuthorPublicDTO authorDTO = authorPublicConverter.fromEntity(user.get());
			return authorDTO;
		
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}
	
	/* 
	 * this method get all authors that a user follows.
	 * 
	 * @param id Long 
	 *
	 * @return SimplifiedAuthorDTO
	 */
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
	
	/* 
	 * this method edit an Author
	 * 
	 * @param id Long
	 * @param data AuthorPersonalDataDTO
	 */
	public void changePersonalData(Long id, AuthorPersonalDataDTO data) {
		AuthorCustomer author = (AuthorCustomer) this.repository.findById(id).get().getCustomer();
		
		author.setBiography(data.getBiography());
		author.setUrlImage(data.getUrlImage());
		
		this.authorRepository.save(author);
		
	}
	
	/* 
	 * allows following an author by changing the Author Customer parameter and UserCustomer parameter
	 * 
	 * @param userId Long
	 * @param authorId Long
	 *
	 * @return Boolean
	 */
	public Boolean followAuthor(Long userId, Long authorId) throws UserManagementException {
		Optional<User> userUser = this.repository.findById(userId);
		Optional<User> userAuthor = this.repository.findById(authorId);

		if (userUser.isPresent() && userAuthor.isPresent()) { // if both isn't present or haven't the appropriate roles
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
