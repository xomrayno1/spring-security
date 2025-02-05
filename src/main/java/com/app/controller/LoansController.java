package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Loans;
import com.app.repository.LoanRepository;

@RestController
public class LoansController {

    private final LoanRepository loanRepository;
  

    public LoansController(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam int id) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }
	
}
