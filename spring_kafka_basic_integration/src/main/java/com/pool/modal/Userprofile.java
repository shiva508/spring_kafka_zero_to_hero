package com.pool.modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PROFILE")
public class Userprofile implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String userName;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private Date dateOfBirth;

	public Userprofile() {

	}

	public Integer getUserId() {
		return userId;
	}

	public Userprofile setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public Userprofile setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Userprofile setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Userprofile setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public Userprofile setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Userprofile setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Userprofile setEmail(String email) {
		this.email = email;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Userprofile setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

}
