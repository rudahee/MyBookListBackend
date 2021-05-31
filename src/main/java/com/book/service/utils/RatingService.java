package com.book.service.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.entity.Relationship.BookUserCustomer;
import com.book.model.enumerated.Status;
import com.book.model.repository.BookRepository;

@Service
public class RatingService {

	@Autowired
	private BookRepository bookRepository;
	
	Double meanScore;
	Long quantityScores;
	
	public Double GetMeanScoreFromBook(Long id) {
		List<BookUserCustomer> entities = this.bookRepository.findById(id).get().getUsers();
		
		meanScore = 0d;
		quantityScores = 0l;
		
		entities.forEach(entity -> {
			if (entity.getStatus().equals(Status.COMPLETED) || entity.getStatus().equals(Status.DROPPED)) {
				meanScore = meanScore + entity.getScore();
				quantityScores++;
			}
		});
		
		meanScore = meanScore / quantityScores;
		
		return meanScore;
	}
	
}
