package com.banking.services;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.banking.entities.User;
import com.banking.error.UserNotFoundException;
import com.banking.models.CustomUserDetails;
import com.banking.repositories.UserRepository;
@SpringBootTest
class CustomUserDetailsServiceTest {
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@MockBean
	private UserRepository userRepository;
	
	private User user;
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		String dateString="1997-11-06";
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		Date dateOfBirth=formatter.parse(dateString);
		user=User.builder()
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
		
		Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByUsername("user2")).thenReturn(Optional.empty());
	}

	@Test
	public void whenLoadUserWithValidUserName_returnUserDetails() {
		String username="user1";
		UserDetails loadUserByUsername = customUserDetailsService.loadUserByUsername(username);
		assertEquals(username, loadUserByUsername.getUsername());
	}
	@Test
	public void whenLoadUserWithIinvalidUserName_throwUserNotFoundException() {
		String username="user2";
		 assertThrows(IllegalArgumentException.class,()->customUserDetailsService.loadUserByUsername(username)
					);
	}
	
	

}
