package com.book.service.converters;

import org.springframework.stereotype.Service;

import com.book.model.dto.books.SagaDTO;
import com.book.model.entity.Saga;
import com.book.service.abstracts.DtoConverter;

/* Implements DtoConverter for saga 
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class SagaConverter extends DtoConverter<Saga, SagaDTO> {

	@Override
	public Saga fromDto(SagaDTO dto) {
		Saga saga = new Saga();
		
		saga.setId(dto.getId());
		saga.setName(dto.getName());
		
		return saga;
	}

	@Override
	public SagaDTO fromEntity(Saga entity) {
		SagaDTO dto = new SagaDTO();
		
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		
		return dto;
	}

}
