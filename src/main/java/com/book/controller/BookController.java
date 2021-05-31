package com.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.BookManagementException;
import com.book.exception.UserManagementException;
import com.book.model.dto.BookDTO;
import com.book.model.dto.BookForApprovalDTO;
import com.book.model.dto.BookUserCustomerDTO;
import com.book.model.dto.SimplifiedAuthorDTO;
import com.book.model.dto.minimal.RatingDoubleDTO;
import com.book.security.error.BodyErrorCode;
import com.book.service.BookForApprovalService;
import com.book.service.BookService;
import com.book.service.BookUserCustomerService;
import com.book.service.converters.BookConverter;
import com.book.service.utils.Checker;
import com.book.service.utils.RatingService;

@RestController
@RequestMapping(path = "/book")
public class BookController {
	@Autowired
	protected Checker checker;

	@Autowired
	protected BookService service;
	
	@Autowired
	protected BookForApprovalService bookForApprovalService;
	
	@Autowired
	protected BookUserCustomerService bookUserCustomerService;
	
	@Autowired
	protected RatingService ratingService;
	
	@Autowired
	protected BookConverter converter;
	
	@PostMapping("/approval")
	public ResponseEntity<?> addBookForApproval(@RequestBody BookForApprovalDTO dto) {
		try {
		
			dto = bookForApprovalService.save(dto);				
			
			if (dto != null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);					
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
			}

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addBook(@RequestBody BookDTO dto, @PathVariable(required = false) Long sagaId, @RequestParam List<Long> authorsId) {
		try {
			if (checker.checkBook(dto)) {
				dto = service.save(dto, authorsId, sagaId);				
			}
			
			if (dto != null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);					
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
			}

		} catch (BookManagementException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
	}
	
	@PostMapping("/all/{authorId}")
	public ResponseEntity<?> addBooks(@RequestBody List<BookDTO> dtos, @PathVariable Long authorId, @RequestParam Long sagaId) {
		System.out.println("authorId " + authorId + " |  sagaId " + sagaId);
		for (BookDTO dto: dtos) {
			try {
				checker.checkBook(dto);
			} catch (BookManagementException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());					
			}
		}
		
		dtos = service.saveAll(dtos);
		
		if (dtos != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);					
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);					
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getBook(@PathVariable Long id) {
		BookDTO dto = this.service.findById(id);
		
		if (dto != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.BOOK_NOT_FOUND);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllBooks() {
		List<BookDTO> dtos = this.service.findAll();
		
		if (dtos != null && !dtos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.BOOK_NOT_FOUND);
		}
	}
	
	@GetMapping("/authors/{id}")
	public ResponseEntity<?> getAuthorsBook(@PathVariable Long id) {
		List<SimplifiedAuthorDTO> authors = this.service.getAuthorsBook(id);
		 
		
		return ResponseEntity.status(HttpStatus.OK).body(authors);
	}
	

	@PutMapping("/add/list")
	public ResponseEntity<?> addBookToUserList(@RequestBody BookUserCustomerDTO dto) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.bookUserCustomerService.save(dto));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);

		}
	}
	
	@GetMapping("/mean-score/{id}")
	public ResponseEntity<?> getMeanScoreFromBook(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(
					new RatingDoubleDTO(this.ratingService.GetMeanScoreFromBook(id))
				);
	}
	
	@GetMapping("/user/{idUser}/list")
	public ResponseEntity<?> getBookListFromUser(@PathVariable Long idUser) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.bookUserCustomerService.getBookListFromUserId(idUser));
		} catch (UserManagementException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
	}
	
}
