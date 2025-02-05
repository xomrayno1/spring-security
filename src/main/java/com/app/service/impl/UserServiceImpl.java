package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.model.Customer;
import com.app.repository.CustomerRepository;
import com.app.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final CustomerRepository customerRepository;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Customer> customers = customerRepository.findByEmail(username);
		if(customers.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		Customer customer = customers.get(0);
 
		List<SimpleGrantedAuthority> authorities = customer.getAuthorities().stream()
				.map(item -> new SimpleGrantedAuthority(item.getName()))
				.toList();
 
		return new User(customer.getEmail(), customer.getPwd(), authorities);
	}

}
