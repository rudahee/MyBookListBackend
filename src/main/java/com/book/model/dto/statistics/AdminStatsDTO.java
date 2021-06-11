package com.book.model.dto.statistics;

import com.book.model.enumerated.Genre;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents a statistics for an admin
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class AdminStatsDTO {

	private Integer userRegisters;
	private Integer authorsRegisters;
	private Integer adminRegisters;
	private Integer newUsersRegistersThisMonth;
	private Integer booksWritten;
	private Genre mostReadGenre;
	private Integer meanAgeFromUserRegisters;
	private Integer qtyMaleUsersRegisters;
	private Integer qtyFemaleUsersRegisters;
	
	public AdminStatsDTO() {
		super();
	}

	public AdminStatsDTO(Integer userRegisters, Integer authorsRegisters, Integer adminRegisters,
			Integer newUsersRegistersThisMonth, Integer booksWritten, Genre mostReadGenre,
			Integer meanAgeFromUserRegisters, Integer qtyMaleUsersRegisters, Integer qtyFemaleUsersRegisters) {
		super();
		this.userRegisters = userRegisters;
		this.authorsRegisters = authorsRegisters;
		this.adminRegisters = adminRegisters;
		this.newUsersRegistersThisMonth = newUsersRegistersThisMonth;
		this.booksWritten = booksWritten;
		this.mostReadGenre = mostReadGenre;
		this.meanAgeFromUserRegisters = meanAgeFromUserRegisters;
		this.qtyMaleUsersRegisters = qtyMaleUsersRegisters;
		this.qtyFemaleUsersRegisters = qtyFemaleUsersRegisters;
	}

	public Integer getUserRegisters() {
		return userRegisters;
	}

	public void setUserRegisters(Integer userRegisters) {
		this.userRegisters = userRegisters;
	}

	public Integer getAuthorsRegisters() {
		return authorsRegisters;
	}

	public void setAuthorsRegisters(Integer authorsRegisters) {
		this.authorsRegisters = authorsRegisters;
	}

	public Integer getAdminRegisters() {
		return adminRegisters;
	}

	public void setAdminRegisters(Integer adminRegisters) {
		this.adminRegisters = adminRegisters;
	}

	public Integer getNewUsersRegistersThisMonth() {
		return newUsersRegistersThisMonth;
	}

	public void setNewUsersRegistersThisMonth(Integer newUsersRegistersThisMonth) {
		this.newUsersRegistersThisMonth = newUsersRegistersThisMonth;
	}

	public Integer getBooksWritten() {
		return booksWritten;
	}

	public void setBooksWritten(Integer booksWritten) {
		this.booksWritten = booksWritten;
	}

	public Genre getMostReadGenre() {
		return mostReadGenre;
	}

	public void setMostReadGenre(Genre mostReadGenre) {
		this.mostReadGenre = mostReadGenre;
	}

	public Integer getMeanAgeFromUserRegisters() {
		return meanAgeFromUserRegisters;
	}

	public void setMeanAgeFromUserRegisters(Integer meanAgeFromUserRegisters) {
		this.meanAgeFromUserRegisters = meanAgeFromUserRegisters;
	}

	public Integer getQtyMaleUsersRegisters() {
		return qtyMaleUsersRegisters;
	}

	public void setQtyMaleUsersRegisters(Integer qtyMaleUsersRegisters) {
		this.qtyMaleUsersRegisters = qtyMaleUsersRegisters;
	}

	public Integer getQtyFemaleUsersRegisters() {
		return qtyFemaleUsersRegisters;
	}

	public void setQtyFemaleUsersRegisters(Integer qtyFemaleUsersRegisters) {
		this.qtyFemaleUsersRegisters = qtyFemaleUsersRegisters;
	}
}
