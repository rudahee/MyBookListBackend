package com.book.service.abstracts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<E, D, DC extends DtoConverter<E, D>, R extends JpaRepository<E, ID>, ID> {

	/*
	 * E -> Entity
	 * D -> DTO
	 * DC -> DTOConverter
	 * ID -> Id Type
	 * R -> Repository
	 */
	
	@Autowired
	protected R repository;
	
	@Autowired
	protected DC DtoConverter;
	
	public D save(D d) {
		
		//Convert to Entity to save in BBDD
		E entity = DtoConverter.fromDto(d);
		
		// Return response in DTO Format.
		return DtoConverter.fromEntity(repository.save(entity));
	}
	
	public D findById(ID id) {
		return DtoConverter.fromEntity(repository.findById(id).get());
	}
	
	public List<D> findAll() {
		return DtoConverter.fromEntities(repository.findAll());
	}
	
	public D edit(D d) {
		//Convert to Entity to save in BBDD
		E entity = DtoConverter.fromDto(d);
		
		// Return response in DTO Format.
		return DtoConverter.fromEntity(repository.save(entity));
	}
	
	public void delete(D d) {
		repository.delete(DtoConverter.fromDto(d));
	}
	
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
	
}
