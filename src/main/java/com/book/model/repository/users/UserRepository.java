package com.book.model.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.book.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameAndPassword(String username, String password);

	boolean existsUserByEmail(String email);
	
	boolean existsUserByUsername(String username);
	
	@Query(value="SELECT DISTINCT subquery.genre "
		    	+ "FROM (SELECT bg.genres AS genre, COUNT(bg.genres) AS countGenre "
				    	+ "FROM book_user_customer buc, book_genres bg, book b "
				            + "WHERE b.id = bg.book_id "
				            + "AND buc.book_id = b.id "
				            + "AND buc.user_customer_id = ?1 "
				            + "GROUP BY bg.genres "
				            + "ORDER BY countGenre DESC "
				            + "LIMIT 1) AS subquery; ", nativeQuery=true)
	public Optional<String> findMaxGenreReadByUserCustomerId(Long id);

	@Query(value="SELECT DISTINCT subquery.FullName "
				+ "FROM (SELECT COUNT(ab.author_id) as countAuthors, CONCAT(u.name, ' ', u.surname) as FullName "
						+ "FROM user_customer uc, book_user_customer buc, book b, author_book ab, author_customer ac, user u, customer c "
						+ "WHERE uc.id = buc.user_customer_id "
						+ "AND buc.book_id = b.id "
						+ "AND b.id = ab.book_id "
						+ "AND ab.author_id = ac.id "
						+ "AND ac.id = c.id "
						+ "AND c.user_id = u.id "
						+ "AND uc.id = ?1 "
						+ "GROUP BY FullName "
						+ "ORDER BY countAuthors DESC "
						+ "LIMIT 1) AS subquery ", nativeQuery=true)
	public Optional<String> findMaxUserReadByUserCustomerId(Long id);
}
