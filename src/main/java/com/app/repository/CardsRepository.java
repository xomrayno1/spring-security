package com.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.model.Cards;

public interface CardsRepository extends CrudRepository<Cards, Long> {
	List<Cards> findByCustomerId(int customerId);
}
