package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Cards;
import com.app.repository.CardsRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j(topic = "CardController")
public class CardController {
 
    private final CardsRepository cardsRepository;
    
    public CardController(CardsRepository cardsRepository) {
		this.cardsRepository = cardsRepository;
	}

	@GetMapping("/myCards/{id}")
    public ResponseEntity<List<Cards>> getCardDetail(@PathVariable("id") int id) {
		log.info(" getCardDetails  [] ");
        return ResponseEntity.ok(cardsRepository.findByCustomerId(id));
    }
	
    @PreAuthorize("hasAuthority('VIEWCARDS1')")
	@GetMapping("/myCards")
    public ResponseEntity<List<Cards>> getCardDetails() {
		log.info(" getCardDetails  [] ");
		List<Cards> cards = cardsRepository.findAll();
        return ResponseEntity.ok(cards);
    }

}
