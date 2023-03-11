package com.pool.modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KAFKA_USER_PROFILE")
public class KafkaUserprofile implements Serializable {
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

	public KafkaUserprofile() {

	}

	public Integer getUserId() {
		return userId;
	}

	public KafkaUserprofile setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public KafkaUserprofile setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public KafkaUserprofile setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public KafkaUserprofile setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public KafkaUserprofile setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public KafkaUserprofile setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public KafkaUserprofile setEmail(String email) {
		this.email = email;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public KafkaUserprofile setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

}
