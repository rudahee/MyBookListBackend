package com.book.service;

import org.springframework.stereotype.Service;

import com.book.model.dto.BookDTO;
import com.book.model.entity.Book;
import com.book.model.repository.BookRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.BookConverter;

@Service
public class BookService extends BaseService<Book, BookDTO, BookConverter, BookRepository, Long> {

}
