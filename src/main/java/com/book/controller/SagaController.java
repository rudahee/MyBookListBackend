package com.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.dto.SagaDTO;
import com.book.security.error.BodyErrorCode;
import com.book.service.SagaService;

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
}
