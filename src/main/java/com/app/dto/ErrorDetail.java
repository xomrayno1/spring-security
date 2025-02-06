package com.app.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.app.APIStatus;
import com.app.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
	private int code;
	private String message;
	@JsonFormat(shape = Shape.STRING, pattern = DateUtils.PATTERN_DATE_TIME_FORMAT)
	private LocalDateTime time;
	@Setter
	private Map<String, String> error;
	
	public ErrorDetail(int code, String message, LocalDateTime time) {
		this.code = code;
		this.message = message;
		this.time = time;
	}
 
	public ErrorDetail(APIStatus apiStatus, LocalDateTime time) {
		if(apiStatus == null) {
			throw new IllegalArgumentException("apiStatus invalid"); 
		}
		this.code = apiStatus.getCode();
		this.message = apiStatus.getMessage();
		this.time = time;
	}
  
}
