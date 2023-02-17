package com.banking.services;

import com.banking.entities.User;
import com.banking.error.UserNotFoundException;

public interface UserService {
	User saveUser(User user);
	User updateUser(Long id,User user) throws UserNotFoundException;
	User getUserById(Long id) throws UserNotFoundException;
}
