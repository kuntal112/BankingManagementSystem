package com.casestudy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class FallbackMethodController {
	@GetMapping("/authservicefallback")
	public String authServiceFallbackMethod() {
		return "AUTH-Service is taking longer than expected";
	}
	@PostMapping("/authservicefallback")
	public String postAuthServiceFallbackMethod() {
		return "AUTH-Service is taking longer than expected";
	}
	@GetMapping("/loanservicefallback")
	public String loanServiceFallbackMethod() {
		return "LOAN-Service is taking longer than expected";
	}
}
