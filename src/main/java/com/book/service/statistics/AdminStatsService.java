package com.book.service.statistics;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.statistics.AdminStatsDTO;
import com.book.model.entity.User;
import com.book.model.entity.book.Book;
import com.book.model.enumerated.Gender;
import com.book.model.enumerated.Genre;
import com.book.model.enumerated.UserRole;
import com.book.model.repository.books.BookRepository;
import com.book.model.repository.users.UserRepository;

@Service
public class AdminStatsService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@SuppressWarnings("deprecation")
	public AdminStatsDTO getAdminStatisticsDTO() {
		AdminStatsDTO dto = initializeAdminStatsDTO();
		
		Date date = new Date();
		
		List<User> users = userRepository.findAll();
		
		users.stream().forEach(
			user -> {
				if (user.getRoles().contains(UserRole.USER)) {
					dto.setUserRegisters(dto.getUserRegisters() + 1);
					
					Instant instant = user.getCreateTime().toInstant(ZoneOffset.UTC);
					Date userDate = Date.from(instant);

					if (date.getMonth() == userDate.getDate() && date.getYear() == userDate.getYear()) {
						dto.setNewUsersRegistersThisMonth(dto.getNewUsersRegistersThisMonth() + 1);
					}
					
					dto.setMeanAgeFromUserRegisters(dto.getMeanAgeFromUserRegisters() + user.getAge());

					if (user.getGender() == Gender.MALE) {
						dto.setQtyMaleUsersRegisters(dto.getQtyMaleUsersRegisters() + 1);
					} else if (user.getGender() == Gender.FEMALE) {
						dto.setQtyFemaleUsersRegisters(dto.getQtyFemaleUsersRegisters() + 1);
					}
					
				} else if (user.getRoles().contains(UserRole.AUTHOR)) {
					dto.setAuthorsRegisters(dto.getAuthorsRegisters() + 1);
				} else {
					dto.setAdminRegisters(dto.getAdminRegisters() + 1);
				}
		});
		
		List<Book> books = this.bookRepository.findAll();
		
		dto.setBooksWritten(books.size());
		
		dto.setMeanAgeFromUserRegisters(dto.getMeanAgeFromUserRegisters() / dto.getUserRegisters());

		Genre genre = getMostReadGenre();
		
		if (genre != null) {
			dto.setMostReadGenre(genre);
		}
		
		return dto;
	}

	private AdminStatsDTO initializeAdminStatsDTO() {
		AdminStatsDTO dto = new AdminStatsDTO();
		
		dto.setAdminRegisters(0);
		dto.setBooksWritten(0);
		dto.setAuthorsRegisters(0);
		dto.setMeanAgeFromUserRegisters(0);
		dto.setMostReadGenre(null);
		dto.setNewUsersRegistersThisMonth(0);
		dto.setQtyFemaleUsersRegisters(0);
		dto.setQtyMaleUsersRegisters(0);
		dto.setUserRegisters(0);
		
		return dto;
	}
	
	private Genre getMostReadGenre() {
		
		Optional<String> mostReadedGenre = this.bookRepository.findMaxGenreRead();
		if (mostReadedGenre.isPresent()) {
			return Genre.valueOf(mostReadedGenre.get());				
		} else {
			return null;			
		}
	}
}
