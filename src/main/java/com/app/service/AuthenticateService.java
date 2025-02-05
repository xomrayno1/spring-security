package com.app.service;

import com.app.dto.LoginResponse;

public interface AuthenticateService {
	
	LoginResponse authenticate(String username, String password);

}
