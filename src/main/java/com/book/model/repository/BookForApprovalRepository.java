package com.book.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.BookForApproval;

@Repository
public interface BookForApprovalRepository extends JpaRepository<BookForApproval, Long>{

}
