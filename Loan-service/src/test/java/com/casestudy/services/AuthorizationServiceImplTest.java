package com.casestudy.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.casestudy.feign.AuthorizationFeign;
import com.casestudy.models.AuthResponse;
@SpringBootTest
class AuthorizationServiceImplTest {
	@Autowired
	AuthorizationService authorizationService;
	@MockBean
	private AuthorizationFeign authClient;
	private String token;
	AuthResponse response;
	@BeforeEach
	void setUp() throws Exception {
	 token="abcd.xyz.pqr";
		response=AuthResponse.builder()
							  .id(1L)
							  .valid(true)
							  .build();
		Mockito.when(authClient.validate(token)).thenReturn(new ResponseEntity<AuthResponse>(response,HttpStatus.ACCEPTED));
	}

	@Test
	public void whenValidToken_returnAuthResponse() {
		
		AuthResponse validateJwt = authorizationService.validateJwt(token);
		assertEquals(response, validateJwt);
		
	}

}
