package com.book.service.converters;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.book.model.dto.BookDTO;
import com.book.model.entity.Book;
import com.book.model.entity.Saga;
import com.book.service.abstracts.DtoConverter;

@Service
public class BookConverter extends DtoConverter<Book, BookDTO> {

	@Override
	public Book fromDto(BookDTO dto) {
		Book book = new Book();
		book.setDescription(dto.getDescription());
		book.setId(dto.getId());
		book.setImageUrl(dto.getImageUrl());
		book.setName(dto.getName());
		book.setIsbn(dto.getIsbn());
		book.setPages(dto.getPages());
		book.setPublishDate(dto.getPublishDate());
		book.setPublisher(dto.getPublisher());
		
		return book;
	}

	@Override
	public BookDTO fromEntity(Book entity) {
		BookDTO dto = new BookDTO();
		
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setName(entity.getName());
		dto.setIsbn(entity.getIsbn());
		dto.setPages(entity.getPages());
		dto.setPublishDate(entity.getPublishDate());
		dto.setPublisher(entity.getPublisher());
		
		Saga saga = entity.getSaga();
		
		if (saga != null) {
			dto.setSagaName(saga.getName());
			dto.setSagaTotalBooks(saga.getBooks().size());
			dto.setOrderInSaga(
				saga.getBooks().stream()
					.map(book -> book.getName())
					.collect(Collectors.toList())
					.indexOf(entity.getName())
			);
		}
		
		return dto;
	}

}
