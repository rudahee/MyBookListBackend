package com.book.service.converters.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.books.BookUserCustomerDTO;
import com.book.model.entity.book.Book;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.enumerated.Status;
import com.book.model.repository.books.BookRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.abstracts.DtoConverter;

/* Implements DtoConverter for BookUserCustomer
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class BookUserCustomerConverter extends DtoConverter<BookUserCustomer, BookUserCustomerDTO> {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public BookUserCustomer fromDto(BookUserCustomerDTO dto) {
		
		BookUserCustomer entity = new BookUserCustomer();

		Book book = bookRepository.findById(dto.getBookId()).get();
		UserCustomer userCustomer = (UserCustomer) userRepository.findById(dto.getUserId()).get().getCustomer();
		
		entity.setStatus(dto.getStatus());
		entity.setDate(dto.getDate());
		
		// different data needs to be saved depending on the status
		
		if (entity.getStatus().equals(Status.COMPLETED)) {
			entity.setPagesReaded(book.getPages());
			
		} else if (entity.getStatus().equals(Status.PLANTOREAD)) {
			entity.setPagesReaded(0);
		
		} else {
			entity.setPagesReaded(dto.getPagesReaded());			
		
		}
		
		if (entity.getStatus().equals(Status.COMPLETED) || entity.getStatus().equals(Status.DROPPED)) {
			entity.setScore(dto.getScore());
		
		} else {
			entity.setScore(null);
		
		}
		
		if (entity.getStatus().equals(Status.PLANTOREAD)) {
			entity.setComment(null);
		
		} else {
			entity.setComment(dto.getComment());			
		
		}
		
		entity.setBook(book);
		entity.setUser(userCustomer);
		
		return entity;
	}

	@Override
	public BookUserCustomerDTO fromEntity(BookUserCustomer entity) {

		return null;
	}

}
