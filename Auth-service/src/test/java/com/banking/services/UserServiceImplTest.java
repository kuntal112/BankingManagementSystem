package com.banking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.banking.entities.User;
import com.banking.error.UserNotFoundException;
import com.banking.repositories.UserRepository;

@SpringBootTest
class UserServiceImplTest {
	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;

	private User user;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		String dateString = "1997-11-06";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dateOfBirth = formatter.parse(dateString);
		user = User.builder()
				.id(1L)
				.accType("savings")
				.address("Kolkata")
				.contactNo("1234567890")
				.country("India")
				.dob(dateOfBirth)
				.email("user1@user1.com")
				.name("User1")
				.pan("adxpd3331k")
				.password("user11234")
				.state("WestBengal")
				.username("user1")
				.build();
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

	}

	@Test
	public void whenValidUserId_returnUserData() throws UserNotFoundException {
		Long userId = 1L;
		User userFound = userService.getUserById(userId);
		assertEquals(userId, userFound.getId());
	}

	@Test
	public void whenInvalidUserId_throwUserNotFound_Exception() throws UserNotFoundException {
		assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
	}

	@Test
	public void whenSaveUser_returnSavedUser() {
		User savedUser = userService.saveUser(user);
		assertEquals(user, savedUser);
	}

	@Test
	public void whenUpdateUserWithValidUserId_returnUser() throws UserNotFoundException {
		User updatedUser = userService.updateUser(1L, user);
		assertEquals(user, updatedUser);
	}

	@Test
	public void whenUpdateUserWithValidUserId_throwUserNotFound_Exception() throws UserNotFoundException {
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(2L, user));
	}

}
