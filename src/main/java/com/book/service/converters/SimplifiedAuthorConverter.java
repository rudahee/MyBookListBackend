package com.book.service.converters;

import org.springframework.stereotype.Service;

import com.book.model.dto.SimplifiedAuthorDTO;
import com.book.model.entity.User;
import com.book.model.entity.Customer.AuthorCustomer;
import com.book.service.abstracts.DtoConverter;

@Service
public class SimplifiedAuthorConverter extends DtoConverter<User, SimplifiedAuthorDTO>  {

	@Override
	public User fromDto(SimplifiedAuthorDTO dto) {
		return null;
	}

	@Override
	public SimplifiedAuthorDTO fromEntity(User entity) {
		SimplifiedAuthorDTO dto = new SimplifiedAuthorDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setSurname(entity.getSurname());
		dto.setUsername(entity.getUsername());
		dto.setCompleteName(entity.getName() + " " + entity.getSurname());
		
		AuthorCustomer author = (AuthorCustomer) entity.getCustomer();
		
		dto.setImageUrl(author.getUrlImage());
		
		return dto;
	}

}
