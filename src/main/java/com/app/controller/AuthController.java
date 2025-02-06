package com.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.model.Customer;
import com.app.repository.CustomerRepository;
import com.app.service.AuthenticateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final CustomerRepository customerRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticateService authenticateService;
	 
	@PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(LocalDate.now());
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
 
        }
        return null;
    }
	
	@PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = null;
        try {
        	response = authenticateService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (BadCredentialsException e ) {
			 throw e;
		}catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/auth/loginOTP")
    public ResponseEntity<Object> loginOTP(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = null;
        try {
        	response = authenticateService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Username or password invalid" + e.getMessage());
		}catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if (!customers.isEmpty()) {
            return customers.get(0);
        } else {
            return null;
        }
    }

}
