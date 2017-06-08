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
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String houseNumber;
	@NotNull
	private String streetName;
	private String addressDetails;
	@NotNull
	private String postcode;
	@NotNull
	private String city;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addresDetails) {
		this.addressDetails = addresDetails;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public Address(String houseNumber, String streetName, String addressDetails, String postcode) {
		super();
		this.houseNumber = houseNumber;
		this.streetName = streetName;
		this.addressDetails = addressDetails;
		this.postcode = postcode;
	}

	public Address() {
		super();
	}
	
	@Override
	public boolean equals(Object obj){
		Address that = (Address) obj;
		if(this.id == that.id && this.user == that.user){
			return true;
		} else {
			return false;
		}
	}
}
