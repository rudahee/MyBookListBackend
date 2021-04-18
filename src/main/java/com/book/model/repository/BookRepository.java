package com.book.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
