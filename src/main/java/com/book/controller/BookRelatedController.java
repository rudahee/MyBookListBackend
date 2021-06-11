package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.dto.books.BookForApprovalDTO;
import com.book.model.dto.minimal.RatingDoubleDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.service.entity.GenreService;
import com.book.service.entity.books.BookForApprovalService;
import com.book.service.utils.RatingService;


/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/book
* 
* This controller is in charge of working with the related to the book entity.
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/book")
public class BookRelatedController {
	
	@Autowired
	protected GenreService service;
	
	@Autowired
	protected BookForApprovalService bookForApprovalService;
	
	@Autowired
	protected RatingService ratingService;
	
	/* HTTP/GET
	 * 
	 * This method returns all the genres.
	 * 
	 * @return ResponseEntity<Genre>.
	 */
	@GetMapping("/genres/all")
	public ResponseEntity<?> getAllGenres() {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllGenres());
	}
	
	/* HTTP/POST
	 * 
	 * Add book to approval in specific table
	 * 
	 * @param dto BookForApprovalDTO
	 * 
	 * @return ResponseEntity<?> BookForApprovalDTO or BodyErrorCode.
	 */
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
	
	/* HTTP/GET
	 * 
	 * This method returns all books for approval.
	 * 
	 * @return ResponseEntity<?> BookForApproval or BodyErrorCode.
	 */
	@GetMapping("/approval/all")
	public ResponseEntity<?> getAllBooksForApproval() {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookForApprovalService.findAll());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/GET
	 * 
	 * This method returns one books for approval.
	 * 
	 * @param id Long for approval id
	 * 
	 * @return ResponseEntity<?> BookForApproval or BodyErrorCode.
	 */
	@GetMapping("/approval/{id}")
	public ResponseEntity<?> getBookForApproval(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookForApprovalService.findById(id));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/DELETE
	 * 
	 * This method delete a book for approval
	 * 
	 * @param id Long for approval id
	 * 
	 * @return ResponseEntity<BodyErrorCode>.
	 */
	@DeleteMapping("/approval/{id}")
	public ResponseEntity<?> deleteBookForApproval(@PathVariable Long id) {
		try {
			bookForApprovalService.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BodyErrorCode.NO_ERROR);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BodyErrorCode.INDETERMINATE_ERROR);
		}
	}
	
	/* HTTP/GET
	 * 
	 * This method returns mean score from book
	 * 
	 * @param id Long for approval id
	 * 
	 * @return ResponseEntity<?> BookForApproval or Double.
	 */
	@GetMapping("/mean-score/{id}")
	public ResponseEntity<?> getMeanScoreFromBook(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(
					new RatingDoubleDTO(this.ratingService.GetMeanScoreFromBook(id))
				);
	}
}
