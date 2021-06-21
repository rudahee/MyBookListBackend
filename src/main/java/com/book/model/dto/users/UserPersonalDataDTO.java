package com.book.model.dto.users;

public class UserPersonalDataDTO {
	private String urlImage;
	private Integer age;
	private String email;
	
	public UserPersonalDataDTO() {
		super();
	}
	
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
}
