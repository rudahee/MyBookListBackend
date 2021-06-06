package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.entity.GenreService;

@RestController
@RequestMapping(path = "/book")
public class BookRelatedController {
	
	@Autowired
	protected GenreService service;
	
	@GetMapping("/genres/all")
	public ResponseEntity<?> getAllGenres() {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllGenres());
	}
}
