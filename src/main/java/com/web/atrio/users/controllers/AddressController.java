package com.web.atrio.users.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.atrio.exceptions.BadRequestException;
import com.web.atrio.exceptions.NotFoundException;
import com.web.atrio.exceptions.UnauthorizedException;
import com.web.atrio.users.models.Account;
import com.web.atrio.users.models.Address;
import com.web.atrio.users.repositories.AccountRepository;
import com.web.atrio.users.repositories.AddressRepository;
import com.web.atrio.users.utilities.UserService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Set> createAddress(@RequestBody Address address, HttpServletRequest request) {
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		address.setUser(userLoggedIn);
		addressRepository.save(address);
		Set<Address> addresses = userLoggedIn.getAddresses();
		addresses.add(address);
		return new ResponseEntity<Set>(addresses, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{addressId}", method = RequestMethod.DELETE)
	public ResponseEntity<Set> deleteAddress(@PathVariable(value = "addressId") Long addressId,
			HttpServletRequest request) throws UnauthorizedException, BadRequestException {
		Address address = addressRepository.findOne(addressId);
		if (address == null) {
			throw new BadRequestException();
		}
		Account user = address.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		Set<Address> addresses = new HashSet<Address>();
		if (!user.equals(userLoggedIn) && !userLoggedIn.getRoles().contains("ADMIN")) {
			throw new UnauthorizedException();
		}
		addresses = user.getAddresses();
		addresses.remove(address);
		addressRepository.delete(address);
		return new ResponseEntity<Set>(addresses, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<Address> updateAddress(@RequestBody Address address, HttpServletRequest request)
			throws UnauthorizedException {
		Address addressFromDatabase = addressRepository.findOne(address.getId());

		Account user = addressFromDatabase.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));

		address.setUser(user);
		// Allowed to modify a address if it is yours or you are an admin
		if (user.equals(userLoggedIn) || userLoggedIn.getRoles().contains("ADMIN")) {
			address = addressRepository.save(address);
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		} else {
			throw new UnauthorizedException();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Set> getAddresss(HttpServletRequest request) {
		Account user = accountRepository.findByUsername(UserService.getUser(request));
		// Return the addresses of the user who sent the request
		return new ResponseEntity<Set>(user.getAddresses(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{addressId}", method = RequestMethod.GET)
	public ResponseEntity<Address> getAddress(@PathVariable(value = "addressId") Long addressId,
			HttpServletRequest request) throws NotFoundException, UnauthorizedException {
		Address address = addressRepository.findOne(addressId);
		if (address == null) {
			throw new NotFoundException();
		}
		Account user = address.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		// Allow to view address if you're an admin or it is your address
		if ((user.equals(userLoggedIn)) || userLoggedIn.getRoles().contains("ADMIN")) {
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		} else {
			throw new UnauthorizedException();
		}
	}
}
