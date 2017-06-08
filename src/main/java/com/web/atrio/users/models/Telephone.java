package com.web.atrio.users.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Telephone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String number;
	@NotNull
	private String type;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user")
	private Account user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public Telephone(String number, String type) {
		super();
		this.number = number;
		this.type = type;
	}

	public Telephone() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		Telephone that = (Telephone) obj;
		if(this.id == that.id && this.user == that.user){
			return true;
		} else {
			return false;
		}
	}

}
