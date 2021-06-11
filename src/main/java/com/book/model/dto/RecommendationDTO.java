package com.book.model.dto;

import java.util.List;

import com.book.model.dto.books.BookForRecommendationDTO;

/* This class represents a recommendation with books
 * 
 * @author J. Rubén Daza
 */
public class RecommendationDTO {
	
	private Integer type;
	private String sentence;
	private List<BookForRecommendationDTO> books;
	
	public RecommendationDTO() {
		super();
	}
	
	public RecommendationDTO(Integer type, String sentence, List<BookForRecommendationDTO> books) {
		super();
		this.type = type;
		this.sentence = sentence;
		this.books = books;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public List<BookForRecommendationDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookForRecommendationDTO> books) {
		this.books = books;
	}
}
