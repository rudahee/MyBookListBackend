package com.book.service.statistics;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.model.dto.statistics.UserStatisticsDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.enumerated.Genre;
import com.book.model.enumerated.Status;
import com.book.model.repository.users.UserRepository;
import com.book.service.utils.RatingService;

@Service
public class UserStatsService {

	@Autowired
	private RatingService ratingService;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	@SuppressWarnings("deprecation")
	public UserStatisticsDTO getUserStatisticsDTO(Long id) {
		Optional<User> user = userRepository.findById(id);
		Date date = new Date();
		
		UserStatisticsDTO dto = new UserStatisticsDTO();
		dto.setTotalBooksRead(0);
		dto.setTotalBooksThisYear(0);
		dto.setTotalPagesRead(0);
		dto.setTotalPagesThisYear(0);
		
		if (user.isPresent()) {
			UserCustomer customer = (UserCustomer) user.get().getCustomer();
			
			List<BookUserCustomer> books = customer.getBooks();
			
			books.forEach(book -> {
				if (book.getDate().getYear() == date.getYear()) {
					
					if (!book.getStatus().equals(Status.PLANTOREAD)) {
						if (book.getStatus().equals(Status.COMPLETED) || book.getStatus().equals(Status.DROPPED)) {
							dto.setTotalBooksRead(dto.getTotalBooksRead() + 1);
							dto.setTotalBooksThisYear(dto.getTotalBooksThisYear() + 1);							
						}
						dto.setTotalPagesRead(dto.getTotalPagesRead() + book.getPagesReaded());
						dto.setTotalPagesThisYear(dto.getTotalPagesThisYear() + book.getPagesReaded());
					}
				} else {					
					if (!book.getStatus().equals(Status.PLANTOREAD)) {
						if (book.getStatus().equals(Status.COMPLETED) || book.getStatus().equals(Status.DROPPED)) {
							dto.setTotalBooksRead(dto.getTotalBooksRead() + 1);
						}						
						dto.setTotalPagesRead(dto.getTotalPagesRead() + book.getPagesReaded());
					}
				}				
			});
			
			int minutesRead = 0;
			int hoursRead = 0;
			double daysRead = 0d;
			
			minutesRead = (int) (dto.getTotalPagesRead() * 2.25);
			hoursRead = minutesRead / 60;
			minutesRead = minutesRead % 60;
			daysRead = (hoursRead / 24d);
			
			dto.setDaysRead(daysRead);
			dto.setHoursRead(hoursRead);
			dto.setMinutesRead(minutesRead);
			
			dto.setMeanScore(ratingService.GetUserMeanScore(id));
			
			Optional<String> mostReadedGenre = this.userRepository.findMaxGenreReadByUserCustomerId(customer.getId());
			if (mostReadedGenre.isPresent()) {
				dto.setMostReadGenre(Genre.valueOf(mostReadedGenre.get()));				
			}
			
			Optional<String> mostReadedAuthor = this.userRepository.findMaxUserReadByUserCustomerId(customer.getId());
			if (mostReadedAuthor.isPresent()) {
				dto.setMostReadAuthor(mostReadedAuthor.get());				
			}
			
		}
		
		
		return dto;
	}
	
}
