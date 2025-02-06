package com.app.exception;

import java.time.LocalDateTime;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.dto.ErrorDetail;

@ControllerAdvice
public class HandlerException  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = {LockedException.class, DisabledException.class, BadCredentialsException.class, AuthenticationException.class})
	private ResponseEntity<ErrorDetail> handleAuthentication(Exception exception, WebRequest request){
		ErrorDetail error = new ErrorDetail(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

}
