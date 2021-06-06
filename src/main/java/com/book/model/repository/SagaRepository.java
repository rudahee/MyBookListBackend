package com.book.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.book.model.entity.Saga;

@Repository
public interface SagaRepository extends JpaRepository<Saga, Long> {

	@Query(value="SELECT DISTINCT s.id, s.name "
				+ "FROM saga s, book b, author_book a "
				+ "WHERE s.id = b.saga_id "
				+ "and b.id = a.book_id "
				+ "and a.author_id = ?1", nativeQuery=true)
	public List<Saga> getSagasByAuthor(Long id);
	
}
