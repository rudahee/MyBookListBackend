package com.book.service.converters.users;

import org.springframework.stereotype.Service;

import com.book.model.dto.users.UserDTO;
import com.book.model.entity.User;
import com.book.service.abstracts.DtoConverter;

/* Implements DtoConverter for User with user role 
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class UserConverter extends DtoConverter<User, UserDTO> {
	
	@Override
	public User fromDto(UserDTO dto) {
		User user  = new User();
		
		user.setId(dto.getId());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setAge(dto.getAge());
		user.setName(dto.getName());
		user.setGender(dto.getGender());
		user.setSurname(dto.getSurname());
		user.setRoles(dto.getRoles());
		user.setActivationCode(dto.getActivationCode());
		user.setEnableAccount(dto.getEnableAccount());
		
		return user;

	}

	@Override
	public UserDTO fromEntity(User entity) {
		UserDTO dto = new UserDTO();
		
		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setUsername(entity.getUsername());
		dto.setPassword(entity.getPassword());
		dto.setAge(entity.getAge());
		dto.setName(entity.getName());
		dto.setSurname(entity.getSurname());
		dto.setRoles(entity.getRoles());
		dto.setGender(entity.getGender());
		dto.setEnableAccount(entity.getEnableAccount());
		dto.setLastPasswordChange(entity.getLastPasswordChange());
		dto.setNextPasswordChange(entity.getNextPasswordChange());
		dto.setCreateTime(entity.getCreateTime());
		dto.setUpdateTime(entity.getUpdateTime());
		dto.setActivationCode(entity.getActivationCode());

		return dto;
	}

}
