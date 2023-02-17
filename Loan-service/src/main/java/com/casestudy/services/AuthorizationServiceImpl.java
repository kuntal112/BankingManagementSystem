package com.casestudy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.feign.AuthorizationFeign;
import com.casestudy.models.AuthResponse;



@Service
public class AuthorizationServiceImpl implements AuthorizationService{

	@Autowired
	private AuthorizationFeign authClient;
	
	@Override
	public AuthResponse validateJwt(String jwt) {
		AuthResponse authenticationResponse = authClient.validate(jwt).getBody();
		return authenticationResponse;
	}
	
	
}
