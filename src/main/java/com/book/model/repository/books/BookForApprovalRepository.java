package com.book.model.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.model.entity.book.BookForApproval;

@Repository
public interface BookForApprovalRepository extends JpaRepository<BookForApproval, Long>{

}
