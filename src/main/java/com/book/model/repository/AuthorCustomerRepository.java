package com.book.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.Customer.AuthorCustomer;

@Repository
public interface AuthorCustomerRepository extends JpaRepository<AuthorCustomer, Long> {

}
