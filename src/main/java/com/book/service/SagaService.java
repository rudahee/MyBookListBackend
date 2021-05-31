package com.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.BookDTO;
import com.book.model.dto.SagaDTO;
import com.book.model.entity.Book;
import com.book.model.entity.Saga;
import com.book.model.repository.BookRepository;
import com.book.model.repository.SagaRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.BookConverter;
import com.book.service.converters.SagaConverter;

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
	
}
