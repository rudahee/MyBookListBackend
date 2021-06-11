package com.book.service.converters.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.books.BookForRecommendationDTO;
import com.book.model.entity.book.Book;
import com.book.service.abstracts.DtoConverter;
import com.book.service.utils.RatingService;

/* Implements DtoConverter for Book and Recommendation
 * 
 * @author J. Rubén Daza
 * 
 * @see DtoConverter
 */
@Service
public class BookForRecommendationConverter extends DtoConverter<Book, BookForRecommendationDTO> {

	@Autowired
	private RatingService ratingService;
	
	@Override
	public Book fromDto(BookForRecommendationDTO dto) {
		return null;
	}

	@Override
	public BookForRecommendationDTO fromEntity(Book entity) {
		BookForRecommendationDTO dto = new BookForRecommendationDTO();
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setImageUrl(entity.getImageUrl());
		dto.setName(entity.getName());
		dto.setMeanRating(ratingService.GetMeanScoreFromBook(entity.getId()));
		
		return dto;
	}

}
