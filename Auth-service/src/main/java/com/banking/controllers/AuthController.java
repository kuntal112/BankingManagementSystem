package com.banking.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entities.User;
import com.banking.error.BadCredentialException;
import com.banking.error.UserNotFoundException;
import com.banking.models.AuthRequest;
import com.banking.models.AuthResponse;
import com.banking.models.CustomUserDetails;
import com.banking.services.CustomUserDetailsService;
import com.banking.services.UserService;
import com.banking.utility.JWTUtility;

import io.swagger.annotations.ApiOperation;





@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
	Logger logger = LoggerFactory.getLogger("Auth-Controller-Logger");
	@Autowired
	private AuthenticationManager authenticationManager;
	@PostMapping("/authenticate")
	@ApiOperation(notes="Returns token to authenticate the microservices", value="authentication")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) throws BadCredentialException{
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
					);
		} catch (Exception e) {
			System.out.println(e);
			throw new BadCredentialException("Username or Password is incorrect");
		}
		
		final UserDetails details=userDetailsService.loadUserByUsername(request.getUsername());
		final String token=jwtUtility.generateToken(details);
		
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	@PostMapping("/register_user")
	public ResponseEntity<User> registerUser( @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser = userService.saveUser(user);
		return  new ResponseEntity<>(saveUser,HttpStatus.CREATED); 
	}
	@PostMapping("/validate")
	public ResponseEntity<AuthResponse> validateJwt(@RequestHeader("Authorization") String jwt){
		
		AuthResponse authenticationResponse = new AuthResponse(null, false);
		ResponseEntity<AuthResponse> response = null;

		//first remove Bearer from Header
		jwt = jwt.substring(7);
		
		//check token
		logger.info("--------JWT :: "+jwt);
		
		
		// check the jwt is proper or not
		final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(jwtUtility.getUsernameFromToken(jwt));
		
		// now validating the jwt
		try {
			if(jwtUtility.validateToken(jwt, userDetails)) {
				authenticationResponse.setId(userDetails.getId());
				
				authenticationResponse.setValid(true);
				response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
				logger.info("Successfully validated the jwt and sending response back!");
			}
			else {
				response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
				logger.error("JWT Token validation failed!");
				throw new Exception("invalid token");
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
			logger.error("Exception occured whil validating JWT : Exception info : " + e.getMessage());
		}
		logger.info("-------- Exiting /validate");
		return response;
	}
	@GetMapping("/details/{id}")
	public ResponseEntity<User> getUserDetailsById(@PathVariable Long id) throws UserNotFoundException{
		User userById = userService.getUserById(id);
		return new ResponseEntity<User>(userById,HttpStatus.OK);
	}
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<User> updateUserDetails(@Valid @PathVariable Long id,@RequestBody User user) throws UserNotFoundException{
		
	
	
		User updatedUser = userService.updateUser(id,user);
		return new ResponseEntity<User>(updatedUser,HttpStatus.CREATED);
	}

}
