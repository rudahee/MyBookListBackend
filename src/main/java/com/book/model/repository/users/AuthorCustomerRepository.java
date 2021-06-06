package com.book.model.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.customer.AuthorCustomer;

@Repository
public interface AuthorCustomerRepository extends JpaRepository<AuthorCustomer, Long> {

}
