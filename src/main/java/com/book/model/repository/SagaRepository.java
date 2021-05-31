package com.book.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.Saga;

@Repository
public interface SagaRepository extends JpaRepository<Saga, Long> {

}
