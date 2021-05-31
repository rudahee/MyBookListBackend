package com.book.service;

import org.springframework.stereotype.Service;

import com.book.model.dto.BookForApprovalDTO;
import com.book.model.entity.BookForApproval;
import com.book.model.repository.BookForApprovalRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.BookForApprovalConverter;

@Service
public class BookForApprovalService extends BaseService<BookForApproval, BookForApprovalDTO, BookForApprovalConverter, BookForApprovalRepository, Long> {

}
