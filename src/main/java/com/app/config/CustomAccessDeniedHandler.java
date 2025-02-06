package com.app.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.app.dto.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN; // 403

		ErrorDetail error = new ErrorDetail(httpStatus.value(), accessDeniedException.getMessage(),
				LocalDateTime.now());

		// setting the response HTTP status code
		response.setStatus(httpStatus.value());

		// serializing the response body in JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().println(objectMapper.writeValueAsString(error));

	}

}
