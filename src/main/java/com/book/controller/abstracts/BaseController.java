package com.book.controller.abstracts;

import org.springframework.beans.factory.annotation.Autowired;

import com.book.service.utils.Checker;

public abstract class BaseController<T, D, S> {
	/*
	 * T -> entity
	 * D -> dto
	 * S -> service
	 */
	
	@Autowired
	protected S service;
	
	@Autowired
	protected Checker checker;

}
