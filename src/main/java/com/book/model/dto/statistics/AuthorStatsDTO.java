package com.book.model.dto.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a statistics for an author
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class AuthorStatsDTO {

	private Integer qtyUserReadBooks;
	private Integer qtyUserDroppedBooks;
	private Double meanScore;
	
	private String lastBook;
	private Integer qtyUserReadLastBook;
	private Integer qtyUserDroppedLastBook;
	private Double meanScoreLastBook;
	
	private String mostReadBook;
	private Integer qtyUserReadMostReadBook;
	private Integer qtyUserDroppedMostReadBook;
	private Double meanScoreMostReadBook;
	
	private String mostRatedBook;
	private Integer qtyUserReadMostRatedBook;
	private Integer qtyUserDroppedMostRatedBook;
	private Double meanScoreMostRatedBook;
	
	private Integer qtyFollowers;
	private Integer femaleFollowers;
	private Integer maleFollowers;
	private Integer meanAgeFollowers;

	public AuthorStatsDTO() {
		super();
	}

	public Integer getQtyUserReadBooks() {
		return qtyUserReadBooks;
	}

	public void setQtyUserReadBooks(Integer qtyUserReadBooks) {
		this.qtyUserReadBooks = qtyUserReadBooks;
	}

	public Integer getQtyUserDroppedBooks() {
		return qtyUserDroppedBooks;
	}

	public void setQtyUserDroppedBooks(Integer qtyUserDroppedBooks) {
		this.qtyUserDroppedBooks = qtyUserDroppedBooks;
	}

	public Double getMeanScore() {
		return meanScore;
	}

	public void setMeanScore(Double meanScore) {
		this.meanScore = meanScore;
	}

	public String getLastBook() {
		return lastBook;
	}

	public void setLastBook(String lastBook) {
		this.lastBook = lastBook;
	}

	public Integer getQtyUserReadLastBook() {
		return qtyUserReadLastBook;
	}

	public void setQtyUserReadLastBook(Integer qtyUserReadLastBook) {
		this.qtyUserReadLastBook = qtyUserReadLastBook;
	}

	public Integer getQtyUserDroppedLastBook() {
		return qtyUserDroppedLastBook;
	}

	public void setQtyUserDroppedLastBook(Integer qtyUserDroppedLastBook) {
		this.qtyUserDroppedLastBook = qtyUserDroppedLastBook;
	}

	public Double getMeanScoreLastBook() {
		return meanScoreLastBook;
	}

	public void setMeanScoreLastBook(Double meanScoreLastBook) {
		this.meanScoreLastBook = meanScoreLastBook;
	}

	public String getMostReadBook() {
		return mostReadBook;
	}

	public void setMostReadBook(String mostReadBook) {
		this.mostReadBook = mostReadBook;
	}

	public Integer getQtyUserReadMostReadBook() {
		return qtyUserReadMostReadBook;
	}

	public void setQtyUserReadMostReadBook(Integer qtyUserReadMostReadBook) {
		this.qtyUserReadMostReadBook = qtyUserReadMostReadBook;
	}

	public Integer getQtyUserDroppedMostReadBook() {
		return qtyUserDroppedMostReadBook;
	}

	public void setQtyUserDroppedMostReadBook(Integer qtyUserDroppedMostReadBook) {
		this.qtyUserDroppedMostReadBook = qtyUserDroppedMostReadBook;
	}

	public Double getMeanScoreMostReadBook() {
		return meanScoreMostReadBook;
	}

	public void setMeanScoreMostReadBook(Double meanScoreMostReadBook) {
		this.meanScoreMostReadBook = meanScoreMostReadBook;
	}

	public String getMostRatedBook() {
		return mostRatedBook;
	}

	public void setMostRatedBook(String mostRatedBook) {
		this.mostRatedBook = mostRatedBook;
	}

	public Integer getQtyUserReadMostRatedBook() {
		return qtyUserReadMostRatedBook;
	}

	public void setQtyUserReadMostRatedBook(Integer qtyUserReadMostRatedBook) {
		this.qtyUserReadMostRatedBook = qtyUserReadMostRatedBook;
	}

	public Integer getQtyUserDroppedMostRatedBook() {
		return qtyUserDroppedMostRatedBook;
	}

	public void setQtyUserDroppedMostRatedBook(Integer qtyUserDroppedMostRatedBook) {
		this.qtyUserDroppedMostRatedBook = qtyUserDroppedMostRatedBook;
	}

	public Double getMeanScoreMostRatedBook() {
		return meanScoreMostRatedBook;
	}

	public void setMeanScoreMostRatedBook(Double meanScoreMostRatedBook) {
		this.meanScoreMostRatedBook = meanScoreMostRatedBook;
	}

	public Integer getQtyFollowers() {
		return qtyFollowers;
	}

	public void setQtyFollowers(Integer qtyFollowers) {
		this.qtyFollowers = qtyFollowers;
	}

	public Integer getFemaleFollowers() {
		return femaleFollowers;
	}

	public void setFemaleFollowers(Integer femaleFollowers) {
		this.femaleFollowers = femaleFollowers;
	}

	public Integer getMeanAgeFollowers() {
		return meanAgeFollowers;
	}

	public void setMeanAgeFollowers(Integer meanAgeFollowers) {
		this.meanAgeFollowers = meanAgeFollowers;
	}

	public Integer getMaleFollowers() {
		return maleFollowers;
	}

	public void setMaleFollowers(Integer maleFollowers) {
		this.maleFollowers = maleFollowers;
	}
}
