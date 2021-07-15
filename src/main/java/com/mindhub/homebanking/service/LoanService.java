package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Loan;
import java.util.List;

public interface LoanService {

    Loan getLoan(long id);
    List<Loan> getLoans();
    void saveLoan(Loan loan);

}
