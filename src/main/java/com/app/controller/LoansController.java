package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

	@GetMapping("/myLoans")
	public String getLoanDetails() {
		return "Loan Details: Loan Amount: $10000, Loan Type: Personal Loan";
	}
	
}
