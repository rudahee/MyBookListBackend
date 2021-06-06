package com.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.book.model.dto.books.BookDTO;
import com.book.model.dto.books.BookForApprovalDTO;
import com.book.model.dto.books.BookUserCustomerDTO;
import com.book.model.dto.minimal.RatingDoubleDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.service.converters.books.BookConverter;
import com.book.service.entity.books.BookForApprovalService;
import com.book.service.entity.books.BookService;
import com.book.service.entity.relationships.BookUserCustomerService;
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
	
	@GetMapping("/approval/all")
	public ResponseEntity<?> getAllBooksForApproval() {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookForApprovalService.findAll());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	@GetMapping("/approval/{id}")
	public ResponseEntity<?> getBookForApproval(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookForApprovalService.findById(id));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	@DeleteMapping("/approval/{id}")
	public ResponseEntity<?> deleteBookForApproval(@PathVariable Long id) {
		try {
			bookForApprovalService.findById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BodyErrorCode.NO_ERROR);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<?> getCommentsById(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getCommentsFromBook(id));
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	
	@PostMapping("/{sagaId}")
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
	
	@PostMapping("/all/{sagaId}")
	public ResponseEntity<?> addBooks(@RequestBody List<BookDTO> dtos, @PathVariable(required = false) Long sagaId, @RequestParam List<Long> authorsId) {
		ArrayList<BookDTO> dtosReturn = new ArrayList<BookDTO>();
		for (BookDTO dto: dtos) {
			try {
				if (checker.checkBook(dto)) {
					dtosReturn.add(service.save(dto, authorsId, sagaId));					
				}
			} catch (BookManagementException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());					
			}
		}
		
		
		if (dtos != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtosReturn);					
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
