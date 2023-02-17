package com.casestudy.services;

import com.casestudy.models.AuthResponse;

public interface AuthorizationService {
	
	public AuthResponse validateJwt(String jwt);
}
