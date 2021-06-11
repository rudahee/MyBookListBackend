package com.book.service.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.model.dto.RecommendationDTO;
import com.book.model.dto.books.BookForRecommendationDTO;
import com.book.model.entity.User;
import com.book.model.entity.book.Book;
import com.book.model.entity.customer.AuthorCustomer;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.Friendship;
import com.book.model.enumerated.Genre;
import com.book.model.enumerated.TypeRecommendation;
import com.book.model.repository.books.BookRepository;
import com.book.model.repository.users.AuthorCustomerRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.converters.books.BookForRecommendationConverter;

@Service
public class RecommendationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorCustomerRepository authorRepository;
	
	@Autowired BookForRecommendationConverter dtoConverter;
	
	public List<RecommendationDTO> getRecommendations(Long id) {
		ArrayList<RecommendationDTO> dtos = new ArrayList<RecommendationDTO>();
		User user = this.userRepository.findById(id).get();
		
		if(((UserCustomer) user.getCustomer()).getBooks().size() > 0) {
			dtos.add(getBooksForFavoriteGenre(user));			
		}
		if (((UserCustomer) user.getCustomer()).getFriends().size() > 0) {
			dtos.add(getBooksForARandomFriend(user));			
		}
		if (((UserCustomer) user.getCustomer()).getFollows().size() > 0) {
			dtos.add(getBooksForARandomFollowAuthor(user));
		}
		
		dtos.add(getBooksForARandomAuthor());
		dtos.add(getRandomBooks());
		
		return dtos;
	}
	
	private RecommendationDTO getBooksForFavoriteGenre(User user) {
		RecommendationDTO dto = new RecommendationDTO();
		List<BookForRecommendationDTO> booksDTO = new ArrayList<BookForRecommendationDTO>();
		UserCustomer customer = (UserCustomer) user.getCustomer();
		Random rnd = new Random();
		
		Genre genre = Genre.valueOf(userRepository.findMaxGenreReadByUserCustomerId(customer.getId()).get());

		String[] sentences = {
			"Since " + genre.getGenre() + " is one of your favorites genres, you might like one of these: ",
			"Would you like to discover more books about " + genre.getGenre() + "?",
			"We know you like " + genre.getGenre() + ", we have chosen these books for you: "
		};
		
		List<Book> books = bookRepository.findBooksByGenreWithoutDistinct(genre.name());
		
		
		dto.setSentence(sentences[rnd.nextInt(sentences.length-1)]);
		
		dto.setType(TypeRecommendation.FAVORITEGENRE.getCode());
		
		Collections.shuffle(books);
		
		if (booksDTO.size() > 10) {
			booksDTO = dtoConverter.fromEntities(books.subList(0, 10));
		} else {
			booksDTO = dtoConverter.fromEntities(books);
		}
		
		dto.setBooks(booksDTO);
		
		return dto;
	}

	private RecommendationDTO getBooksForARandomFriend(User user) {
		RecommendationDTO dto = new RecommendationDTO();
		List<Book> books = new ArrayList<Book>();		
		Random rnd = new Random();
		UserCustomer friend;		
		UserCustomer customer = (UserCustomer) user.getCustomer(); 
		
		Friendship friendship = customer
									.getFriends()
									.stream()
									.filter(f -> f.getAccepted() == true)
									.collect(Collectors.toCollection(ArrayList::new))
									.get(rnd.nextInt(customer.getFriends().size() - 1));
			
		if (friendship.getFriend().getId() != customer.getId()) {
			friend = friendship.getFriend();
		} else {
			friend = friendship.getRequester();
		}
		
		String[] sentences = {
				"Your friend " + friend.getUser().getUsername() + " has read some of these books: ",
				"As you are a friend of " + friend.getUser().getUsername() + ", we think these books might interest you:"
		};
		
		
		if (friend.getBooks().size() > 10) {
			friend.getBooks().subList(0, 10).forEach(b -> {
				books.add(b.getBook());
			});;
			
			dto.setBooks(dtoConverter.fromEntities(books));
		} else {
			friend.getBooks().forEach(b -> {
				books.add(b.getBook());
			});;
			
			dto.setBooks(dtoConverter.fromEntities(books));
		}
		dto.setSentence(sentences[rnd.nextInt(sentences.length-1)]);
		dto.setType(TypeRecommendation.FRIENDSBOOK.getCode());
	
		return dto;
	}
	
	private RecommendationDTO getBooksForARandomFollowAuthor(User user) {
		RecommendationDTO dto = new RecommendationDTO();		
		List<Book> books = new ArrayList<Book>();		
		Random rnd = new Random();
		UserCustomer customer = (UserCustomer) user.getCustomer(); 
		
		AuthorCustomer author;
		if (customer.getFollows().size() > 1) {
			author = customer.getFollows().get(rnd.nextInt(customer.getFollows().size() -1));			
		} else {
			author = customer.getFollows().get(0);
		}
		
		
		
		String[] sentences = {
				"Would you like to read something by " + author.getUser().getName() + " " + author.getUser().getSurname() + "?",
				"We recommend some books by " + author.getUser().getName() + " " + author.getUser().getSurname() + ":",
				"Do you want to know more " + author.getUser().getName() + " " + author.getUser().getSurname() +  " books?"
		};
		
		if (author.getBooks().size() > 10) {
			books.addAll(author.getBooks().subList(0, 10));
			dto.setBooks(dtoConverter.fromEntities(books));
		} else {
			books.addAll(author.getBooks());
			dto.setBooks(dtoConverter.fromEntities(books));
		}
		dto.setSentence(sentences[rnd.nextInt(sentences.length-1)]);
		dto.setType(TypeRecommendation.AUTHORSBOOK.getCode());
	
		return dto;
		
	}
	
	private RecommendationDTO getBooksForARandomAuthor() {
		List<AuthorCustomer> authors = this.authorRepository.findAll();
		RecommendationDTO dto = new RecommendationDTO();		
		Random rnd = new Random();

		AuthorCustomer selectedAuthor = authors.get(rnd.nextInt(authors.size() - 1));
		
		String[] sentences = {
				"Discover something about " + selectedAuthor.getUser().getName() + " " + selectedAuthor.getUser().getSurname() + ", we recommend these books:",
				"We have selected some of " + selectedAuthor.getUser().getName() + " " + selectedAuthor.getUser().getSurname() +"'s books for you:",
		};
		
		List<Book> books = selectedAuthor.getBooks();
		Collections.shuffle(books);
		
		if (selectedAuthor.getBooks().size() > 10) {
			dto.setBooks(dtoConverter.fromEntities(books.subList(0, 10)));
		} else {
			dto.setBooks(dtoConverter.fromEntities(books));
		}

		dto.setSentence(sentences[rnd.nextInt(sentences.length-1)]);
		dto.setType(TypeRecommendation.RANDOMAUTHOR.getCode());
	
		return dto;
	}

	private RecommendationDTO getRandomBooks() {
		List<Book> books = bookRepository.findAll();
		RecommendationDTO dto = new RecommendationDTO();		
		Random rnd = new Random();
		
		String[] sentences = {
				"Would you like to read something new?",
				"Discover a new book:",
				"You may be interested in one of these books:"
		};
		
		Collections.shuffle(books);
		
		if (books.size() > 10) {
			dto.setBooks(dtoConverter.fromEntities(books.subList(0, 10)));
		} else {
			dto.setBooks(dtoConverter.fromEntities(books));
		}
		
		dto.setSentence(sentences[rnd.nextInt(sentences.length-1)]);
		dto.setType(TypeRecommendation.RANDOMBOOK.getCode());
	
		return dto;
	}
}
