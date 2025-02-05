package com.app.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TokenBody {
	
	private String username;
	
	private List<String> authorities;

}
