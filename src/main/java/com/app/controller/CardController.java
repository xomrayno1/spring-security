package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	
	@GetMapping("/myCards")
	public String getCardDetails() {
		return "Card Details: Card Type: Debit Card, Card Number: 1234567890";
	}

}
