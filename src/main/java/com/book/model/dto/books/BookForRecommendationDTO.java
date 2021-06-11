package com.book.model.dto.books;

import java.util.List;

import com.book.model.enumerated.Genre;

public class BookForRecommendationDTO {

	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private String sagaName;
	private Double meanRating;
	
	public BookForRecommendationDTO() {
		super();
	}

	public BookForRecommendationDTO(Long id, String name, String description, String imageUrl, String isbn,
			List<Genre> genres, String sagaName, Double meanRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.sagaName = sagaName;
		this.meanRating = meanRating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSagaName() {
		return sagaName;
	}

	public void setSagaName(String sagaName) {
		this.sagaName = sagaName;
	}

	public Double getMeanRating() {
		return meanRating;
	}

	public void setMeanRating(Double meanRating) {
		this.meanRating = meanRating;
	}
}
