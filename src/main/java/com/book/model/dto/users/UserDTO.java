package com.book.model.dto.users;

import java.time.LocalDateTime;
import java.util.Set;

import com.book.model.entity.customer.Customer;
import com.book.model.enumerated.Gender;
import com.book.model.enumerated.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* This class represents an user
 * 
 * @author J. Rubén Daza
 */
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	private Long id;
	private String name;
	private String surname;
	private Integer age;
	
	private String username;
	private String password;
	private String email;
		
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
		
	private LocalDateTime lastPasswordChange;
	private LocalDateTime nextPasswordChange;
	
	private Boolean enableAccount;
	
	private Set<UserRole> roles;
	
	private Gender gender;
	private Customer customer;
	private String activationCode;

	public UserDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public LocalDateTime getNextPasswordChange() {
		return nextPasswordChange;
	}

	public void setNextPasswordChange(LocalDateTime nextPasswordChange) {
		this.nextPasswordChange = nextPasswordChange;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Boolean getEnableAccount() {
		return enableAccount;
	}

	public void setEnableAccount(Boolean enableAccount) {
		this.enableAccount = enableAccount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
