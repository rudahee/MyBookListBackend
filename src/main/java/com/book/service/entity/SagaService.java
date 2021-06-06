package com.book.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.books.BookDTO;
import com.book.model.dto.books.SagaDTO;
import com.book.model.entity.Saga;
import com.book.model.entity.book.Book;
import com.book.model.repository.SagaRepository;
import com.book.model.repository.books.BookRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.SagaConverter;
import com.book.service.converters.books.BookConverter;

@Service
public class SagaService extends BaseService<Saga, SagaDTO, SagaConverter, SagaRepository, Long> {

	@Autowired
	private BookRepository bookRepository;
		
	@Autowired
	private BookConverter bookConverter;
	
	
	public List<BookDTO> getBooksFromSagaByBookId(Long id) {
		Book book = this.bookRepository.findById(id).get();
	
		List<BookDTO> booksDTO = bookConverter.fromEntities(book.getSaga().getBooks());
		
		return booksDTO;
	}
	
	public List<SagaDTO> getSagaByAuthorId(Long id) {
		return this.DtoConverter.fromEntities(this.repository.getSagasByAuthor(id+1)); 
	}
}
