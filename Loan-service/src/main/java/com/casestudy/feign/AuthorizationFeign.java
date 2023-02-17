package com.casestudy.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.casestudy.models.AuthResponse;



@FeignClient(name="AUTH-SERVICE")
public interface AuthorizationFeign{

	@PostMapping("/auth/validate")
	public ResponseEntity<AuthResponse> validate(@RequestHeader("Authorization") String jwt);

}
