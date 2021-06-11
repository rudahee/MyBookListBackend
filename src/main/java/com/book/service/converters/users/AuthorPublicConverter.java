package com.book.service.converters.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.books.BookDTO;
import com.book.model.dto.users.AuthorPublicDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.service.abstracts.DtoConverter;
import com.book.service.converters.books.BookConverter;

/* Implements DtoConverter for User with author role
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class AuthorPublicConverter extends DtoConverter<User, AuthorPublicDTO> {

	@Autowired
	private BookConverter bookConverter;
	
	@Override
	public User fromDto(AuthorPublicDTO dto) {
		return null;
	}

	@Override
	public AuthorPublicDTO fromEntity(User entity) {
		AuthorPublicDTO dto = new AuthorPublicDTO();
		// User related
		dto.setId(entity.getId());
		dto.setAge(entity.getAge());
		dto.setEmail(entity.getEmail());
		dto.setName(entity.getName());
		dto.setSurname(entity.getSurname());
		dto.setUsername(entity.getUsername());
		dto.setEmail(entity.getEmail());
		dto.setRoles(entity.getRoles());
		
		AuthorCustomer customerEntity = (AuthorCustomer) entity.getCustomer();
		
		dto.setBiography(customerEntity.getBiography());
		dto.setUrlImage(customerEntity.getUrlImage());
		
		List<BookDTO> booksDTO = bookConverter.fromEntities(customerEntity.getBooks());
		
		dto.setBooks(booksDTO);
		
		return dto;
	}

}
