package com.book.service.entity.relationships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exception.UserManagementException;
import com.book.model.dto.books.BookForUserListDTO;
import com.book.model.dto.books.BookUserCustomerDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.repository.relationships.BookUserCustomerRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.books.BookUserCustomerConverter;

@Service
public class BookUserCustomerService extends BaseService<BookUserCustomer, BookUserCustomerDTO, BookUserCustomerConverter, BookUserCustomerRepository, Long> {

	@Autowired
	private UserRepository userRepository;
	
	
	public List<BookForUserListDTO> getBookListFromUserId(Long idUser) throws UserManagementException {
		Optional<User> user = this.userRepository.findById(idUser);
		ArrayList<BookForUserListDTO> dtos = new ArrayList<BookForUserListDTO>();
		
		if (user.isPresent()) {
			if (user.get().getCustomer().getClass().isAssignableFrom(UserCustomer.class)) {
				UserCustomer userCustomer = (UserCustomer) user.get().getCustomer();
				
				userCustomer.getBooks().forEach(buc -> {
					BookForUserListDTO auxBook = new BookForUserListDTO();
					auxBook.setId(buc.getBook().getId());
					auxBook.setName(buc.getBook().getName());
					auxBook.setImageUrl(buc.getBook().getImageUrl());
					if(buc.getBook().getSaga() != null) {
						auxBook.setSagaName(buc.getBook().getSaga().getName());						
					}
					auxBook.setTotalPages(buc.getBook().getPages());
					auxBook.setComment(buc.getComment());
					auxBook.setPagesReaded(buc.getPagesReaded());
					auxBook.setScore(buc.getScore());
					auxBook.setStatus(buc.getStatus());
					dtos.add(auxBook);
				});
				
				
				return dtos;
			} else {
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
			}
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}
	
}
