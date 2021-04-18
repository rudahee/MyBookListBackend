package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.BookService;
import com.book.service.converters.BookConverter;
import com.book.service.utils.Checker;

@RestController
@RequestMapping(path = "/book")
public class BookController {
	@Autowired
	protected Checker checker;

	@Autowired
	protected BookService service;
	
	@Autowired
	protected BookConverter converter;
	

}
