package com.app.filter;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * logging <br> 
 *  setting bên SecurityConfig show logging sau khi run BasicAuthenticationFilter
 *  trong logic của OncePerRequestFilter có login check nếu filter đã run rồi thì nó sẽ gửi tới next filter mà không run lại lần thứ 2 
 *  Sử dụng OncePerRequestFilter thay vì interface Filter vì khi login chỉ tạo ra 1 log, còn Filter tạo ra 2 log
 * **/
public class AuthoritiesLoggingAfterFilter extends OncePerRequestFilter {

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOG.info("User " + authentication.getName() + " is successfully authenticated and "
                    + "has the authorities " + authentication.getAuthorities().toString());
        }
        filterChain.doFilter(request, response);
	}

}
