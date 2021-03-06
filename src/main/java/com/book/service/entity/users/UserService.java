package com.book.service.entity.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.exception.UserManagementException;
import com.book.model.dto.FriendshipDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.dto.users.UserDTO;
import com.book.model.dto.users.UserPersonalDataDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.AdminCustomer;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.Friendship;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.enumerated.UserRole;
import com.book.model.repository.relationships.FriendshipRepository;
import com.book.model.repository.users.AuthorCustomerRepository;
import com.book.model.repository.users.UserCustomerRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.FriendshipConverter;
import com.book.service.converters.users.SimplifiedAuthorConverter;
import com.book.service.converters.users.UserConverter;
import com.book.service.utils.EmailService;

@Service
public class UserService extends BaseService<User, UserDTO, UserConverter, UserRepository, Long> implements UserDetailsService {
	
	@Autowired
	protected PasswordEncoder passEncoder;
	
	@Autowired
	private UserCustomerRepository userCustomerRepository;
	
	@Autowired
	private AuthorCustomerRepository authorCustomerRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private FriendshipRepository friendsRepository;
	
	@Autowired
	private FriendshipConverter friendsConverter;
	
	@Autowired
	private SimplifiedAuthorConverter simpleAuthorConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Optional<User> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}	
	
	public boolean existsById(Long id) {
		return this.repository.existsById(id);
	}	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("Username not found"));
	}

	public UserDetails loadUserById(Long id) {
		return super.repository.findById(id).get();
	}
	
	
	public UserDTO signUpUser(UserDTO user) throws UserManagementException {
		
		if (repository.existsUserByEmail(user.getEmail())) {
			throw new UserManagementException(BodyErrorCode.EMAIL_ALREADY_EXISTS);
		} else if(repository.existsUserByUsername(user.getUsername())) {
			throw new UserManagementException(BodyErrorCode.USERNAME_ALREADY_EXISTS);
		}
		
		String activationCode = UUID.randomUUID().toString().substring(0, 8);
		user.setActivationCode(activationCode);
		user.setEnableAccount(false);
		user.setRoles(Set.of(UserRole.USER));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserCustomer customer = new UserCustomer();
		
		User userObj = DtoConverter.fromDto(user);
		
		userObj.setCustomer(customer);
		customer.setUser(userObj);
		
		return DtoConverter.fromEntity(repository.save(userObj));
	}
	
	public UserDTO signUpAuthor(UserDTO user) throws UserManagementException {
		
		if (repository.existsUserByEmail(user.getEmail())) {
			throw new UserManagementException(BodyErrorCode.EMAIL_ALREADY_EXISTS);
		} else if(repository.existsUserByUsername(user.getUsername())) {
			throw new UserManagementException(BodyErrorCode.USERNAME_ALREADY_EXISTS);
		}
		
		user.setPassword(UUID.randomUUID().toString().substring(0, 13));
		if (user.getEnableAccount()) {
			String completeName = user.getName() + ' ' + user.getSurname();
			emailService.sendSignUpComplete(user.getUsername(), user.getPassword(), completeName, user.getEmail());
		} else {
			String activationCode = UUID.randomUUID().toString().substring(0, 8);
			user.setActivationCode(activationCode);
		}
		
		if (user.getEnableAccount()) {
		}
		user.setRoles(Set.of(UserRole.AUTHOR));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		AuthorCustomer customer = new AuthorCustomer();
		customer.setBiography("");
		customer.setUrlImage("");
		
		User userObj = DtoConverter.fromDto(user);
		
		userObj.setCustomer(customer);
		customer.setUser(userObj);
		
		return DtoConverter.fromEntity(repository.save(userObj));
	}
	
	public UserDTO signUpAdmin(UserDTO user) throws UserManagementException {
		
		if (repository.existsUserByEmail(user.getEmail())) {
			throw new UserManagementException(BodyErrorCode.EMAIL_ALREADY_EXISTS);
		} else if(repository.existsUserByUsername(user.getUsername())) {
			throw new UserManagementException(BodyErrorCode.USERNAME_ALREADY_EXISTS);
		}
		
		if (user.getEnableAccount()) {
			user.setPassword(UUID.randomUUID().toString().substring(0, 13));
			String completeName = user.getName() + ' ' + user.getSurname();
			emailService.sendSignUpComplete(user.getUsername(), user.getPassword(), completeName, user.getEmail());
			
		} else {
			String activationCode = UUID.randomUUID().toString().substring(0, 8);
			user.setActivationCode(activationCode);			
			
		}
		
		user.setRoles(Set.of(UserRole.ADMIN));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		AdminCustomer customer = new AdminCustomer();
		
		User userObj = DtoConverter.fromDto(user);
		
		userObj.setCustomer(customer);
		customer.setUser(userObj);
		
		return DtoConverter.fromEntity(repository.save(userObj));
	}
	
	public Boolean createFriendRequest(Long requesterId, Long receiverId) throws UserManagementException {
		
		if (requesterId.equals(receiverId)) {
			throw new UserManagementException(BodyErrorCode.FRIENDS_ITSELF);
		}
		
		UserCustomer requesterCustomer = (UserCustomer) repository.findById(requesterId).get().getCustomer();
		UserCustomer receiverCustomer = (UserCustomer) repository.findById(receiverId).get().getCustomer();

		
		Friendship friendship = new Friendship(requesterCustomer, receiverCustomer, false);
		
		Set<Friendship> auxFriendshipSet = requesterCustomer.getRequests();
		auxFriendshipSet.add(friendship);
		
		requesterCustomer.setRequests(auxFriendshipSet);
		
		auxFriendshipSet = receiverCustomer.getFriends();
		auxFriendshipSet.add(friendship);
		
		receiverCustomer.setFriends(auxFriendshipSet);
		
		friendsRepository.save(friendship);
		
		userCustomerRepository.save(requesterCustomer);
		userCustomerRepository.save(receiverCustomer);
		
		return true;
	}
	
	public Boolean acceptFriendRequest(Long requesterId, Long receiverId) {
		UserCustomer requesterCustomer = (UserCustomer) repository.findById(requesterId).get().getCustomer();
		UserCustomer receiverCustomer = (UserCustomer) repository.findById(receiverId).get().getCustomer();

		
		Friendship friendship = friendsRepository.findByFriendAndRequester(receiverCustomer, requesterCustomer).get();
		
		friendship.setAccepted(true);
		
		friendsRepository.save(friendship);
		return true;
	}
	
	public Boolean rejectFriendRequest(Long requesterId, Long receiverId) {
		UserCustomer requesterCustomer = (UserCustomer) repository.findById(requesterId).get().getCustomer();
		UserCustomer receiverCustomer = (UserCustomer) repository.findById(receiverId).get().getCustomer();

		Friendship friendship = friendsRepository.findByFriendAndRequester(receiverCustomer, requesterCustomer).get();
		
		friendsRepository.delete(friendship);
		
		return true;
	}
	
	public List<FriendshipDTO> getFriendList(Long receiverId) {
		UserCustomer receiverCustomer = (UserCustomer) repository.findById(receiverId).get().getCustomer();
		
		return friendsConverter.fromEntities(friendsRepository.findAllByfriend(receiverCustomer));
	}
	
	public void followAuthor(Long userId, Long authorId) throws UserManagementException {
		Optional<UserCustomer> user = userCustomerRepository.findById(userId);
		Optional<AuthorCustomer> author = authorCustomerRepository.findById(authorId);
		
		if (user.isEmpty() || author.isEmpty()) {
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		} 
		AuthorCustomer authorFinal = author.get();
		UserCustomer userFinal = user.get();
		authorFinal.addFollower(userFinal);
		userFinal.addAuthorFollow(authorFinal);
		
		userCustomerRepository.save(userFinal);
		authorCustomerRepository.save(authorFinal);
	}
	
	public List<SimplifiedAuthorDTO> getFollows(Long userId) throws UserManagementException {
		Optional<UserCustomer> user = userCustomerRepository.findById(userId);
		ArrayList<SimplifiedAuthorDTO> authors = new ArrayList<SimplifiedAuthorDTO>();
		
		if (user.isEmpty()) {
			throw new UserManagementException(BodyErrorCode.FIELD_IS_MISSING);
		}
		
		user.get().getFollows().forEach(c -> {
			authors.add(simpleAuthorConverter.fromEntity(c.getUser()));
		});
		
		return authors;
	}
	
	@Transactional
	public void changePersonalData(Long id, UserPersonalDataDTO data) throws UserManagementException {
		Optional<User> userOpt = repository.findById(id);
		User user;
		if (userOpt.isPresent()) {
			user = userOpt.get();
			
			if (data.getAge() != null) {
				user.setAge(data.getAge());
			}
			if (data.getEmail() != null) {
				if (!repository.existsUserByEmail(data.getEmail())) {
					user.setEmail(data.getEmail());					
				} else {
					throw new UserManagementException(BodyErrorCode.EMAIL_ALREADY_EXISTS);
				}
			}
			
			if (data.getUrlImage() != null) {
				UserCustomer customer = (UserCustomer) user.getCustomer();
				customer.setUrlImageProfile(data.getUrlImage());
			}
			
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}

	public UserPersonalDataDTO getPersonalInfoForChange(Long id) {
		Optional<User> userOpt = repository.findById(id);
		UserPersonalDataDTO dto = new UserPersonalDataDTO();
		
		dto.setAge(userOpt.get().getAge());
		dto.setEmail(userOpt.get().getEmail());
		dto.setUrlImage(((UserCustomer) userOpt.get().getCustomer()).getUrlImageProfile());
		return dto;
	}
}
