package com.book.model.repository.relationships;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.model.entity.relationship.BookUserCustomer;

public interface BookUserCustomerRepository extends JpaRepository<BookUserCustomer, Long> {

}
