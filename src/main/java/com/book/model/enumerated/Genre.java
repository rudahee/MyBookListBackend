package com.book.model.enumerated;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/* This enumeration is used to define genres of a book.
 * 
 * @author J. Rubén Daza
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Genre implements Serializable {
	AUTOBIOGRAPHICAL("Autobiographical"),
	SCIENTIST("Scientist"),
	SELFHELP("Self Help"),
	ADVENTURE("Adventure"),
	TALES("Tales"),
	SPORTS("Sports"),
	HUMOR("Humor"),
	ANTHOLOGY("Anthology"),
	SCIFI("Sci-Fi"),
	FANTASY("Fantasy"),
	FICTION("Fiction"),
	HISTORICAL("Historical"),
	RELIGIOUS("Religious"),
	SPIRITUAL("Spiritual"),
	NOVEL("Novel"),
	POETRY("Poetry"),
	POLICE("Police"),
	ROMANTIC("Romantic"),
	THEATER("Theater"),
	TERROR("Terror"),
	HEALTH("Health"),
	APHORISMS("Aphorisms"),
	ROLE("Role"),
	POLITICS("Politics"),
	DRAMATIC("Dramatic"),
	COMEDY("Comedy"),
	SATIRICAL("Satirical"),
	PHILOSPHY("Philosphy"),
	TECHNICAL("Technical"),
	PSYCHOLOGICAL("Psychological"),
	WAR("War"),
	SUSPENSE("Suspense"),
	INFANTILE("Infantile"),
	JUVENILE("Juvenile"),
	OTHER("Other");
	
	@JsonSerialize(using = ToStringSerializer.class) 
	private final String genre;

	private Genre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
	
    @JsonCreator
    public static Genre fromJson(@JsonProperty("genre") String genre) {
    	// This method is special. Get a genre from the text that represents it. 
    	return Arrays.stream(values())
                .filter(g -> g.genre.equalsIgnoreCase(genre))
                .findFirst()
                .get();
    	
    }
	
}
