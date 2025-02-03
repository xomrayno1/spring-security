package com.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.AccountTransactions;

@Repository
public interface AccountTransactionsRepository  extends CrudRepository<AccountTransactions, Long>{
	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}
