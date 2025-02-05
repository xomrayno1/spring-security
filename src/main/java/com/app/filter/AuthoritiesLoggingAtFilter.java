package com.app.filter;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthoritiesLoggingAtFilter extends OncePerRequestFilter {

    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        LOG.info("Authentication Validation is in progress");
        filterChain.doFilter(request, response);
		
	}

}