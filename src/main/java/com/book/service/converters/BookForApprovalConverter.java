package com.book.service.converters;

import org.springframework.stereotype.Service;

import com.book.model.dto.BookForApprovalDTO;
import com.book.model.entity.BookForApproval;
import com.book.service.abstracts.DtoConverter;

@Service
public class BookForApprovalConverter extends DtoConverter<BookForApproval, BookForApprovalDTO> {

	@Override
	public BookForApproval fromDto(BookForApprovalDTO dto) {
		BookForApproval entity = new BookForApproval();
		
		entity.setAuthorName(dto.getAuthorName());
		entity.setDescription(dto.getDescription());
		entity.setId(dto.getId());
		entity.setImageUrl(dto.getImageUrl());
		entity.setIsbn(dto.getIsbn());
		entity.setName(dto.getName());
		entity.setNotes(dto.getNotes());
		entity.setPages(dto.getPages());
		entity.setPublishDate(dto.getPublishDate());
		entity.setPublisher(dto.getPublisher());
		entity.setReferences1(dto.getReferences1());
		entity.setReferences2(dto.getReferences2());
		entity.setSagaName(dto.getSagaName());
		
		return entity;
	}

	@Override
	public BookForApprovalDTO fromEntity(BookForApproval entity) {
		BookForApprovalDTO dto = new BookForApprovalDTO();
		
		dto.setAuthorName(entity.getAuthorName());
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setIsbn(entity.getIsbn());
		dto.setName(entity.getName());
		dto.setNotes(entity.getNotes());
		dto.setPages(entity.getPages());
		dto.setPublishDate(entity.getPublishDate());
		dto.setPublisher(entity.getPublisher());
		dto.setReferences1(entity.getReferences1());
		dto.setReferences2(entity.getReferences2());
		dto.setSagaName(entity.getSagaName());
		
		return dto;
	}

}
