package com.book.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.model.entity.Relationship.BookUserCustomer;

public interface BookUserCustomerRepository extends JpaRepository<BookUserCustomer, Long> {

}
