package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.model.Accounts;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	Accounts findByCustomerId(int customerId);
}
