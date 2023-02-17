package com.banking.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.entities.User;
import com.banking.error.UserNotFoundException;
import com.banking.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Transactional
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id,User user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepository.findById(id);
		findById.orElseThrow(()->new UserNotFoundException("User Does not exist"));
		user.setId(id);
		user.setPassword(findById.get().getPassword());
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) throws UserNotFoundException {
		Optional<User> findById = userRepository.findById(id);
		findById.orElseThrow(()->new UserNotFoundException("User Does not exist"));
		
		return findById.get();
	}
	
}
