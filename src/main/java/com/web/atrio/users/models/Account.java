package com.web.atrio.users.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.atrio.users.utilities.PasswordEncrypter;
import com.web.atrio.users.utilities.RoleService;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	@Column(unique = true)
	private String username;

	@JsonIgnore
	@NotNull
	@Convert(converter = PasswordEncrypter.class)
	private String password;

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Address> addresses;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Telephone> telephones;

	@NotNull
	private String roles = "";

	public List<String> getRoles() {
		return RoleService.getRoles(this);
	}

	public String sendRolesToRoleService() {
		return this.roles;
	}

	public void setRolesByRoleService(String roles) {
		this.roles = roles;
	}

	public void addRole(String role) {
		RoleService.addRole(this, role);
	}

	public void removeRole(String role) {
		RoleService.removeRole(this, role);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public Set<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	public Account(String email, String username, String password, String firstName, String lastName,
			Set<Address> addresses) {
		super();
		this.addRole("USER");
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
	}

	public Account() {
		super();
		this.addRole("USER");
	}

	@Override
	public boolean equals(Object obj) {
		Account that = (Account) obj;
		if (this.username == that.username && this.email == that.email && this.id == that.id) {
			return true;
		} else
			return false;
	}
}
