package com.book.service.entity.relationships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exception.UserManagementException;
import com.book.model.dto.books.BookForUserListDTO;
import com.book.model.dto.books.BookUserCustomerDTO;
import com.book.model.entity.User;
import com.book.model.entity.customer.UserCustomer;
import com.book.model.entity.relationship.BookUserCustomer;
import com.book.model.enumerated.BodyErrorCode;
import com.book.model.repository.relationships.BookUserCustomerRepository;
import com.book.model.repository.users.UserRepository;
import com.book.service.abstracts.BaseService;
import com.book.service.converters.books.BookUserCustomerConverter;


/* Implements BaseService for BookUserCustomer
 * 
 * @author J. Rubén Daza
 * 
 * @see BaseService
 */
@Service
public class BookUserCustomerService extends BaseService<BookUserCustomer, BookUserCustomerDTO, BookUserCustomerConverter, BookUserCustomerRepository, Long> {

	@Autowired
	private UserRepository userRepository;
	
	/* 
	 * this method get user book list from user id.
	 * 
	 * @param idUser Long
	 *
	 * @return List<BookForUserListDTO>
	 */
	public List<BookForUserListDTO> getBookListFromUserId(Long idUser) throws UserManagementException {
		Optional<User> user = this.userRepository.findById(idUser);
		ArrayList<BookForUserListDTO> dtos = new ArrayList<BookForUserListDTO>();
		
		if (user.isPresent()) { //if user exists
			if (user.get().getCustomer().getClass().isAssignableFrom(UserCustomer.class)) { //if user is user(role)
				UserCustomer userCustomer = (UserCustomer) user.get().getCustomer();
				
				userCustomer.getBooks().forEach(buc -> { 
					BookForUserListDTO auxBook = new BookForUserListDTO();
					auxBook.setId(buc.getBook().getId());
					auxBook.setName(buc.getBook().getName());
					auxBook.setImageUrl(buc.getBook().getImageUrl());
					
					if(buc.getBook().getSaga() != null) {
						auxBook.setSagaName(buc.getBook().getSaga().getName());						
					}
					
					auxBook.setTotalPages(buc.getBook().getPages());
					auxBook.setComment(buc.getComment());
					auxBook.setPagesReaded(buc.getPagesReaded());
					auxBook.setScore(buc.getScore());
					auxBook.setStatus(buc.getStatus());
					dtos.add(auxBook);
				});
				
				return dtos;
			} else {
				throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
			}
		} else {
			throw new UserManagementException(BodyErrorCode.USER_NOT_EXIST);
		}
	}
	
	/* 
	 * this method get booklist for user from book id and user id.
	 * 
	 * @param idUser Long
	 * @param idBook Long
	 *
	 * @return List<BookForUserListDTO>
	 */
	public BookForUserListDTO getStatusBookFromUser(Long idUser, Long idBook) {
		User user = this.userRepository.findById(idUser).get();
		BookForUserListDTO dto = new BookForUserListDTO();

		List<BookUserCustomer> bookUserCustomerList = this.repository.findAll();
		
		BookUserCustomer bucRelation = bookUserCustomerList.stream()
										.filter(buc -> buc.getUser().getId() == user.getCustomer().getId() && buc.getBook().getId() == idBook)
										.findFirst()
										.get();
		
		
		// We change what we are going to add to the disc according to the status of the book 
		if (bucRelation.getComment() != null) {
			dto.setComment(bucRelation.getComment());			
		}
		if (bucRelation.getPagesReaded() != null) {
			dto.setPagesReaded(bucRelation.getPagesReaded());			
		}
		if (bucRelation.getScore() != null) {
			dto.setScore(bucRelation.getScore());			
		}
		
		dto.setStatus(bucRelation.getStatus());
		
		return dto;
	}
	
	/* 
	 * this method edit BookUserCustomer.
	 * 
	 * @param id Long id
	 * @param dto BookUserCustomerDTO
	 *
	 * @return BookUserCustomerDTO
	 */
	public BookUserCustomerDTO editBookUserCustomer(BookUserCustomerDTO dto, Long id) {
		User user = this.userRepository.findById(id).get();
		
		List<BookUserCustomer> bookUserCustomerList = this.repository.findAll();
		
		BookUserCustomer bucToEdit = bookUserCustomerList.stream()
				.filter(buc -> buc.getUser().getId() == user.getCustomer().getId() && buc.getBook().getId() == dto.getBookId())
				.findFirst()
				.get();
		
		bucToEdit.setComment(dto.getComment());
		bucToEdit.setPagesReaded(dto.getPagesReaded());
		bucToEdit.setScore(dto.getScore());
		bucToEdit.setStatus(dto.getStatus());
		
		// for some reason it won't let me edit it with the 'save' method in JpaRepository
		this.repository.deleteById(bucToEdit.getId());
		
		return DtoConverter.fromEntity(this.repository.save(bucToEdit));
		
	}
}
