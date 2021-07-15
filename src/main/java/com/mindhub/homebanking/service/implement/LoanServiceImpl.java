package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan getLoan(long id) { return loanRepository.findById(id); }
    @Override
    public List<Loan> getLoans(){ return loanRepository.findAll(); }
    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

}
