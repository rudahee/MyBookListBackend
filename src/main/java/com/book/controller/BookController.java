package com.book.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.book.model.dto.books.BookDTO;
import com.book.model.dto.books.BookUserCustomerDTO;
import com.book.model.dto.users.SimplifiedAuthorDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.security.common.SecurityConstants;
import com.book.security.jwt.JWTTokenProvider;
import com.book.service.converters.books.BookConverter;
import com.book.service.entity.books.BookService;
import com.book.service.entity.relationships.BookUserCustomerService;
import com.book.service.utils.Checker;


/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/book
* 
* This controller is in charge of working with the book entity.
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/book")
public class BookController {
	@Autowired
	protected Checker checker;

	@Autowired
	protected BookService service;
	
	@Autowired
	protected BookUserCustomerService bookUserCustomerService;

	@Autowired
	protected BookConverter converter;
	
	/* HTTP/GET
	 * 
	 * This method returns all the comments for a book id.
	 * 
	 * @param id Long for book id
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or CommentsDTO.
	 */
	@GetMapping("/comments/{id}")
	public ResponseEntity<?> getCommentsById(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getCommentsFromBook(id));
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/POST
	 * 
	 * This method allows you to create a new book in the database with its corresponding relationships. 
	 * 
	 * @param dto BookDTO
	 * @param sagaId Long. this parameter its not required 
	 * @param authorsId List<Long>. at least one.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookDTO.
	 */
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
	
	/* HTTP/POST
	 * 
	 * This method allows you to create a new books (one or more) in the database with its corresponding relationships. 
	 * 
	 * @param dto List<BookDTO> various dtos
	 * @param sagaId Long. this parameter its not required 
	 * @param authorsId List<Long>. at least one.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookDTO.
	 */
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
	
	/* HTTP/GET
	 * 
	 * This method allows to obtain all the data of a book.
	 * 
	 * @param id Long. id from book.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookDTO.
	 */
	@GetMapping("{id}")
	public ResponseEntity<?> getBook(@PathVariable Long id) {
		BookDTO dto = this.service.findById(id);
		
		if (dto != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.BOOK_NOT_FOUND);
		}
	}

	/* HTTP/GET
	 * 
	 * This method allows to obtain all the data of all books.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookDTO.
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllBooks() {
		List<BookDTO> dtos = this.service.findAll();
		
		if (dtos != null && !dtos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtos);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.BOOK_NOT_FOUND);
		}
	}
	
	/* HTTP/GET
	 * 
	 * This method return all authors of a book
	 * 
	 * @param id Long. id from book.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or SimplifiedAuthorDTO.
	 */
	@GetMapping("/authors/{id}")
	public ResponseEntity<?> getAuthorsBook(@PathVariable Long id) {
		List<SimplifiedAuthorDTO> authors = this.service.getAuthorsBook(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(authors);
	}
	

	/* HTTP/PUT
	 * 
	 * Add a book to user list.
	 * 
	 * @param dto BookUserCustomerDTO
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookUserCustomerDTO.
	 */
	@PutMapping("/add/list")
	public ResponseEntity<?> addBookToUserList(@RequestBody BookUserCustomerDTO dto) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.bookUserCustomerService.save(dto));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);

		}
	}
	
	/* HTTP/PUT
	 * 
	 * update a book to user list.
	 * 
	 * @param dto BookUserCustomerDTO, 
	 * @param request HttpServletRequest. Its mandatory to obtain id 
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookUserCustomerDTO.
	 */
	@PutMapping("/update/list")
	public ResponseEntity<?> updateBookToUserList(@RequestBody BookUserCustomerDTO dto, HttpServletRequest request) {
		Long idUser = JWTTokenProvider.getIdFromToken(
				request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
				);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.bookUserCustomerService.editBookUserCustomer(dto, idUser));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);

		}
	}
	
	/* HTTP/GET
	 * 
	 * This method return all books added a BookList of one user
	 * 
	 * @param idUser Long. id from user.
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or BookForUserListDTO.
	 */
	@GetMapping("/user/{idUser}/list")
	public ResponseEntity<?> getBookListFromUser(@PathVariable Long idUser) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.bookUserCustomerService.getBookListFromUserId(idUser));
		} catch (UserManagementException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getCode());
		}
	}
	
	/* HTTP/GET
	 * 
	 *  This method return one books added a BookList of one user
	 * 
	 * @param id Long. idBook from book.
	 * @param request HttpServletRequest. Its mandatory to obtain id user
	 * 
	 * @return ResponseEntity<?> BodyErrorCode or SimplifiedAuthorDTO.
	 */
	@GetMapping("/user/status/{idBook}")
	public ResponseEntity<?> getBookStatusFromUser(@PathVariable Long idBook, HttpServletRequest request) {
		try {
			Long idUser = JWTTokenProvider.getIdFromToken(
					request.getHeader(SecurityConstants.TOKEN_HEADER).substring(SecurityConstants.TOKEN_PREFIX.length())
					);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.bookUserCustomerService.getStatusBookFromUser(idUser, idBook));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
}
