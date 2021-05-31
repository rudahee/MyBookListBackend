package com.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.BookDTO;
import com.book.model.dto.SimplifiedAuthorDTO;
import com.book.model.entity.Book;
import com.book.model.entity.Saga;
import com.book.model.entity.Customer.AuthorCustomer;
import com.book.model.repository.AuthorCustomerRepository;
import com.book.model.repository.BookRepository;
import com.book.model.repository.SagaRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.BookConverter;
import com.book.service.converters.SimplifiedAuthorConverter;

@Service
public class BookService extends BaseService<Book, BookDTO, BookConverter, BookRepository, Long> {

	@Autowired
	private SimplifiedAuthorConverter authorConverter;
	@Autowired
	private SagaRepository sagaRepository;
	@Autowired
	private AuthorCustomerRepository authorRepository;
	
	
	public BookDTO save(BookDTO dto, List<Long> authorsId, Long sagaId) {
		
		Book bookEntity = DtoConverter.fromDto(dto);
		if (sagaId != null) {
			Saga sagaEntity = sagaRepository.findById(sagaId).get();
			
			bookEntity.setSaga(sagaEntity);
			sagaEntity.addBook(bookEntity);
			
			sagaRepository.saveAndFlush(sagaEntity);
		}
		
		ArrayList<AuthorCustomer> authorsEntities = new ArrayList<AuthorCustomer>();
		
		authorsId.forEach(authorId -> {
			AuthorCustomer authorEntity = authorRepository.findById(authorId+1).get();
			authorsEntities.add(authorEntity);
			authorEntity.addBook(bookEntity);
			
		});
		bookEntity.AddAllAuthors(authorsEntities);

		authorRepository.saveAll(authorsEntities);
				
		return DtoConverter.fromEntity(bookEntity);
	}
	
	public List<BookDTO> saveAll(List<BookDTO> dtos) {
		List<Book> entities = DtoConverter.fromDtos(dtos);
		
		return DtoConverter.fromEntities(repository.saveAll(entities));
	}
	
	public List<SimplifiedAuthorDTO> getAuthorsBook(Long id) {
		Optional<Book> book = this.repository.findById(id);
		
		if (book.isPresent()) {
			List<AuthorCustomer> authors = book.get().getAuthor();
			
			ArrayList<SimplifiedAuthorDTO> authorsDTO = new ArrayList<SimplifiedAuthorDTO>();
			
			authors.forEach(author -> {
				authorsDTO.add(authorConverter.fromEntity(author.getUser()));
			});
			
			return authorsDTO;
		} else {
			return null;
		}
	}
	
}
