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
import com.web.atrio.users.models.Telephone;
import com.web.atrio.users.repositories.AccountRepository;
import com.web.atrio.users.repositories.TelephoneRepository;
import com.web.atrio.users.utilities.UserService;

@RestController
@RequestMapping("/api/telephones")
public class TelephoneController {

	@Autowired
	private TelephoneRepository telephoneRepository;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Set> createTelephone(@RequestBody Telephone telephone, HttpServletRequest request) {
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		telephone.setUser(userLoggedIn);
		telephoneRepository.save(telephone);
		Set<Telephone> telephones = userLoggedIn.getTelephones();
		telephones.add(telephone);
		return new ResponseEntity<Set>(telephones, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{telephoneId}", method = RequestMethod.DELETE)
	public ResponseEntity<Set> deleteTelephone(@PathVariable(value = "telephoneId") Long telephoneId,
			HttpServletRequest request) throws UnauthorizedException, BadRequestException {
		Telephone telephone = telephoneRepository.findOne(telephoneId);
		if (telephone == null) {
			throw new BadRequestException();
		}
		Account user = telephone.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		Set<Telephone> telephones = new HashSet<Telephone>();
		if (!user.equals(userLoggedIn) && !userLoggedIn.getRoles().contains("ADMIN")) {
			throw new UnauthorizedException();
		}
		telephones = user.getTelephones();
		telephones.remove(telephone);
		telephoneRepository.delete(telephone);
		return new ResponseEntity<Set>(telephones, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<Telephone> updateTelephone(@RequestBody Telephone telephone, HttpServletRequest request)
			throws UnauthorizedException {
		Telephone telephoneFromDatabase = telephoneRepository.findOne(telephone.getId());

		Account user = telephoneFromDatabase.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));

		telephone.setUser(user);
		// Allowed to modify a telephone if it is yours or you are an admin
		if (user.equals(userLoggedIn) || userLoggedIn.getRoles().contains("ADMIN")) {
			telephone = telephoneRepository.save(telephone);
			return new ResponseEntity<Telephone>(telephone, HttpStatus.OK);
		} else {
			throw new UnauthorizedException();
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Set> getTelephones(HttpServletRequest request) {
		Account user = accountRepository.findByUsername(UserService.getUser(request));
		// Return the telephones of the user who sent the request
		return new ResponseEntity<Set>(user.getTelephones(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{telephoneId}", method = RequestMethod.GET)
	public ResponseEntity<Telephone> getTelephone(@PathVariable(value = "telephoneId") Long telephoneId,
			HttpServletRequest request) throws NotFoundException, UnauthorizedException {
		Telephone telephone = telephoneRepository.findOne(telephoneId);
		if (telephone == null) {
			throw new NotFoundException();
		}
		Account user = telephone.getUser();
		Account userLoggedIn = accountRepository.findByUsername(UserService.getUser(request));
		// Allow to view telephone if you're an admin or it is your telephone
		if ((user.equals(userLoggedIn)) || userLoggedIn.getRoles().contains("ADMIN")) {
			return new ResponseEntity<Telephone>(telephone, HttpStatus.OK);
		} else {
			throw new UnauthorizedException();
		}
	}
}
