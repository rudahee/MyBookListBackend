package com.book.model.repository.books;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.book.model.entity.book.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value="SELECT DISTINCT subquery.genre "
	    			+ "FROM (SELECT bg.genres AS genre, COUNT(bg.genres) AS countGenre "
				    	+ "FROM book_genres bg, book b "
				    		+ "WHERE b.id = bg.book_id "
				            + "GROUP BY bg.genres "
				            + "ORDER BY countGenre DESC "
				            + "LIMIT 1) AS subquery", nativeQuery=true)
	public Optional<String> findMaxGenreRead();
	
	
	@Query(value="SELECT DISTINCT * "
					+ "FROM book b, book_genres bg "
					+ "WHERE b.id = bg.book_id "
					+ "AND bg.genres = ?1", nativeQuery=true)
	public List<Book> findBooksByGenre(String genre);
	
	@Query(value="SELECT * "
			+ "FROM book b, book_genres bg "
			+ "WHERE b.id = bg.book_id "
			+ "AND bg.genres = ?1", nativeQuery=true)
public List<Book> findBooksByGenreWithoutDistinct(String genre);
}
