package com.book.model.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
	MALE, FEMALE, NONE
}
