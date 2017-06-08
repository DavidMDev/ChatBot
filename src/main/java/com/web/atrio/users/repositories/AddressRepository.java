package com.web.atrio.users.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.web.atrio.users.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
	public List<Address> findByPostcode(String postcode);
}
