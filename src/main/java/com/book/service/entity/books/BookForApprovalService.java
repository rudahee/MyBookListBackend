package com.book.service.entity.books;

import org.springframework.stereotype.Service;

import com.book.model.dto.books.BookForApprovalDTO;
import com.book.model.entity.book.BookForApproval;
import com.book.model.repository.books.BookForApprovalRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.books.BookForApprovalConverter;

@Service
public class BookForApprovalService extends BaseService<BookForApproval, BookForApprovalDTO, BookForApprovalConverter, BookForApprovalRepository, Long> {

}
