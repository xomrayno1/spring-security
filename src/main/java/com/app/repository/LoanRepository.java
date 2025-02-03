package com.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.model.Loans;

public interface LoanRepository extends CrudRepository<Loans, Long>{
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
