package com.book.service.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.book.model.enumerated.Genre;

/* This class manages the genres enum
 * 
 * @author J. Rubén Daza
 */
@Service
public class GenreService {

	/* Obtain all genres
	 *
	 *@return List<Genre>
	 */
	public List<Genre> getAllGenres() {
		
		ArrayList<Genre> genres = new ArrayList<Genre>();
		
		genres.addAll(EnumSet.allOf(Genre.class));	
		
		return genres;
	}
}
