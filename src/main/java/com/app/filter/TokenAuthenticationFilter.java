package com.app.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.constants.SecurityConstants;
import com.app.dto.TokenBody;
import com.app.utils.JwtTokenUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(SecurityConstants.JWT_HEADER);
		if(token == null || !token.contains("Bearer ") || token.length() < 7 ) {
			filterChain.doFilter(request, response);
			return;
		}
		
		token = token.split("Bearer ")[1];
		if(!JwtTokenUtils.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		TokenBody tokenBody = JwtTokenUtils.getBody(token);
		if(tokenBody != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			List<SimpleGrantedAuthority> authorities = tokenBody.getAuthorities().stream()
					.map(SimpleGrantedAuthority::new)
					.toList();
			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					tokenBody.getUsername(), null, authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
