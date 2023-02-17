package com.banking.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.banking.entities.User;
import com.banking.models.CustomUserDetails;
import com.banking.repositories.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		UserDetails user=null;
		Optional<User> findByEmail = repository.findByUsername(email);
		if(findByEmail.isEmpty()) {
			throw new IllegalArgumentException("exception occured");
		}
		user=new CustomUserDetails(findByEmail.get());
		
		return user;
	}
	
}
