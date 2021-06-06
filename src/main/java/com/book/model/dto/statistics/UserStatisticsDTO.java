package com.book.model.dto.statistics;

import com.book.model.enumerated.Genre;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserStatisticsDTO {

	private Integer totalBooksRead;
	private Integer totalBooksThisYear;
	private Integer totalPagesRead;
	private Integer totalPagesThisYear;
	private Integer hoursRead;
	private Integer minutesRead;
	private Double daysRead;
	private Genre mostReadGenre;
	private String mostReadAuthor;
	private Double meanScore;
	
	public UserStatisticsDTO() {
		super();
	}
	
	public UserStatisticsDTO(Integer totalBooksRead, Integer totalBooksThisYear, Integer hoursRead, Integer minutesRead,
			Double daysRead, Genre mostReadGenre, String mostReadAuthor, Double meanScore) {
		super();
		this.totalBooksRead = totalBooksRead;
		this.totalBooksThisYear = totalBooksThisYear;
		this.hoursRead = hoursRead;
		this.minutesRead = minutesRead;
		this.daysRead = daysRead;
		this.mostReadGenre = mostReadGenre;
		this.mostReadAuthor = mostReadAuthor;
		this.meanScore = meanScore;
	}
	
	public Integer getTotalBooksRead() {
		return totalBooksRead;
	}
	public void setTotalBooksRead(Integer totalBooksRead) {
		this.totalBooksRead = totalBooksRead;
	}
	public Integer getTotalBooksThisYear() {
		return totalBooksThisYear;
	}
	public void setTotalBooksThisYear(Integer totalBooksThisYear) {
		this.totalBooksThisYear = totalBooksThisYear;
	}
	public Integer getHoursRead() {
		return hoursRead;
	}
	public void setHoursRead(Integer hoursRead) {
		this.hoursRead = hoursRead;
	}
	public Integer getMinutesRead() {
		return minutesRead;
	}
	public void setMinutesRead(Integer minutesRead) {
		this.minutesRead = minutesRead;
	}
	public Double getDaysRead() {
		return daysRead;
	}
	public void setDaysRead(Double daysRead) {
		this.daysRead = daysRead;
	}
	public Genre getMostReadGenre() {
		return mostReadGenre;
	}
	public void setMostReadGenre(Genre mostReadGenre) {
		this.mostReadGenre = mostReadGenre;
	}
	public String getMostReadAuthor() {
		return mostReadAuthor;
	}
	public void setMostReadAuthor(String mostReadAuthor) {
		this.mostReadAuthor = mostReadAuthor;
	}
	public Double getMeanScore() {
		return meanScore;
	}
	public void setMeanScore(Double meanScore) {
		this.meanScore = meanScore;
	}

	public Integer getTotalPagesRead() {
		return totalPagesRead;
	}

	public void setTotalPagesRead(Integer totalPagesRead) {
		this.totalPagesRead = totalPagesRead;
	}

	public Integer getTotalPagesThisYear() {
		return totalPagesThisYear;
	}

	public void setTotalPagesThisYear(Integer totalPagesThisYear) {
		this.totalPagesThisYear = totalPagesThisYear;
	}
}
