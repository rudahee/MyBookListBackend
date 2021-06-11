package com.book.service.statistics;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.statistics.AuthorStatsDTO;
import com.book.model.entity.User;
import com.book.model.entity.book.Book;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.model.enumerated.Gender;
import com.book.model.enumerated.Status;
import com.book.model.repository.users.UserRepository;
import com.book.service.utils.RatingService;

@Service
public class AuthorStatsService {
	@Autowired
	private RatingService ratingService;
	
	@Autowired 
	private UserRepository userRepository;
	
	Book bookMostRated;
	Book bookMostRead;
	Double mostRating = -1d;
	Integer mostReading = -1;
	
	public AuthorStatsDTO getAuthorStatisticsDTO(Long id) {
	
		Optional<User> user = userRepository.findById(id);		
		AuthorStatsDTO dto = initializeAuthorStatsDTO();
		
		/*
		 * FOLLOWERS STATISTICS
		 */
		
		if (user.isPresent()) {
			AuthorCustomer customer = (AuthorCustomer) user.get().getCustomer();
			
			dto.setQtyFollowers(customer.getFollowers().size());
			
			customer.getFollowers().forEach(
				follower -> {
					Gender gender = follower.getUser().getGender();
					if (gender.equals(Gender.FEMALE)) {
						dto.setFemaleFollowers(dto.getFemaleFollowers() + 1);
					} else if (gender.equals(Gender.MALE)) {
						dto.setMaleFollowers(dto.getMaleFollowers() + 1);
					}
					
					dto.setMeanAgeFollowers(dto.getMeanAgeFollowers() + follower.getUser().getAge());
				
			});
			
			/*
			 * BOOKS GENERAL STATISTICS
			 */
			if (dto.getQtyFollowers() != 0) {
				dto.setMeanAgeFollowers(dto.getMeanAgeFollowers() / dto.getQtyFollowers());				
			}
			
			customer.getBooks().forEach(
				book -> {
					dto.setQtyUserReadBooks(dto.getQtyUserReadBooks() + book.getUsers().stream().filter(b -> b.getStatus() == Status.COMPLETED).toArray().length);
					dto.setQtyUserDroppedBooks(dto.getQtyUserDroppedBooks() +  book.getUsers().stream().filter(b -> b.getStatus() == Status.DROPPED).toArray().length);
					book.getUsers().forEach(b -> {
						if (b.getScore() != null) {
							dto.setMeanScore(dto.getMeanScore() + b.getScore());							
						}
												
						Double ratingActual = ratingService.GetMeanScoreFromBook(b.getBook().getId());
						Integer peopleReadActual = b.getBook().getUsers().stream().filter(ucb -> ucb.getStatus() == Status.COMPLETED).toArray().length;
						
						if (mostRating == -1 && !ratingActual.isNaN()) {
							bookMostRated = b.getBook();
							mostRating = ratingActual;
						} else {
							if (mostRating < ratingActual) {
								bookMostRated = b.getBook();
								mostRating = ratingActual;
							}
						}
						
						if (mostReading == -1 && ratingActual != null) {
							bookMostRead = b.getBook();
							mostReading = peopleReadActual;
						} else {
							if (mostReading < peopleReadActual) {
								bookMostRead = b.getBook();
								mostReading = peopleReadActual;
							}
						}
					
					});
			});
			
			dto.setMostRatedBook(bookMostRated.getName());
			dto.setQtyUserReadMostRatedBook(bookMostRated.getUsers().stream().filter(ucb -> ucb.getStatus() == Status.COMPLETED && ucb.getScore() != null).toArray().length);
			System.out.println(dto.getQtyUserReadMostRatedBook());
			dto.setQtyUserDroppedMostRatedBook(bookMostRated.getUsers().stream().filter(ucb -> ucb.getStatus() == Status.DROPPED).toArray().length);
			dto.setMeanScoreMostRatedBook(mostRating);
			
			dto.setMostReadBook(bookMostRead.getName());
			dto.setQtyUserReadMostReadBook(mostReading);
			dto.setQtyUserDroppedMostReadBook(bookMostRated.getUsers().stream().filter(ucb -> ucb.getStatus() == Status.DROPPED).toArray().length);
			dto.setMeanScoreMostReadBook(ratingService.GetMeanScoreFromBook(bookMostRead.getId()));
			
			if ((dto.getQtyUserReadBooks() + dto.getQtyUserDroppedBooks()) != 0 ) {
				dto.setMeanScore( dto.getMeanScore() / (dto.getQtyUserReadBooks() + dto.getQtyUserDroppedBooks()) );
			}
			

			
			/*
			 * LAST BOOK STATISTICS
			 */
			
			Book lastBook = customer.getBooks().get(customer.getBooks().size() - 1);
			dto.setLastBook(lastBook.getName());
			
			dto.setQtyUserReadLastBook(lastBook.getUsers().stream().filter(u -> u.getStatus() == Status.COMPLETED).toArray().length);
			dto.setQtyUserDroppedLastBook(lastBook.getUsers().stream().filter(u -> u.getStatus() == Status.DROPPED).toArray().length);

			lastBook.getUsers().forEach(b -> {
				dto.setMeanScoreLastBook(dto.getMeanScoreLastBook() + b.getScore());
			});
			
			if (lastBook.getUsers().size() != 0) {
				dto.setMeanScoreLastBook(dto.getMeanScoreLastBook() / lastBook.getUsers().size());				
			}
			
		}

		return dto;
	}
	
	
	private AuthorStatsDTO initializeAuthorStatsDTO() {
		AuthorStatsDTO dto = new AuthorStatsDTO();

		dto.setQtyUserReadBooks(0);
		dto.setQtyUserDroppedBooks(0);
		dto.setMeanScore(0d);
		
		dto.setLastBook("");
		dto.setQtyUserReadLastBook(0);
		dto.setQtyUserDroppedLastBook(0);
		dto.setMeanScoreLastBook(0d);
		
		dto.setMostReadBook("");
		dto.setQtyUserReadMostReadBook(0);
		dto.setQtyUserDroppedMostReadBook(0);
		dto.setMeanScoreMostReadBook(0d);
		
		dto.setMostRatedBook("");
		dto.setQtyUserReadMostRatedBook(0);
		dto.setQtyUserDroppedMostRatedBook(0);
		dto.setMeanScoreMostRatedBook(0d);

		dto.setQtyFollowers(0);
		dto.setFemaleFollowers(0);
		dto.setMaleFollowers(0);
		dto.setMeanAgeFollowers(0);
		
		return dto;
	}
}
