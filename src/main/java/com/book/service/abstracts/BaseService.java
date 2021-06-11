package com.book.service.abstracts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/* Base Service is abstract class that makes use of several generics, to facilitate the creation of implemented classes. 
 * The classes that inherit from this establish the connection between the repositories, the entity converters to dtos and viceversa, 
 * and contain the necessary logic to carry out the necessary actions. 
 * 
 * @author J. Rubén Daza
 */
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
	
	/* This method save an entity from dto, and returns dto. 
	 * 
	 * @param d Dto
	 * 
	 * @return Dto
	 */
	public D save(D d) {
		
		//Convert to Entity to save in BBDD
		E entity = DtoConverter.fromDto(d);
		
		// Return response in DTO Format.
		return DtoConverter.fromEntity(repository.save(entity));
	}
	
	/* This method return a dto by id. 
	 * 
	 * @param id ID
	 * 
	 * @return Dto
	 */
	public D findById(ID id) {
		return DtoConverter.fromEntity(repository.findById(id).get());
	}
	
	/* This method return a list of dtos by id. 
	 * 
	 * @param id ID
	 * 
	 * @return List<Dto>
	 */
	public List<D> findAll() {
		return DtoConverter.fromEntities(repository.findAll());
	}
	
	/* This method save a dto. 
	 * 
	 * @param d Dto
	 * 
	 * @return Dto
	 */
	public D edit(D d) {
		//Convert to Entity to save in BBDD
		E entity = DtoConverter.fromDto(d);
		
		// Return response in DTO Format.
		return DtoConverter.fromEntity(repository.save(entity));
	}
	
	/* This method delete a dto. 
	 * 
	 * @param d Dto
	 */
	public void delete(D d) {
		repository.delete(DtoConverter.fromDto(d));
	}
	
	/* This method delete a dto by id. 
	 * 
	 * @param id ID
	 */
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
	
}
