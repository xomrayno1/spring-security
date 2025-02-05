package com.app.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.dto.LoginResponse;
import com.app.dto.TokenBody;
import com.app.service.AuthenticateService;
import com.app.utils.JwtTokenUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	public LoginResponse authenticate(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		List<String> authorities = authentication.getAuthorities().stream().toList().stream()
			.map(item -> item.getAuthority())
			.toList();
		
		return LoginResponse.builder()
				.token(JwtTokenUtils.generateToken(
						TokenBody.builder()
							.username(authentication.getName())
							.authorities(authorities)
							.build())
						)
				.build();
	}

}
