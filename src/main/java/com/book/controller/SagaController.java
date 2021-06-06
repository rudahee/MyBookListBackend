package com.book.controller;

import java.util.List;

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

import com.book.model.dto.books.SagaDTO;
import com.book.model.enumerated.BodyErrorCode;
import com.book.service.entity.SagaService;

@RestController
@RequestMapping(path = "/saga")
public class SagaController {

	@Autowired
	protected SagaService service;
	
	@GetMapping("all")
	public ResponseEntity<?> getSagas() {
		List<SagaDTO> dto = this.service.findAll();
		
		if (dto != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.BOOK_NOT_FOUND);
		}
	}
	
	@GetMapping("/book/{idBook}")
	public ResponseEntity<?> getBooksFromSagaByBookId(@PathVariable Long idBook) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getBooksFromSagaByBookId(idBook));
	}
	
	@GetMapping("/author/{id}")
	public ResponseEntity<?> getSagasByAuthor(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getSagaByAuthorId(id));
	}
	
	@PostMapping("")
	public ResponseEntity<?> postSaga(@RequestBody SagaDTO dto) {
		if (dto != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(dto));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.FIELD_IS_MISSING);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (id >= 0) {
			service.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BodyErrorCode.NO_ERROR);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BodyErrorCode.FIELD_IS_MISSING);
		}
	}
}
