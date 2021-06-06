package com.book.service.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.book.model.enumerated.Genre;

@Service
public class GenreService {

	public List<Genre> getAllGenres() {
		
		ArrayList<Genre> genres = new ArrayList<Genre>();
		
		genres.addAll(EnumSet.allOf(Genre.class));	
		
		return genres;
	}
}
